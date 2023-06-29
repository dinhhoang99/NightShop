package com.nightshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nightshop.entity.Product_SizeEntity;

public interface Product_SizeRepository extends JpaRepository<Product_SizeEntity, Integer>{

	List<Product_SizeEntity> findAllByOrderByIsActiveDesc();
	
	@Query("SELECT ps FROM Product_SizeEntity ps WHERE ps.product.id = ?1 AND ps.isActive = 1")
	List<Product_SizeEntity> findByProductId(int id);
	
	@Query("SELECT ps FROM Product_SizeEntity ps WHERE ps.product.productName LIKE %?1%")
	List<Product_SizeEntity> findByKeyWord(String keyword);
}
