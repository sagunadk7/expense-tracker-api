package com.sagun.expense_tracker_api.dto;

import com.sagun.expense_tracker_api.entity.Expense;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ExpenseResponseDto(

        Long id,
        String title,
        String description,
        Expense.Category category,
        BigDecimal amount,
        LocalDate expenseDate
) {
    public static ExpenseResponseDto from(Expense expense){
        return new ExpenseResponseDto(expense.getId(),
                expense.getTitle(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getAmount(),
                expense.getExpenseDate());
    }
}
