package com.nightshop.controller.admin;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nightshop.entity.AccountEntity;
import com.nightshop.repository.AccountRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AccountController {
	
	@Autowired
	private AccountRepository accountRepo;
	
	
	//List Account
	@GetMapping("/accountlist")
	public String pageListAccount(Model model) {
		List<AccountEntity> listAccount = accountRepo.findAll();
//		Collections.sort(listAccount, Comparator.com
		
		model.addAttribute("listAccount", listAccount);
		return "admin/accountlist";
	}
	
	@GetMapping("/accountlist/delete/{id}")
	public String deleteAccount(Model model,
			@PathVariable(name = "id") int id) {
		AccountEntity deleteAccount = accountRepo.findById(id);
		
		deleteAccount.setActive(false);
		
		accountRepo.save(deleteAccount);
		return "redirect:/admin/accountlist";
	}
	
	//Form Account
	@GetMapping("/account")
	public String pageAccount(Model model) {
		model.addAttribute("account", new AccountEntity());
		model.addAttribute("uri", "account");
		return "admin/addaccount";
	}
	
	//Add Account
	@PostMapping("/account")
	public String addAccount(@Valid @ModelAttribute("addaccount") AccountEntity acccount,
				RedirectAttributes attributes
			) {
		AccountEntity accountByUsername = accountRepo.findByUsername(acccount.getUsername());
		AccountEntity accountByEmail = accountRepo.findByEmail(acccount.getEmail());
		
		if(accountByUsername == null) {
			if(accountByEmail ==  null) {
				accountRepo.save(acccount);
				return "redirect:/admin/accountlist";
			}
		}else {
			
		}
		return "admin/addaccount";
	}
	
	// Deatils Account
	@GetMapping("/accountlist/details/{id}")
	public String detailAccount(Model model,
				@PathVariable(name = "id") int id
			) {
		AccountEntity findById = accountRepo.findById(id);
		
		model.addAttribute("uri", "updateAccount/"+ findById.getId());
		model.addAttribute("account", findById);
		return "admin/addaccount";
	}
	
	//Update Account
	@PostMapping("/accountlist/details/updateAccount/{id}")
	public String updateAccount(@Valid @ModelAttribute("addaccount") AccountEntity acccount,
			RedirectAttributes attributes,@PathVariable(name = "id") int id) 
	{
		acccount.setId(id);
		accountRepo.save(acccount);
		return "redirect:/admin/accountlist";
	}
		
}
