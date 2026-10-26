package com.sagun.expense_tracker_api.entity;

import jakarta.persistence.Entity;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends BaseEntity{

    private String note;
    private String paymentMode;
    private String category;
    private long income;
    private long expense;
    private long Balance;

}
