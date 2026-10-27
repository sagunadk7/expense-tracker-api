package com.sagun.expense_tracker_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends BaseEntity{

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false,length = 100)
    private String title;

    @Size(max = 255)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private Category category;

    @NotNull
    @Positive
    @Column(nullable = false,precision = 12, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(updatable = false)
    private Instant expenseDate;

    public enum Category{
        FOOD,RENT,TRANSPORT,UTILITIES,ENTERTAINMENT,HEALTH,SHOPPING,OTHER
    }

}
