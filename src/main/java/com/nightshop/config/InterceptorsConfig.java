package com.nightshop.config;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.nightshop.interceptor.LoginInterceptor;

@Configuration
public class InterceptorsConfig implements	WebMvcConfigurer {
	
	@Autowired 
	private LoginInterceptor loginInterceptor;
	
	@Bean("messageSource")
	public MessageSource loadMessageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		
		source.setBasenames( 
				"classpath:i18n/home"
		);
		
		source.setDefaultEncoding(StandardCharsets.UTF_8.name());
		return source;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
		.addPathPatterns("/admin/**")
		.excludePathPatterns("/login");
		
		LocaleChangeInterceptor changeInterceptor  = new LocaleChangeInterceptor();
		changeInterceptor.setParamName("language");
		registry.addInterceptor(changeInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/admin/**");
		
	}
	
	@Bean("localeResolver")
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setCookieMaxAge(60*60 * 24 * 3);
		cookieLocaleResolver.setCookiePath("/");
		cookieLocaleResolver.setDefaultLocale(new Locale("vn"));
		return  cookieLocaleResolver;
	}
	
}
