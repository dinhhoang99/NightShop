package com.nightshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nightshop.entity.DetailedInvoiceEntity;
import com.nightshop.entity.ProductEntity;

public interface DetailedInvoiceRepository extends JpaRepository<DetailedInvoiceEntity, String>{

	@Query("SELECT d FROM DetailedInvoiceEntity d WHERE d.bill.id = ?1")
	List<DetailedInvoiceEntity> findByBillId(String id);
	
	@Query("SELECT d FROM DetailedInvoiceEntity d WHERE d.id = ?1")
	DetailedInvoiceEntity findByid(String id);
	
	
}
