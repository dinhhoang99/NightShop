package com.nightshop.entity;

import java.util.Comparator;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class AccountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "fullname")
	private String fullname;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "dia_chi")
	private String diachi;
	
	@Column(name = "sdt")
	private String sdt;
	
	@Column(name = "images")
	private String images;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "is_admin")
	private boolean isAdmin;

	@OneToMany(mappedBy = "account")
	List<CartEntity> cart;
	
	@OneToMany(mappedBy = "account")
	List<BillEntity> bill;
	
	@OneToMany(mappedBy = "account")
	List<FavoriteEntity> favorite;
	
	public AccountEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public AccountEntity(int id, String fullname, String username, String password, String email, String diachi,
			String sdt, String images, boolean isActive, boolean isAdmin) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.diachi = diachi;
		this.sdt = sdt;
		this.images = images;
		this.isActive = isActive;
		this.isAdmin = isAdmin;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<CartEntity> getCart() {
		return cart;
	}

	public void setCart(List<CartEntity> cart) {
		this.cart = cart;
	}
	
	
}
