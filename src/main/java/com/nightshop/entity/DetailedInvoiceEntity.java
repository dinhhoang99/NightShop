package com.nightshop.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detailed_invoice")
public class DetailedInvoiceEntity {

	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "UNIQUEIDENTIFIER")
    private String id;
	
	@JoinColumn(name = "quantity")
	private int quantity;
	
	@JoinColumn(name = "unit_price")
	private BigDecimal unitPrice;
	
	@JoinColumn(name = "total_money")
	private BigDecimal totalMoney;
	
	@JoinColumn(name = "is_active")
	private int isActive;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	ProductEntity product;
	
	@ManyToOne
	@JoinColumn(name = "id_color")
	ColorEntity color;
	
	@ManyToOne
	@JoinColumn(name = "id_size")
	SizeEntity size;
	
	@ManyToOne
	@JoinColumn(name = "id_bill")
	BillEntity bill;

	public DetailedInvoiceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DetailedInvoiceEntity(String id, int quantity, BigDecimal unitPrice, BigDecimal totalMoney, int isActive,
			ProductEntity product, BillEntity bill) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalMoney = totalMoney;
		this.isActive = isActive;
		this.product = product;
		this.bill = bill;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quatity) {
		this.quantity = quatity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public BillEntity getBill() {
		return bill;
	}

	public void setBill(BillEntity bill) {
		this.bill = bill;
	}

	public ColorEntity getColor() {
		return color;
	}

	public void setColor(ColorEntity color) {
		this.color = color;
	}

	public SizeEntity getSize() {
		return size;
	}

	public void setSize(SizeEntity size) {
		this.size = size;
	}
	
	
}
