package com.sagun.expense_tracker_api.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record MonthlyReportDTO(
        int year,
        int month,
        BigDecimal totalExpense,
        long totalTransactions,
        BigDecimal averageExpense,
        BigDecimal highestExpense,
        BigDecimal lowestExpense,
        BigDecimal previousMonthExpense,
        BigDecimal expenseDifference,
        Double percentageChange,
        Map<String, BigDecimal> categoryBreakdown,
        List<DailyExpenseDTO> dailyBreakDown

) {
}
