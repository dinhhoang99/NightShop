package com.nightshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nightshop.entity.CategoryEntity;
import com.nightshop.entity.ProductEntity;
import com.nightshop.repository.CategoryRepository;
import com.nightshop.repository.ProductRepository;

@Controller
@RequestMapping("/index")
public class ShopController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("/shop")
	public String index(Model model,@RequestParam(name = "pageSize", defaultValue = "1") Integer pageSize,
			@RequestParam(name = "pageNumber",required = false, defaultValue = "1") Integer pageNumber) {
		List<ProductEntity> listProduct = productRepo.findByIsActive();
		
		int start = (pageNumber - 1) * pageSize + 1;
		int end = start + pageSize - 1;
		int totalPage = listProduct.size() / 3 + (listProduct.size() > 0 ? 1 : 0);
		
		List<ProductEntity> productPage = listProduct.subList(start - 1, Math.min(end, listProduct.size()));

		
		model.addAttribute("listProduct", productPage);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("pageNumber",pageNumber);
		
		return "user/shop";
	}
	
	@GetMapping("/category/{id}")
	public String pageCategory(Model model, @PathVariable(name = "id") int id) {
		List<ProductEntity> listCategory = productRepo.findByCategoryId(id);
		model.addAttribute("listProduct", listCategory);
		return "user/shop";
	}
	
	@PostMapping("/search")
	public String pageSearch(Model model, @RequestParam("searchproduct") String search) {
		
		List<ProductEntity> listSearch = productRepo.searchProduct(search);
		model.addAttribute("listProduct", listSearch);
		return "user/shop";
	}
}
