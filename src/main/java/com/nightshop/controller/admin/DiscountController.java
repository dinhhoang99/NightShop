package com.nightshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nightshop.entity.DiscountEntity;
import com.nightshop.repository.DiscountRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class DiscountController {

	@Autowired
	private DiscountRepository discountRepo;
	
	@GetMapping("/discount")
	public String pageDiscount(Model model) {
		DiscountEntity discountEntity  = new DiscountEntity();
		List<DiscountEntity> listDiscount  = discountRepo.findAll();
		
		model.addAttribute("discountEntity", discountEntity);
		model.addAttribute("discount", listDiscount);
		model.addAttribute("uri", "adddiscount");
		return "admin/discount";
	}
	

	
	@GetMapping("/discount/edit/{id}")
	public String pageEditDisCount(Model model,@PathVariable(name = "id") int id) {
		
		DiscountEntity findDiscount = discountRepo.findById(id);
		List<DiscountEntity> listDiscount  = discountRepo.findAll();
//		System.out.println(findDiscount.getNameDiscount());
		
		model.addAttribute("discountEntity", findDiscount);
		model.addAttribute("discount", listDiscount);
		model.addAttribute("uri", "update/" + id);
		return "admin/discount";
		
	}
	
	@PostMapping("/discount/adddiscount")
	public String addDiscount(Model model, @Valid @ModelAttribute("addDiscount") DiscountEntity discount) {
		List<DiscountEntity> listDistcount = discountRepo.findAll();
		
		discountRepo.save(discount);
		model.addAttribute("uri", "adddiscount");
		return "redirect:/admin/discount";
	}
	
	@PostMapping("/discount/update/{id}")
	public String updateDiscount(Model model, @Valid @ModelAttribute("addDiscount") DiscountEntity discount, @PathVariable(name = "id") int id) {
		List<DiscountEntity> listDistcount = discountRepo.findAll();
		discount.setId(id);
		discountRepo.save(discount);
		model.addAttribute("uri", "adddiscount");
		return "redirect:/admin/discount";
	}
}
