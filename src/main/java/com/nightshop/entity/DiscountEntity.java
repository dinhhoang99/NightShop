package com.nightshop.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Discount")
public class DiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "nameDiscount")
    private String nameDiscount;

    @JoinColumn(name = "startDay")
    private Date startDay;

    @JoinColumn(name = "endDate")
    private Date endDate;

    @JoinColumn(name = "percentDiscount")
    private int percentDiscount;

    @JoinColumn(name = "discountConditions")
    private int discountConditions;

    @JoinColumn(name = "isActive")
    private boolean isActive = true;

    @OneToMany(mappedBy = "discount")
    List<BillEntity> bill;

    public DiscountEntity() {
    }

    public DiscountEntity(String nameDiscount, Date startDay, Date endDate, int percentDiscount, int discountConditions) {
        this.nameDiscount = nameDiscount;
        this.startDay = startDay;
        this.endDate = endDate;
        this.percentDiscount = percentDiscount;
        this.discountConditions = discountConditions;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameDiscount() {
        return nameDiscount;
    }

    public void setNameDiscount(String nameDiscount) {
        this.nameDiscount = nameDiscount;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(int percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public int getDiscountConditions() {
        return discountConditions;
    }

    public void setDiscountConditions(int discountConditions) {
        this.discountConditions = discountConditions;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

	public List<BillEntity> getBill() {
		return bill;
	}

	public void setBill(List<BillEntity> bill) {
		this.bill = bill;
	}
    
    
}

