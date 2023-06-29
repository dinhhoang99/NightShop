package com.nightshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_size")
public class Product_SizeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private int id;
	
	@JoinColumn(name = "is_active")
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	ProductEntity product;
	
	@ManyToOne()
	@JoinColumn(name = "id_size")
	SizeEntity size;

	public Product_SizeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product_SizeEntity(int id, ProductEntity product, SizeEntity size) {
		super();
		this.id = id;
		this.product = product;
		this.size = size;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public SizeEntity getSize() {
		return size;
	}

	public void setSize(SizeEntity size) {
		this.size = size;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
