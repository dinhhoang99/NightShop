package com.nightshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexAdminController {

//	@Autowired
//	private AccountRepository accountRepo;

	@GetMapping("/admin")
	public String index(Model model) {
		
		
		return "admin/index";
	}
	
	@GetMapping("/admin/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("Account");
		return "redirect:/login";
	}
}
