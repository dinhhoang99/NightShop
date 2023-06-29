package com.nightshop.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nightshop.entity.AccountEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AccountEntity  account = (AccountEntity) request.getSession().getAttribute("Account");
		if(account != null) {
			if(account.isAdmin() == false) {
				return true;
			}else {
				response.sendRedirect("/login");
				return false;
			}
		}
		
		response.sendRedirect("/login");
		return false;
	}
}
