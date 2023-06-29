package com.nightshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nightshop.entity.SizeEntity;

public interface SizeRepository extends JpaRepository<SizeEntity, Integer>{
	SizeEntity findById (int id);
}
