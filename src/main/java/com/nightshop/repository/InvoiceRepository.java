package com.nightshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nightshop.entity.DetailedInvoiceEntity;

public interface InvoiceRepository extends JpaRepository<DetailedInvoiceEntity, String>{

}
