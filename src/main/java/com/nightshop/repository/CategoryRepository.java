package com.nightshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nightshop.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>{
	CategoryEntity findById(int id);
}
