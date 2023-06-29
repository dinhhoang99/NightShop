package com.nightshop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nightshop.entity.AccountEntity;
import com.nightshop.entity.BillEntity;
import com.nightshop.entity.CartEntity;
import com.nightshop.entity.DetailedInvoiceEntity;
import com.nightshop.entity.DiscountEntity;
import com.nightshop.repository.AccountRepository;
import com.nightshop.repository.BillRepository;
import com.nightshop.repository.CartRepository;
import com.nightshop.repository.DiscountRepository;
import com.nightshop.repository.InvoiceRepository;
import com.nightshop.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/index")
public class CheckoutController {
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private DiscountRepository distcountRepo;
	
	@Autowired
	private BillRepository billRepo;
	
	@Autowired
	private InvoiceRepository invoiceRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@SuppressWarnings("unchecked")
	
	@PostMapping("/checkout/submit")
	public String submitCheckout(Model model,HttpSession session, @Valid @ModelAttribute("bill") BillEntity bill) {
		BillEntity billEntity = new BillEntity();
		
		// Lấy Giá Trị Từ Session
		BigDecimal totalMoney = (BigDecimal) session.getAttribute("totalMoney");
		double shipping = (double) session.getAttribute("shipping");
		String discountName = (String) session.getAttribute("discountName");
		double intoMoney = (double) session.getAttribute("intoMoney");
		
		AccountEntity account  = (AccountEntity) session.getAttribute("Account");
		List<CartEntity> listCart = (List<CartEntity>) session.getAttribute("cart");
		DiscountEntity discount = distcountRepo.findByName(discountName);
		
		
		BigDecimal moneyDiscount = BigDecimal.ZERO;
		BigDecimal percent = BigDecimal.valueOf(100);
		if(discount == null) {
			discount = distcountRepo.findByName("VC0");
		}else {
			int discountCondition = discount.getDiscountConditions();
			moneyDiscount = totalMoney.divide(percent).multiply(BigDecimal.valueOf(discountCondition));
		}
		// Set dữ liệu chi BillEntity
		billEntity.setRecipientName(bill.getRecipientName());
		billEntity.setSdtRecipient(bill.getSdtRecipient());
		billEntity.setEmail(bill.getEmail());
		billEntity.setAddres(bill.getAddres());
		billEntity.setDateCreated(LocalDate.now());
		billEntity.setDiscountAmount(moneyDiscount);
		billEntity.setShippingFee(BigDecimal.valueOf(shipping));
		billEntity.setTotalMoney(totalMoney);
		billEntity.setIntoMoney(BigDecimal.valueOf(intoMoney));
		billEntity.setIsActive(1);
		billEntity.setQuantity(listCart.size());
		billEntity.setAccount(account);
		billEntity.setDiscount(discount);
		// Thêm đối tường vào bảng
		billRepo.save(billEntity);
		// Set và thêm dữ liệu cho bảng DetailedInvoiceEntity
		
		for(CartEntity c: listCart) {
			DetailedInvoiceEntity detailedInvoiceEntity = new DetailedInvoiceEntity();
			detailedInvoiceEntity.setQuantity(c.getQuantity());
			detailedInvoiceEntity.setUnitPrice(c.getProduct().getImportPrice());
			BigDecimal quantity = BigDecimal.valueOf(c.getQuantity());
			BigDecimal totalMoneyNew = quantity.multiply(c.getProduct().getImportPrice());
			detailedInvoiceEntity.setTotalMoney(totalMoneyNew);
			detailedInvoiceEntity.setIsActive(1);
			detailedInvoiceEntity.setProduct(c.getProduct());
			detailedInvoiceEntity.setBill(billEntity);
			detailedInvoiceEntity.setColor(c.getColor());
			detailedInvoiceEntity.setSize(c.getSize());
			
			invoiceRepo.save(detailedInvoiceEntity);
			c.getProduct().setQuantity(c.getProduct().getQuantity() - 1);
			productRepo.save(c.getProduct());
			cartRepo.delete(c);
		}
		
		return "redirect:/index";
	}
	
	
}
