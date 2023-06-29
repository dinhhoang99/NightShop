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
@Table(name = "size")
public class SizeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "size_name")
	private String sizeName;
	
	@Column(name = "is_active")
	private boolean isActive;

	@OneToMany(mappedBy = "size")
	List<Product_SizeEntity> productSize; 
	
	@OneToMany(mappedBy = "size")
	List<CartEntity> cart;  
	
	@OneToMany(mappedBy = "size")
	List<FavoriteEntity> favorite;
	
	@OneToMany(mappedBy = "size")
	List<DetailedInvoiceEntity> detailedinvoice;  
	
	public SizeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SizeEntity(int id, String sizeName, boolean isActive) {
		super();
		this.id = id;
		this.sizeName = sizeName;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<Product_SizeEntity> getProductSize() {
		return productSize;
	}

	public void setProductSize(List<Product_SizeEntity> productSize) {
		this.productSize = productSize;
	}
	
	
}
