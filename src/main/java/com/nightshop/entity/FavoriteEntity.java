package com.nightshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorite")
public class FavoriteEntity {

	@Id
	@JoinColumn(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "idAccount")
	AccountEntity account; 
	
	@ManyToOne
	@JoinColumn(name = "idProduct")
	ProductEntity product;
	
	@ManyToOne
	@JoinColumn(name = "idSize")
	SizeEntity size;
	
	@ManyToOne
	@JoinColumn(name = "idColor")
	ColorEntity color;
	
	public FavoriteEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FavoriteEntity(int id, AccountEntity account, ProductEntity product) {
		super();
		this.id = id;
		this.account = account;
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
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

	public ColorEntity getColor() {
		return color;
	}

	public void setColor(ColorEntity color) {
		this.color = color;
	}

	
}
