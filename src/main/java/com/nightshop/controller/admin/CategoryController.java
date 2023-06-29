package com.nightshop.controller.admin;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nightshop.entity.CategoryEntity;
import com.nightshop.entity.ProductEntity;
import com.nightshop.repository.CategoryRepository;
import com.nightshop.repository.ProductRepository;

@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@GetMapping("/category")
	public String index(Model model) {
		List<ProductEntity> listProduct = productRepo.findAll();
		List<CategoryEntity> listCategory = categoryRepo.findAll();
		
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("category", listCategory);
		return "admin/category";
	}
	
	@PostMapping("/category")
	public String indexPost(Model model) {
		List<ProductEntity> listProduct = productRepo.findAll();
		List<CategoryEntity> listCategory = categoryRepo.findAll();
		
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("category", listCategory);
		return "admin/category";
	}
	
	
	@PostMapping("/category/add")
	public String addCategory(@RequestParam("nameCategory") String categoryName,
			@RequestParam("isActive") Boolean isActive,@RequestParam("images") MultipartFile images,Model model) {
		List<CategoryEntity> listCategory = categoryRepo.findAll();
		
		if (!images.isEmpty()) {
	        try {
	            // Lưu file vào thư mục cần thiết
	            String uploadsDir = "D:\\Java5\\Assignment_BanHang\\src\\main\\resources\\static\\img\\"; // Thay đổi đường dẫn tùy theo nơi lưu trữ bạn mong muốn
	            String imagesname = images.getOriginalFilename();
	            String imagesPath = uploadsDir + "/" + imagesname;
	            images.transferTo(new File(imagesPath));
	        } catch (Exception e) {
	            return "admin/addproduct " + e.getMessage();
	        }
	    } else {
	        
	    }
		
		if(categoryName.isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Tên Danh Mục");
			return "forward:/admin/category";
		}
		
		
		for(CategoryEntity c : listCategory) {
			if(categoryName.contains(c.getCategoryName())) {
				model.addAttribute("error", "Danh Mục Đã Tồn Tại");
				return "forward:/admin/category";
			}
		}
		CategoryEntity category = new CategoryEntity();
		category.setImages(images.getOriginalFilename());
		category.setCategoryName(categoryName);
		category.setActive(isActive);
		categoryRepo.save(category);
		model.addAttribute("error", "Thêm Thành Công");
		return "redirect:/admin/category";
	}
}
