package com.nightshop.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private int id;
	
	@JoinColumn(name = "product_name")
	private String productName;
	
	@JoinColumn(name = "import_price")
	private BigDecimal importPrice;
	
	@JoinColumn(name = "price")
	private BigDecimal price;
	
	@JoinColumn(name = "quantity")
	private int quantity;
	
	@JoinColumn(name = "description")
	private String description;
	
	@JoinColumn(name = "is_active")
	private Boolean isActive;
	
	@JoinColumn(name = "images")
	private String images;
	
	@ManyToOne()
	@JoinColumn(name = "id_danhmuc")
	CategoryEntity category;
	
	@OneToMany(mappedBy = "product")
	List<DetailedInvoiceEntity> detailedInvoice;
	
	@OneToMany(mappedBy = "product")
	List<Product_ColorEntity> productColor;
	
	@OneToMany(mappedBy = "product")
	List<Product_SizeEntity> productSize;
	
	@OneToMany(mappedBy = "product")
	List<FavoriteEntity> favorite;

	public ProductEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductEntity(int id, String productName, BigDecimal importPrice, BigDecimal price, int quantity,
			String description, Boolean isActive, CategoryEntity category) {
		super();
		this.id = id;
		this.productName = productName;
		this.importPrice = importPrice;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.isActive = isActive;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getImportPrice() {
		return importPrice;
	}

	public void setImportPrice(BigDecimal importPrice) {
		this.importPrice = importPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public List<DetailedInvoiceEntity> getDetailedInvoice() {
		return detailedInvoice;
	}

	public void setDetailedInvoice(List<DetailedInvoiceEntity> detailedInvoice) {
		this.detailedInvoice = detailedInvoice;
	}

	public List<Product_ColorEntity> getProductColor() {
		return productColor;
	}

	public void setProductColor(List<Product_ColorEntity> productColor) {
		this.productColor = productColor;
	}

	public List<Product_SizeEntity> getProductSize() {
		return productSize;
	}

	public void setProductSize(List<Product_SizeEntity> productSize) {
		this.productSize = productSize;
	}

	public List<FavoriteEntity> getFavorite() {
		return favorite;
	}

	public void setFavorite(List<FavoriteEntity> favorite) {
		this.favorite = favorite;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
	
	
}
