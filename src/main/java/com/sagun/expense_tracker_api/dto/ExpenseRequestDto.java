package com.sagun.expense_tracker_api.dto;

import com.sagun.expense_tracker_api.entity.Expense;
import com.sagun.expense_tracker_api.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequestDto(
        @NotNull
        @NotBlank
        @Size(max = 100)
        String title,

        @NotNull
        @NotBlank
        @Size(max = 255)
        String description,

        @NotNull
        Expense.Category category,

        @NotNull
        @Positive
        BigDecimal amount,

        LocalDate expenseDate
) {
    public static ExpenseRequestDto from(Expense expense){
        return new ExpenseRequestDto(expense.getTitle(),expense.getDescription(),expense.getCategory(),expense.getAmount(),expense.getExpenseDate());
    }

    public static Expense toExpense(ExpenseRequestDto requestDto, User user){
        return new Expense(requestDto.title(),requestDto.description(),requestDto.category(),requestDto.amount(),requestDto.expenseDate(),user);
    }

}
