package com.nightshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_color")
public class Product_ColorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private int id;
	
	@JoinColumn(name = "is_active")
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	ProductEntity product;
	
	@ManyToOne
	@JoinColumn(name = "id_color")
	ColorEntity color;

	public Product_ColorEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product_ColorEntity(int id, ProductEntity product, ColorEntity color) {
		super();
		this.id = id;
		this.product = product;
		this.color = color;
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

	public ColorEntity getColor() {
		return color;
	}

	public void setColor(ColorEntity color) {
		this.color = color;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
