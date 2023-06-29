package com.nightshop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nightshop.entity.CategoryEntity;
import com.nightshop.entity.ColorEntity;
import com.nightshop.entity.ProductEntity;
import com.nightshop.entity.Product_ColorEntity;
import com.nightshop.entity.Product_SizeEntity;
import com.nightshop.entity.SizeEntity;
import com.nightshop.repository.CategoryRepository;
import com.nightshop.repository.ColorRepository;
import com.nightshop.repository.ProductRepository;
import com.nightshop.repository.Product_ColorRepository;
import com.nightshop.repository.Product_SizeRepository;
import com.nightshop.repository.SizeRepository;

import jakarta.validation.Valid;

import java.lang.Iterable;


@Controller
@RequestMapping("/admin")
public class ProductController {

	@Autowired
	private ColorRepository colorRepository;
	
	@Autowired
	private SizeRepository sizeRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private Product_SizeRepository pr_sizeRepository;
	
	@Autowired
	private Product_ColorRepository pr_colorRepository;
	
	
	@GetMapping("/productlist")
	public String pageProductlist(Model model) {
		List<ProductEntity> listProduct = productRepository.findAll();
		model.addAttribute("listProduct", listProduct);
		return "admin/productlist";
	}
	
	
	@GetMapping("/product")
	public String pageAddproduct(Model model) {
		model.addAttribute("product", new ProductEntity());
		model.addAttribute("color", new ColorEntity());
		model.addAttribute("size", new SizeEntity());
		
		List<ColorEntity> listColor = colorRepository.findAll();
		List<SizeEntity> listSize = sizeRepository.findAll();
		List<CategoryEntity> listCategory = categoryRepository.findAll();
		
		ProductEntity product = new ProductEntity();
		product.setIsActive(true);
		
		model.addAttribute("pr", product);
		model.addAttribute("showCategory", true);
		model.addAttribute("listColor", listColor);
		model.addAttribute("listSize", listSize);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("uri", "addproduct");
		
		return "admin/addproduct";
	}
	

	@PostMapping("/addproduct")
	public String addProduct(Model model, @RequestParam("image") MultipartFile images,@RequestParam("colorname") int[] colorid,
			@RequestParam("sizename") int[] sizeId,@RequestParam("category") String categoryName, @Valid @ModelAttribute("product") ProductEntity product
			){
		// Lưu Sản Phẩm vào db
		// Lấy đường dẫn và lưu ảnh vào mục lưu trư
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
	    
	    product.setImages(images.getOriginalFilename());
	    product.setCategory(product.getCategory());
	    List<ProductEntity> listProduct = productRepository.findAll();
	    
	    //Kiểm Tra xem sản phẩm đã tồn tại hay chưa
	    Boolean checkProduct = false;
	    for(ProductEntity p : listProduct) {
		   if(product.getProductName().equals(p.getProductName())) {
			   checkProduct = true;
		   }		
	    }
	    if(checkProduct) {
	    	
	    }else {
	    	productRepository.save(product);
		}
	    
	    // Thêm Dữ liệu vào bảng Product_size
	    for(int s : sizeId) {
	    	 Product_SizeEntity pr_size = new Product_SizeEntity();
	    	pr_size.setProduct(product);
	    	pr_size.setSize(sizeRepository.findById(s));
			pr_sizeRepository.save(pr_size);
		}
	    
	 // Thêm Dữ liệu vào bảng Product_color
	    for(int c : colorid) {
	    	Product_ColorEntity pr_color = new Product_ColorEntity();
	    	pr_color.setProduct(product);
	    	pr_color.setColor(colorRepository.findById(c));
	    	pr_colorRepository.save(pr_color);
		}
	    
		return "redirect:/admin/productlist";
	}
	
	
	@GetMapping("/product/details/{id}")
	public String detailProduct(Model model,@PathVariable(name = "id") int id
			) {
		
		ProductEntity productById = productRepository.findById(id);
		
		model.addAttribute("pr", productById);
		model.addAttribute("uri", "/admin/product/updateproduct/" + productById.getId());
		
		return "admin/addproduct";
	}
	
	
	@SuppressWarnings("unused")
	@PostMapping("/product/updateproduct/{id}")
	private String updateProduct(Model model,@PathVariable(name = "id") int id, @RequestParam("image") MultipartFile images,
			 @Valid @ModelAttribute("product") ProductEntity product) {
		
		ProductEntity productFind = productRepository.findById(id);
		
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
		
		productFind.setProductName(product.getProductName());
		productFind.setQuantity(product.getQuantity());
		productFind.setImportPrice(product.getImportPrice());
		productFind.setPrice(product.getPrice());
		productFind.setCategory(productFind.getCategory());
		productFind.setProductColor(productFind.getProductColor());
		productFind.setProductSize(productFind.getProductSize());
		productFind.setIsActive(product.getIsActive());
		productFind.setDescription(product.getDescription());
		if (!images.isEmpty()) {
			productFind.setImages(images.getOriginalFilename());
		}else {
			productFind.setImages(productFind.getImages());
		}
		
		productRepository.save(productFind);
		return "redirect:/admin/productlist";
	}
	
	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		ProductEntity deleteProduct = productRepository.findById(id);
		deleteProduct.setIsActive(false);
		productRepository.save(deleteProduct);
		return "redirect:/admin/productlist";
	}

	
}
