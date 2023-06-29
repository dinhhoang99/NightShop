package com.nightshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nightshop.entity.BillEntity;

public interface BillRepository extends JpaRepository<BillEntity, String>{

	
	List<BillEntity> findByIsActive(int isActive);
	
	@Query("SELECT b FROM BillEntity b WHERE b.id = :id")
	BillEntity findBillByID(@Param("id") String id);
	
	@Query("SELECT b FROM BillEntity b WHERE b.account.id = :idAccount ORDER BY b.isActive ASC")
	List<BillEntity> findByAccountId(int idAccount);
}
