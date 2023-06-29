package com.nightshop.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nightshop.entity.AccountEntity;
import com.nightshop.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/index")
public class ProfileController {

	private int senderCode;
	
	private final JavaMailSender emailSender;
	 
	public ProfileController(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	
	@Autowired
	private AccountRepository accountRepo;
	
	@GetMapping("profile")
	private String pageProfile(Model model, HttpSession session) {
		AccountEntity account = (AccountEntity) session.getAttribute("Account");
		model.addAttribute("account", account);	
		return "user/profile";
	}
	
	@PostMapping("profile")
	private String pageProfilePost(Model model, HttpSession session) {
		AccountEntity account = (AccountEntity) session.getAttribute("Account");
		model.addAttribute("account", account);	
		return "user/profile";
	}
	
	@PostMapping("/profile/update/{id}")
	private String updateProfile(Model model,@PathVariable(name = "id") int id, @Valid @ModelAttribute("account") AccountEntity profile,
			HttpSession session, @RequestParam("sendCode") String sendCode) {
		
	
		
		if(profile.getSdt().isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Số Điện Thoại");
			return "forward:/index/profile";
		}
		if(profile.getDiachi().isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Địa Chỉ");
			return "forward:/index/profile";
		}
		if(sendCode.isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Mã");
			return "forward:/index/profile";
		}
		if(Integer.parseInt(sendCode) != senderCode) {
			model.addAttribute("error", "Mã Xác Thực Không Đúng");
			return "forward:/index/profile";
		}
		AccountEntity account = accountRepo.findById(id);
		account.setFullname(profile.getFullname());
		account.setSdt(profile.getSdt());
		account.setEmail(profile.getEmail());
		account.setDiachi(profile.getDiachi());
		account.setUsername(profile.getUsername());
		account.setPassword(profile.getPassword());
		session.setAttribute("Account", account);
		accountRepo.save(account);
		model.addAttribute("messager", "Cập Nhật Thành Công ");
		return "forward:/index/profile";
	}
	
	@PostMapping("/profile/sendcode")
	private String sendCodeProfile(@RequestParam("email") String email, Model model) {
		Random random = new Random();
		senderCode = random.nextInt(99999) + 10000;
		String to = email;
		String subject = "Mã Xác Thực ";
        String body = "Mã Cập Nhật Tài Khoản Là : " + senderCode;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
		return "forward:/index/profile";
	}
	
}
