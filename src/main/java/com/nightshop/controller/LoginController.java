package com.nightshop.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.nightshop.entity.AccountEntity;
import com.nightshop.repository.AccountRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller

public class LoginController {
	private final JavaMailSender emailSender ;
	private static String emails;

	
	public LoginController(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	
	@Autowired
	private AccountRepository accountRepo;
	
	@GetMapping(value = "/login")
	public String pageLogin() {
		System.out.println("Hoàng");
		return "login";
	}
	
	@PostMapping("/login")
	public String checkLogin(Model model,@Valid @ModelAttribute("account") AccountEntity account,HttpSession session,
			RedirectAttributes attributes
			) {
		AccountEntity accountByUsername = accountRepo.findByUsername(account.getUsername());
		
		if(accountByUsername.getUsername().equals(account.getUsername()) &&
			    accountByUsername.getPassword().equals(account.getPassword())) {
				if (accountByUsername.isActive()) {
					if(accountByUsername.isAdmin()) {
						session.setAttribute("Account", accountByUsername);
						attributes.addFlashAttribute("fullname",accountByUsername.getFullname());
						return "redirect:/index";
					}else {
						session.setAttribute("Account", accountByUsername);
						return "redirect:/admin";
					}
				}else {
					return "redirect:/cart";
				}
			}else {
				
		}
			
		
		return "login";
	}
	
	@GetMapping("/logout")
	public String pageLogout(HttpSession session) {
		session.removeAttribute("Account");
		return "redirect:/index";
	}
	
	
	@PostMapping("/register")
	public String pageRegister(Model model, @Valid @ModelAttribute(name = "registerAccount") AccountEntity account) {
		AccountEntity findByEmail = accountRepo.findByEmail(account.getEmail());
		AccountEntity findByUserName = accountRepo.findByUsername(account.getUsername());
		if(account.getFullname().isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Họ Và Tên");
			return "/login";
		}
		if(account.getUsername().isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Tên Đăng Nhập");
			return "/login";
		}
		if(account.getEmail().isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Email");
			return "/login";
		}
		if(account.getPassword().isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Mật Khẩu");
			return "/login";
		}
		if(findByUserName != null) {
			model.addAttribute("error", "Tên Đăng Nhập Đã Tồn Tại");
			return "/login";
		}
		
		if(findByEmail !=  null) {
			model.addAttribute("error", "Email Đã Tồn Tại");
			return "/login";
		}
		
		account.setActive(true);
		account.setAdmin(true);
		accountRepo.save(account);
		
		return "redirect:/index";
	}
	
	// Quên Mật Khẩu
	
	@GetMapping("/forgot-password")
	private String pageForgotPassWord(Model model) {
		model.addAttribute("displayEmail", true);
		model.addAttribute("displayEmailLink", false);
		model.addAttribute("displayForgotPass", false);
		
		model.addAttribute("uri", "/forgot-password/sendemail");
		return "/forgotpassword";
	}
	
	@PostMapping("/forgot-password")
	private String pageForgotPassWordPost(Model model) {
		model.addAttribute("displayEmail", true);
		model.addAttribute("displayEmailLink", false);
		model.addAttribute("displayForgotPass", false);
		
		model.addAttribute("uri", "/forgot-password/sendemail");
		return "/forgotpassword";
	}
	
	@PostMapping("/forgot-password/sendemail")
	private String forgotSendEmail(Model model,@RequestParam("email") String email, HttpServletRequest request) {
		AccountEntity findByEmail = accountRepo.findByEmail(email);
		if(findByEmail == null) {
			model.addAttribute("messager", "Email Không Tồn Tại");
			return "forward:/forgot-password";
		}else {
			String resetLink = UriComponentsBuilder.fromHttpUrl(getBaseUrl(request))
                .path("/newpassword")
                .queryParam("email", email)
                .encode() // Mã hóa giá trị tham số
                .toUriString();

			String to = email;
	        String subject = "Đổi Mật Khẩu Tài Khoản";
	        String body = "Nhấn Vào Để Đổi Mật Khẩu : " +	 resetLink;
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);
	        emailSender.send(message);
			
			model.addAttribute("displayEmailLink", true);
			model.addAttribute("displayForgotPass", false);
				
		}
		return "/forgotpassword";
	}
	
	// Lấy base URL từ request
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        return scheme + "://" + serverName + ":" + serverPort + contextPath;
    }
    
	@GetMapping("/newpassword")
	private String newPassword(Model model ,@RequestParam("email") String email) {
		model.addAttribute("uri", "/reset");
		emails = email;
		model.addAttribute("displayForgotPass", true);
		return "/forgotpassword";
	}
	
	@PostMapping("/reset")
	private  String pageReset(Model model, @RequestParam("password") String password,@RequestParam("confirmPassword") String confirmPassword, HttpServletRequest request ) {
		AccountEntity findByEmail = accountRepo.findByEmail(emails);
		String resetLink = "localhost:8080/newpassword?email="+emails;
		if(confirmPassword.equals(password)) {
			findByEmail.setPassword(password);	
			accountRepo.save(findByEmail);
		}else {
			return "redirect:/forgot-password";
		}
		
		return "/login";
	}
}
