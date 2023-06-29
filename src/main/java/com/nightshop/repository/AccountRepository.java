package com.nightshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nightshop.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer>{
	@Query("SELECT a FROM AccountEntity a ORDER BY a.isActive DESC")
	List<AccountEntity> findAll();
	AccountEntity findById(int id);
	AccountEntity findByUsername(String username);
	AccountEntity findByEmail(String email);
}
