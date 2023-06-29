package com.nightshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nightshop.entity.ColorEntity;

public interface ColorRepository extends JpaRepository<ColorEntity, Integer>{
	ColorEntity findById(int id);
	
}
