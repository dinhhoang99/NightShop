package com.nightshop.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "color")
public class ColorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "color_name")
	private String colorname;
	
	@Column(name = "is_Active")
	private boolean isActive;

	@OneToMany(mappedBy = "color")
	List<Product_ColorEntity> productColor;
	
	@OneToMany(mappedBy = "color")
	List<CartEntity> cart;
	
	@OneToMany(mappedBy = "color")
	List<FavoriteEntity> color;
	
	@OneToMany(mappedBy = "color")
	List<DetailedInvoiceEntity> detailedinvoice;  
	
	public ColorEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ColorEntity(int id, String colorname, boolean isActive) {
		super();
		this.id = id;
		this.colorname = colorname;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColorname() {
		return colorname;
	}

	public void setColorname(String colorname) {
		this.colorname = colorname;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<Product_ColorEntity> getProductColor() {
		return productColor;
	}

	public void setProductColor(List<Product_ColorEntity> productColor) {
		this.productColor = productColor;
	}
	
	
}
