package com.nightshop.controller.admin;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nightshop.entity.AccountEntity;
import com.nightshop.entity.BillEntity;
import com.nightshop.entity.DetailedInvoiceEntity;
import com.nightshop.repository.BillRepository;
import com.nightshop.repository.DetailedInvoiceRepository;
import com.nightshop.sender.EmailSender;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class BillAdminController {

	private final JavaMailSender emailSender;
	 
	public BillAdminController(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	 
	@Autowired
	private BillRepository billRepo;
	
	@Autowired
	private DetailedInvoiceRepository detailedBillRepo;
	
	@GetMapping("billwaiting")
	public String pageBillWaiting(Model model, HttpSession session) {
		
		List<BillEntity> listBillWaiting = billRepo.findByIsActive(1);
		
		model.addAttribute("listBill", listBillWaiting);
		return "admin/order";
	}
	
	@GetMapping("billwaiting/detailed/{id}")
	public String pageDetailedBill(Model model, @PathVariable(name = "id") String id) {
		List<DetailedInvoiceEntity> detailedBill = detailedBillRepo.findByBillId(id);
		model.addAttribute("detailedBill", detailedBill);
		return "admin/detailedbill";
	}
	
	@GetMapping("/billwaiting/delivery/{id}")
	public String submitDelivery(Model model, @PathVariable(name = "id") String id, HttpSession session) {
		
		BillEntity billDelivery = billRepo.findBillByID(id);
		billDelivery.setIsActive(2);
		billDelivery.setDeliveryDate(LocalDate.now());
		billRepo.save(billDelivery);
		
		// Gửi email thông báo
		String to = billDelivery.getEmail();
        String subject = "Đơn Hàng Đã Được Gửi";
        String body = "Đơn Hàng Của Bạn Đã Được Giao Cho Đơn Vị Vận Chuyển. Cảm Ơn Bạn Đã Ủng Hộ NightShop. Kiểm Tra Đơn Hàng Của Bạn Tại : http://localhost:8080/index/order";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
		return "redirect:/admin/billwaiting";
	}
	
	@GetMapping("/delivering")
	public String pageDelivering(Model model) {
		
		List<BillEntity> listBillWaiting = billRepo.findByIsActive(2);
		
		model.addAttribute("listBill", listBillWaiting);
		return "admin/order";
	}
	
	@GetMapping("/delivered")
	public String pageDelivered(Model model) {
		
		List<BillEntity> listBillWaiting = billRepo.findByIsActive(3);
		
		model.addAttribute("listBill", listBillWaiting);
		return "admin/order";
	}
}
