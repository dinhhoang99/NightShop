package com.nightshop.controller;

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

import com.nightshop.entity.AccountEntity;
import com.nightshop.entity.CartEntity;
import com.nightshop.entity.ColorEntity;
import com.nightshop.entity.ProductEntity;
import com.nightshop.entity.Product_ColorEntity;
import com.nightshop.entity.Product_SizeEntity;
import com.nightshop.entity.SizeEntity;
import com.nightshop.repository.CartRepository;
import com.nightshop.repository.ColorRepository;
import com.nightshop.repository.ProductRepository;
import com.nightshop.repository.Product_ColorRepository;
import com.nightshop.repository.Product_SizeRepository;
import com.nightshop.repository.SizeRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/index")
public class DetailController {
	
	@Autowired
	private ProductRepository prRepository;
	
	@Autowired
	private Product_SizeRepository prSizeRepository;
	
	@Autowired 
	private Product_ColorRepository prCorlorRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ColorRepository colorRepository;
	
	@Autowired
	private SizeRepository sizeRepository;
	
	@GetMapping("/detailProduct/{id}")
	public String index(Model model, @PathVariable(name = "id") int id) {
		ProductEntity prEntity = prRepository.findById(id);
		List<Product_SizeEntity> prSizeEntity = prSizeRepository.findByProductId(id);
		List<Product_ColorEntity> prColorEntity = prCorlorRepository.findByProductId(id);
		
		model.addAttribute("prColor", prColorEntity);
		model.addAttribute("prSize",prSizeEntity);
		model.addAttribute("product", prEntity);
		model.addAttribute("quantity", prEntity.getQuantity());
		return "user/detail";
	}
	@PostMapping("/detailProduct/{id}")
	public String indexPost(Model model, @PathVariable(name = "id") int id) {
		ProductEntity prEntity = prRepository.findById(id);
		List<Product_SizeEntity> prSizeEntity = prSizeRepository.findByProductId(id);
		List<Product_ColorEntity> prColorEntity = prCorlorRepository.findByProductId(id);
		
		model.addAttribute("prColor", prColorEntity);
		model.addAttribute("prSize",prSizeEntity);
		model.addAttribute("product", prEntity);
		model.addAttribute("quantity", prEntity.getQuantity());
		return "user/detail";
	}
	
	@SuppressWarnings("unused")
	@PostMapping("/addCartProduct/{id}")
	public String addCartProduct(Model model, HttpSession session,HttpServletRequest request,@PathVariable(name = "id") int id
			) {
		AccountEntity account = (AccountEntity) session.getAttribute("Account");
		if(account == null) {
			return "redirect:/login";
		}else {
			String  color = request.getParameter("color");
			String  size = request.getParameter("size");
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			
			
			if(color == null || size == null) {
				model.addAttribute("messager", "Chưa Chọn Kích Thước Hoặc Màu Sắc");
				return "forward:/index/detailProduct/" + id;
				
			}else {
				
				ColorEntity colorEntity = colorRepository.findById(Integer.parseInt(color));
				SizeEntity sizeEntity = sizeRepository.findById(Integer.parseInt(size));
				ProductEntity productEntity = prRepository.findById(id);
				List<CartEntity> findAllCartById = cartRepository.findAll(account.getId());
				CartEntity cartEntity = new CartEntity();
				
				if(quantity > productEntity.getQuantity()) {
					model.addAttribute("messager", "Số Lượng Còn Lại Không Đủ");
					return "forward:/index/detailProduct/" + id;
				}
					
				cartEntity.setColor(colorEntity);
				cartEntity.setSize(sizeEntity);
				cartEntity.setAccount(account);
				cartEntity.setProduct(productEntity);
				cartEntity.setQuantity(quantity);
				
				CartEntity cartToUpdate = null;

				// Kiểm tra xem sản phẩm đã tồn tại hay chưa
				for (CartEntity cart : findAllCartById) {
				    if (cart.getProduct().equals(cartEntity.getProduct()) &&
				            cart.getSize().equals(cartEntity.getSize()) &&
				            cart.getColor().equals(cartEntity.getColor())) {
				        cartToUpdate = cart;
				        System.out.println(cartToUpdate.getColor().getColorname());
				        break;
				    }
				}

				if (cartToUpdate == null) {
				    cartRepository.save(cartEntity);
				} else {
				    cartToUpdate.setQuantity(cartToUpdate.getQuantity() + 1);
				    cartRepository.save(cartToUpdate);
				}

			}
			
			
		}
		return "redirect:/index/cart";
	}
}
