package com.nightshop.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nightshop.entity.AccountEntity;
import com.nightshop.entity.BillEntity;
import com.nightshop.entity.CartEntity;
import com.nightshop.entity.DetailedInvoiceEntity;
import com.nightshop.entity.DiscountEntity;
import com.nightshop.repository.CartRepository;
import com.nightshop.repository.DetailedInvoiceRepository;
import com.nightshop.repository.DiscountRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/index")
public class CartController {
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private DiscountRepository disCountRepository;
	
	@Autowired
	private DetailedInvoiceRepository detailedRepo;
	
	@GetMapping("/cart")
	public String addCartProduct(HttpSession session, Model model) {
		AccountEntity account = (AccountEntity) session.getAttribute("Account");
		if(account == null) {
			return "redirect:/login";
		}else {
			List<CartEntity> findAll = cartRepository.findAll(account.getId());
			model.addAttribute("listCart", findAll);
		}
		return "user/cart";
	}
	
	@PostMapping("/cart")
	public String addCartProductPost(HttpSession session, Model model) {
		AccountEntity account = (AccountEntity) session.getAttribute("Account");
		if(account == null) {
			return "redirect:/login";
		}else {
			List<CartEntity> findAll = cartRepository.findAll(account.getId());
			model.addAttribute("listCart", findAll);
		}
		return "user/cart";
	}
	
	@PostMapping("/cart/delete/{id}")
	public String deteteProductCart(@PathVariable(name = "id") int id) {
		CartEntity cartDelete = (CartEntity) cartRepository.findById(id);
		cartRepository.delete(cartDelete);
		return "redirect:/index/cart";
		
	}
	
	@PostMapping("/cart/addamount/{id}")
	public String addAmount(@PathVariable(name = "id") int id, Model model) {
		CartEntity cartAddAmount = (CartEntity) cartRepository.findById(id);
		if(cartAddAmount.getQuantity() > 1) {
			cartAddAmount.setQuantity(cartAddAmount.getQuantity() - 1);
		}else {
			model.addAttribute("messager", "Không Thể Giảm Số Lượng Xuống Bằng 0");
			return "forward:/index/cart";
		}
		
		cartRepository.save(cartAddAmount);
		return "redirect:/index/cart";
	}
	
	@PostMapping("/cart/minusquantity/{id}")
	public String cartMinusQuantity(@PathVariable(name = "id") int id,  Model model) {
		CartEntity cartAddAmount = (CartEntity) cartRepository.findById(id);
		if(cartAddAmount.getQuantity() < cartAddAmount.getProduct().getQuantity()) {
			cartAddAmount.setQuantity(cartAddAmount.getQuantity() + 1);
		}else {
			model.addAttribute("messager", "Số Lượng Không Đủ");
			return "forward:/index/cart";
		}
		
		cartRepository.save(cartAddAmount);
		return "redirect:/index/cart";
	}
	
	@PostMapping("/checkout")
	public String getProductCart(Model model,HttpSession session, @RequestParam("idCart") String[] cartId,@RequestParam("quantity") int[] quantity,
			@RequestParam("discount") String discountName, HttpServletRequest request
			){
		List<CartEntity> listCart = new ArrayList<>();
		List<DetailedInvoiceEntity> detailedInvoice = new ArrayList<>();
		
		
		String uri = request.getRequestURI();
		AccountEntity account = (AccountEntity) session.getAttribute("Account");
		DiscountEntity discount = disCountRepository.findByName(discountName);
		BigDecimal totalMoney = BigDecimal.ZERO;
		
		//Lấy  những sản phẩm được chọn để thanh toán
		if(uri.contains("checkout")) {
			for(String i : cartId) {
				CartEntity findCart = cartRepository.findById(Integer.parseInt(i));
				totalMoney = totalMoney.add(findCart.getProduct().getImportPrice().multiply(BigDecimal.valueOf(findCart.getQuantity())));
				listCart.add(findCart);
				model.addAttribute("cart", listCart);
			}
		}else {
			for(String id : cartId) {
				DetailedInvoiceEntity detailed = detailedRepo.findByid(id);
				totalMoney = totalMoney.add(detailed.getProduct().getImportPrice().multiply(BigDecimal.valueOf(detailed.getQuantity())));
				detailedInvoice.add(detailed);
				model.addAttribute("cart", detailedInvoice);
			}
		}
		
		
		double moneyDiscount  = 0;
		double moneyNew = totalMoney.doubleValue();
		double shipping = 30.000;
		
		if(discount == null) {
			
		}else {
			int discountCondition = discount.getDiscountConditions();
			moneyDiscount = ( moneyNew / 100 )  * discountCondition;
			
		}
		double intoMoney = (moneyNew + shipping) - moneyDiscount;
		//Lưu Giá trị vào session
		
		session.setAttribute("discountName", discountName);
		session.setAttribute("shipping", shipping);
		session.setAttribute("totalMoney", totalMoney);
		session.setAttribute("intoMoney", intoMoney);
		session.setAttribute("cart", listCart);
		
		// Trả về view
		model.addAttribute("bill", new BillEntity());
		model.addAttribute("account", account);
		model.addAttribute("nameDiscount", discountName);
		model.addAttribute("totalMoney", moneyNew);
		model.addAttribute("discount", moneyDiscount);
		model.addAttribute("shipping", shipping);
		model.addAttribute("intoMoney", intoMoney);
		
		
		return "user/checkout";
	}
	
	
}
