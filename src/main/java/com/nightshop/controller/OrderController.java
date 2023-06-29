package com.nightshop.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nightshop.entity.AccountEntity;
import com.nightshop.entity.BillEntity;
import com.nightshop.entity.DetailedInvoiceEntity;
import com.nightshop.entity.ProductEntity;
import com.nightshop.repository.BillRepository;
import com.nightshop.repository.DetailedInvoiceRepository;
import com.nightshop.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/index")
public class OrderController {
	
	@Autowired()
	private BillRepository billRepo;
	
	@Autowired
	private DetailedInvoiceRepository detailedInvoiceRepo;
	
	@Autowired
	private ProductRepository prRepo;
	
	@GetMapping("/order")
	public String index(HttpSession session, Model model) {
		AccountEntity account = (AccountEntity) session.getAttribute("Account");
		
		if(account ==  null) {
			return "redirect:/login";
		}else {
			model.addAttribute("show", true);
			List<BillEntity> listBill = billRepo.findByAccountId(account.getId());
			model.addAttribute("listBill", listBill);
		}
		return "user/order";
	}
	
	@GetMapping("/order/detailtbill/{id}")
	private String pageDetailtBill(Model model, @PathVariable(name = "id") String id) {
		
		List<DetailedInvoiceEntity> listDetailBill = detailedInvoiceRepo.findByBillId(id);
		
		model.addAttribute("dtBill", listDetailBill);
		
		model.addAttribute("showDetail", true);
		return "user/order";
	}
	
	
	@GetMapping("/order/received/{id}")
	public String pageReceived(Model model, @PathVariable(name = "id") String id) {
		BillEntity billDelivery = billRepo.findBillByID(id);
		List<DetailedInvoiceEntity> listDetailed = detailedInvoiceRepo.findByBillId(id);
		billDelivery.setIsActive(3);
		billDelivery.setReceivedDate(LocalDate.now());
		for(DetailedInvoiceEntity d : listDetailed) {
			d.setIsActive(3);
			
			detailedInvoiceRepo.save(d);
		}
		billRepo.save(billDelivery);
		return "redirect:/index/order";
	}
	
	@GetMapping("/order/invoice/{id}")
	public String pageInvoice(Model model, @PathVariable(name = "id") String id) {
		BillEntity invoice = billRepo.findBillByID(id);
		List<DetailedInvoiceEntity> detailedInvoice = detailedInvoiceRepo.findByBillId(id);
		model.addAttribute("invoice", invoice);
		model.addAttribute("detailedInvoice", detailedInvoice);
		return "invoice";
	}
	
	@GetMapping("/order/deleteinvoice/{id}")
	public String pageDeleteInvoice(Model model, @PathVariable(name = "id") String id) {
		BillEntity billDelivery = billRepo.findBillByID(id);
		List<DetailedInvoiceEntity> listDetailed = detailedInvoiceRepo.findByBillId(id);
		billDelivery.setIsActive(4);
		
		for(DetailedInvoiceEntity d : listDetailed) {
			ProductEntity product = d.getProduct();
			product.setQuantity(d.getQuantity() + product.getQuantity());
			d.setIsActive(4);
			detailedInvoiceRepo.save(d);
			prRepo.save(product);
		}
		billRepo.save(billDelivery);
		return "redirect:/index/order";
	}
	
	@GetMapping("/order/repurchase/{id}")
	public String pageRepurchase(@PathVariable(name = "id") String id, Model model) {
		List<DetailedInvoiceEntity> dtInvoice = detailedInvoiceRepo.findByBillId(id);
		
		model.addAttribute("listCart", dtInvoice);
		
		return "/user/cart";
	}
}
