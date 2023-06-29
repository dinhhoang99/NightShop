package com.nightshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nightshop.entity.DiscountEntity;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Integer>{

	@Query("SELECT d FROM DiscountEntity d ORDER BY d.isActive DESC")
	List<DiscountEntity> findAll();
	
	@Query("SELECT d FROM DiscountEntity d WHERE d.nameDiscount = ?1")
	DiscountEntity findByName(String name);
	
	@Query("SELECT d FROM DiscountEntity d WHERE d.id = ?1")
	DiscountEntity findById(int id);
}
