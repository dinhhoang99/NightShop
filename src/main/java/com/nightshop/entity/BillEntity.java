package com.nightshop.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "bill")
public class BillEntity {

	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "UNIQUEIDENTIFIER")
    private String id;
	
	@JoinColumn(name = "recipient_name")
	private String recipientName;
	
	@JoinColumn(name = "sdt_recipient")
	private String sdtRecipient;
	
	@JoinColumn(name = "email")
	private String email;
	
	@JoinColumn(name = "addres")
	private String addres;
	
	@JoinColumn(name = "date_created")
	private LocalDate dateCreated;
	
	@JoinColumn(name = "delivery_date")
	private LocalDate deliveryDate;
	
	@JoinColumn(name = "received_date")
	private LocalDate receivedDate;
	
	@JoinColumn(name = "discount_amount")
	private BigDecimal discountAmount;
	
	@JoinColumn(name = "shipping_fee")
	private BigDecimal shippingFee;
	
	@JoinColumn(name = "total_money")
	private BigDecimal totalMoney;
	
	@JoinColumn(name = "into_money")
	private BigDecimal intoMoney;
	
	@JoinColumn(name = "is_active")
	private int isActive;
	
	@JoinColumn(name = "quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "id_account")
	AccountEntity account;
	
	@ManyToOne
	@JoinColumn(name = "id_discount")
	DiscountEntity discount;

	
	@OneToMany(mappedBy = "bill")
	List<DetailedInvoiceEntity> detailedInvoice;
	
	public BillEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BillEntity(String id, String recipientName, String sdtRecipient, String email, String addres,
			LocalDate dateCreated, LocalDate deliveryDate, LocalDate receivedDate, BigDecimal discountAmount,
			BigDecimal shippingFee, BigDecimal totalMoney, BigDecimal intoMoney, int isActive, AccountEntity account,
			DiscountEntity discount, List<DetailedInvoiceEntity> detailedInvoice) {
		super();
		this.id = id;
		this.recipientName = recipientName;
		this.sdtRecipient = sdtRecipient;
		this.email = email;
		this.addres = addres;
		this.dateCreated = dateCreated;
		this.deliveryDate = deliveryDate;
		this.receivedDate = receivedDate;
		this.discountAmount = discountAmount;
		this.shippingFee = shippingFee;
		this.totalMoney = totalMoney;
		this.intoMoney = intoMoney;
		this.isActive = isActive;
		this.account = account;
		this.discount = discount;
		this.detailedInvoice = detailedInvoice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getSdtRecipient() {
		return sdtRecipient;
	}

	public void setSdtRecipient(String sdtRecipient) {
		this.sdtRecipient = sdtRecipient;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddres() {
		return addres;
	}

	public void setAddres(String addres) {
		this.addres = addres;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public LocalDate getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(BigDecimal shippingFee) {
		this.shippingFee = shippingFee;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getIntoMoney() {
		return intoMoney;
	}

	public void setIntoMoney(BigDecimal intoMoney) {
		this.intoMoney = intoMoney;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

	public DiscountEntity getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountEntity discount) {
		this.discount = discount;
	}

	public List<DetailedInvoiceEntity> getDetailedInvoice() {
		return detailedInvoice;
	}

	public void setDetailedInvoice(List<DetailedInvoiceEntity> detailedInvoice) {
		this.detailedInvoice = detailedInvoice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
}
