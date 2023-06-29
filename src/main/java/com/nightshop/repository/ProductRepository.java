package com.nightshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nightshop.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
	
	@Query("SELECT p FROM ProductEntity p WHERE p.isActive = 1 ORDER BY p.isActive DESC")
	List<ProductEntity> findAll();
	
	@Query("SELECT p FROM ProductEntity p WHERE p.isActive = 1 AND p.quantity > 0")
	List<ProductEntity> findByIsActive();
	
	@Query("SELECT p FROM ProductEntity p WHERE p.category.id = ?1 AND p.isActive = 1")
	List<ProductEntity> findByCategoryId(int id);
	
	@Query("SELECT p FROM ProductEntity p WHERE p.productName LIKE %?1%")
	List<ProductEntity> searchProduct(String productName);
	
	ProductEntity findById(int id);
}
