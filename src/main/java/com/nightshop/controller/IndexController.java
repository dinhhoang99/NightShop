	package com.nightshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nightshop.entity.AccountEntity;
import com.nightshop.entity.CartEntity;
import com.nightshop.entity.CategoryEntity;
import com.nightshop.entity.ProductEntity;
import com.nightshop.repository.CartRepository;
import com.nightshop.repository.CategoryRepository;
import com.nightshop.repository.ProductRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

	@Autowired
	private ProductRepository prRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@SuppressWarnings("unused")
	@RequestMapping(value = {"/index"}, method = RequestMethod.GET)
	public String  index(HttpServletRequest request,Model model,HttpSession session) {
		
		AccountEntity account = (AccountEntity) session.getAttribute("Account");
		List<ProductEntity> listProduct = prRepository.findByIsActive();
		List<CategoryEntity> listCategory = categoryRepository.findAll();
		
		
		Boolean check;
		int count = 0;
		if(account != null) {
			List<CartEntity> findAll = cartRepository.findAll(account.getId());
			model.addAttribute("fullname", account.getFullname());
			session.setAttribute("Account", account);
			for(CartEntity c : findAll) {
				count ++;
			}
			session.setAttribute("countCart", count);
			check = true;
		}else {
			session.setAttribute("Account", null);
			session.setAttribute("countCart", count);
			check = false;
		}
		List<ProductEntity> productPage = listProduct.subList(0, Math.min(4, listProduct.size()));
		model.addAttribute("listProduct", listProduct);
		session.setAttribute("category", listCategory); 
		
		model.addAttribute("uri", check);
		return "user/index";
	}
	
	
}
