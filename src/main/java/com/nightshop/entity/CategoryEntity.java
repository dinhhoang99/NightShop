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
@Table(name = "category")
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "categorye_name")
	private String categoryName;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "images")
	private String images;
	
	@OneToMany(mappedBy = "category")
	List<ProductEntity> product;
	

	public CategoryEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CategoryEntity(int id, String categoryName, boolean isActive, String images) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.isActive = isActive;
		this.images = images;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public String getImages() {
		return images;
	}


	public void setImages(String images) {
		this.images = images;
	}


	public List<ProductEntity> getProduct() {
		return product;
	}


	public void setProduct(List<ProductEntity> product) {
		this.product = product;
	}

	
	
}
