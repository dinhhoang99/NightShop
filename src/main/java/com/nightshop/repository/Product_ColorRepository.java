package com.nightshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nightshop.entity.Product_ColorEntity;
import com.nightshop.entity.Product_SizeEntity;

public interface Product_ColorRepository extends JpaRepository<Product_ColorEntity, Integer>{
	
	List<Product_ColorEntity> findAllByOrderByIsActiveDesc();
	
	@Query("SELECT pc FROM Product_ColorEntity pc WHERE pc.product.id = ?1 AND pc.isActive = 1")
	List<Product_ColorEntity> findByProductId(int id);
	
	@Query("SELECT pc FROM Product_ColorEntity pc WHERE pc.product.productName LIKE %?1%")
	List<Product_ColorEntity> findByKeyWord(String keyword);
}
