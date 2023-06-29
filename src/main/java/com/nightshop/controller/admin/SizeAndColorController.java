package com.nightshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nightshop.entity.ColorEntity;
import com.nightshop.entity.ProductEntity;
import com.nightshop.entity.Product_ColorEntity;
import com.nightshop.entity.Product_SizeEntity;
import com.nightshop.entity.SizeEntity;
import com.nightshop.repository.ColorRepository;
import com.nightshop.repository.ProductRepository;
import com.nightshop.repository.Product_ColorRepository;
import com.nightshop.repository.Product_SizeRepository;
import com.nightshop.repository.SizeRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class SizeAndColorController {

	@Autowired
	private Product_SizeRepository prSizeRepo;
	
	@Autowired
	private Product_ColorRepository prColorRepo;
	
	@Autowired
	private SizeRepository sizeRepo;
	
	@Autowired
	private ColorRepository colorRepo;
	
	@Autowired
	private ProductRepository productRepo;
	@GetMapping("/size")
	public String index(Model model) {
		List<Product_ColorEntity> listProductColor = prColorRepo.findAllByOrderByIsActiveDesc();
		List<Product_SizeEntity> listProductSize = prSizeRepo.findAll();
		List<SizeEntity> listSize = sizeRepo.findAll();
		List<ColorEntity> listColor = colorRepo.findAll();
		List<ProductEntity> listProduct = productRepo.findAll();
		
		model.addAttribute("listProductSize", listProductSize);
		model.addAttribute("listProductColor", listProductColor);
		model.addAttribute("listSize", listSize);
		model.addAttribute("listColor", listColor);
		model.addAttribute("product", listProduct);
		return "admin/size";
	}
	
	@PostMapping("/size")
	public String pageIndex(Model model) {
		List<Product_ColorEntity> listProductColor = prColorRepo.findAllByOrderByIsActiveDesc();
		List<Product_SizeEntity> listProductSize = prSizeRepo.findAll();
		List<SizeEntity> listSize = sizeRepo.findAll();
		List<ColorEntity> listColor = colorRepo.findAll();
		List<ProductEntity> listProduct = productRepo.findAll();
		
		model.addAttribute("listProductSize", listProductSize);
		model.addAttribute("listProductColor", listProductColor);
		model.addAttribute("listSize", listSize);
		model.addAttribute("listColor", listColor);
		model.addAttribute("product", listProduct);
		return "admin/size";
	}
	
	@PostMapping("/addsize")
	public String pageAddSize(Model model, @Valid @ModelAttribute("addsize") SizeEntity size, RedirectAttributes attributes) {
		List<SizeEntity> listSize = sizeRepo.findAll();
		System.out.println(size.getSizeName());
		if(size.getSizeName().isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Kích Thước");
			return "forward:/admin/size";
		}
		for(SizeEntity s : listSize) {
			if(size.getSizeName().equals(s.getSizeName())) {
				model.addAttribute("error", "Kích Thước Tồn Tại");
				return "forward:/admin/size";
			}
		}
		sizeRepo.save(size);
		model.addAttribute("messager", "Thêm Thành Công");
		return "forward:/admin/size";
	}
	
	@PostMapping("/addcolor")
	public String pageAddColor(Model model, @Valid @ModelAttribute("addcolor") ColorEntity color, RedirectAttributes attributes) {
		List<ColorEntity> listColor = colorRepo.findAll();
		System.out.println(color.getColorname());
		if(color.getColorname().isEmpty()) {
			model.addAttribute("error", "Chưa Nhập Màu Sắc");
			return "forward:/admin/size";
		}
		for(ColorEntity c : listColor) {
			if(color.getColorname().equals(c.getColorname())) {
				model.addAttribute("errorColor", "Màu Sắc Đã Tồn Tại");
				return "forward:/admin/size";
			}
		}
		colorRepo.save(color);
		model.addAttribute("messagerColor", "Thêm Thành Công");
		return "forward:/admin/size";
	}
	
	@PostMapping("/size/searchsize")
	public String pageSearchSize(@RequestParam("searchSize") String searchSize, Model model) {
		
		List<Product_SizeEntity> listPrSize = prSizeRepo.findByKeyWord(searchSize);
		List<Product_ColorEntity> listProductColor = prColorRepo.findAll();
		List<SizeEntity> listSize = sizeRepo.findAll();
		List<ColorEntity> listColor = colorRepo.findAll();
		List<ProductEntity> listProduct = productRepo.findAll();
		
		model.addAttribute("product", listProduct);
		model.addAttribute("listProductColor", listProductColor);
		model.addAttribute("listSize", listSize);
		model.addAttribute("listColor", listColor);
		model.addAttribute("listProductSize", listPrSize);
		return "admin/size";
	}
	
	@PostMapping("/color/searchcolor")
	public String pageSearchColor(@RequestParam("searchColor") String searchcolor, Model model) {
		
		List<Product_ColorEntity> listPrColor = prColorRepo.findByKeyWord(searchcolor);
		List<SizeEntity> listSize = sizeRepo.findAll();
		List<ColorEntity> listColor = colorRepo.findAll();
		List<Product_SizeEntity> listProductSize = prSizeRepo.findAll();
		List<ProductEntity> listProduct = productRepo.findAll();
		
		model.addAttribute("product", listProduct);
		model.addAttribute("listSize", listSize);
		model.addAttribute("listColor", listColor);
		model.addAttribute("listProductSize", listProductSize);
		model.addAttribute("listProductColor", listPrColor);
		return "admin/size";
	}
	
	@GetMapping("/size/hidesize/{id}")
	public String hideSize(@PathVariable(name = "id") int id, Model model) {
		
		Product_SizeEntity prSize = prSizeRepo.getOne(id);
		prSize.setIsActive(false);
		
		prSizeRepo.save(prSize);
		model.addAttribute("messager", "Chỉnh Sửa Thành Công");
		return "forward:/admin/size";
	}
	
	@GetMapping("/size/presently/{id}")
	public String hidePresently(@PathVariable(name = "id") int id, Model model) {
		
		Product_SizeEntity prSize = prSizeRepo.getOne(id);
		prSize.setIsActive(true);
		
		prSizeRepo.save(prSize);
		model.addAttribute("messager", "Chỉnh Sửa Thành Công");
		return "forward:/admin/size";
	}
	
	@GetMapping("/color/presentlycolor/{id}")
	public String hideColor(@PathVariable(name = "id") int id, Model model) {
		
		Product_ColorEntity prColor = prColorRepo.getOne(id);
		prColor.setIsActive(false);
		
		prColorRepo.save(prColor);
		model.addAttribute("messager", "Chỉnh Sửa Thành Công");
		return "forward:/admin/size";
	}
	
	@GetMapping("/color/presently/{id}")
	public String hidePresentlyCColor(@PathVariable(name = "id") int id, Model model) {
		
		Product_ColorEntity prColor = prColorRepo.getOne(id);
		prColor.setIsActive(true);
		
		prColorRepo.save(prColor);
		model.addAttribute("messager", "Chỉnh Sửa Thành Công");
		return "forward:/admin/size";
	}
	
	@PostMapping("/size/addsizeproduct")
	public String addSizeProduct(@RequestParam("product") int productId, 
			@RequestParam("size") int sizeId, Model model) {
		ProductEntity product = productRepo.findById(productId);
		SizeEntity size = sizeRepo.findById(sizeId);
		List<Product_SizeEntity> listPrSize = prSizeRepo.findAll();
		
		for(Product_SizeEntity ps : listPrSize) {
			if(ps.getProduct().getId() == productId && ps.getSize().getId() == sizeId) {
				model.addAttribute("errorProductSize", "Thêm Thất Bại Kích Thước Đã Tồn Tại");
				return "forward:/admin/size";
			}
		}
		model.addAttribute("messagerProductSize", "Thêm Thành Công");
		Product_SizeEntity productSize = new Product_SizeEntity();
		productSize.setProduct(product);
		productSize.setSize(size);
		productSize.setIsActive(true);
		prSizeRepo.save(productSize);
		
		return "forward:/admin/size";
	}
	
	@PostMapping("/size/addcolorproduct")
	public String addColorProduct(@RequestParam("product") int productId, 
			@RequestParam("color") int colorId, Model model) {
		ProductEntity product = productRepo.findById(productId);
		ColorEntity color = colorRepo.findById(colorId);
		List<Product_ColorEntity> listProductColor = prColorRepo.findAll();
		
		for(Product_ColorEntity pc : listProductColor) {
			if(pc.getProduct().getId() == productId && pc.getColor().getId() == colorId) {
				model.addAttribute("errorProductColor", "Thêm Thất Bại Màu Sắc Đã Tồn Tại");
				return "forward:/admin/size";
			}
		}
		model.addAttribute("messagerProductColor", "Thêm Thành Công");
		Product_ColorEntity productColor = new Product_ColorEntity();
		productColor.setProduct(product);
		productColor.setColor(color);
		productColor.setIsActive(true);
		prColorRepo.save(productColor);
		
		return "forward:/admin/size";
	}
}
