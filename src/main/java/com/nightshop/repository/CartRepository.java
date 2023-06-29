package com.nightshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nightshop.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Integer>{

	@Query("SELECT c FROM CartEntity c WHERE c.account.id = :idAccount")
	List<CartEntity> findAll(@Param("idAccount") int idAccount);
	
	CartEntity findById(int id);

}
