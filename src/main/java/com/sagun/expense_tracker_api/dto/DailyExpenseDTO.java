package com.sagun.expense_tracker_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyExpenseDTO(LocalDate date, BigDecimal totalExpense) {
}
