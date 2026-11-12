package com.sagun.expense_tracker_api.service;

import com.sagun.expense_tracker_api.customExceptions.UserNotFoundException;
import com.sagun.expense_tracker_api.dto.DailyExpenseDTO;
import com.sagun.expense_tracker_api.dto.MonthlyReportDTO;
import com.sagun.expense_tracker_api.entity.Expense;
import com.sagun.expense_tracker_api.entity.User;
import com.sagun.expense_tracker_api.principal.UserPrincipal;
import com.sagun.expense_tracker_api.repository.ExpenseRepository;
import com.sagun.expense_tracker_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonthlyReportService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public MonthlyReportDTO generateMonthlyReport(UserPrincipal principal, int year, int month){
        User user = userRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException("User does not exists"));
        Long userId = user.getId();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        BigDecimal totalExpense = expenseRepository.getTotalExpense(userId, startDate, endDate);
        long totalTransactions = expenseRepository.countExpenses(userId, startDate, endDate);
        BigDecimal averageExpense = totalTransactions == 0
                ? BigDecimal.ZERO
                : totalExpense.divide(
                BigDecimal.valueOf(totalTransactions), 2,
                RoundingMode.HALF_UP
        );
        BigDecimal highestExpense = expenseRepository.getHighestExpense(userId, startDate, endDate);
        BigDecimal lowestExpense = expenseRepository.getLowestExpense(userId, startDate, endDate);
        List<Object[]> categoryResults = expenseRepository.getCategoryBreakdown(userId, startDate, endDate);
        Map<String, BigDecimal> categoryBreakdown = categoryResults.stream()
                .collect(Collectors.toMap(
                        row -> ((Expense.Category) row[0]).name(),
                        row -> (BigDecimal) row[1]
                ));
        List<Object[]> dailyResults = expenseRepository.getDailyBreakDown(userId, startDate, endDate);
        List<DailyExpenseDTO> dailyBreakdown = dailyResults.stream()
                .map(
                        row -> new DailyExpenseDTO((LocalDate) row[0], (BigDecimal) row[1])
                ).toList();
        LocalDate previousMonthStart = startDate.minusMonths(1);
        LocalDate previousMonthEnd = previousMonthStart.withDayOfMonth(previousMonthStart.lengthOfMonth());
        BigDecimal previousMonthExpense = expenseRepository.getTotalExpense(userId, previousMonthStart, previousMonthEnd);
        BigDecimal expenseDifference = totalExpense.subtract(previousMonthExpense);
        double percentageChange = previousMonthExpense.compareTo(BigDecimal.ZERO) == 0
                ? (totalExpense.compareTo(BigDecimal.ZERO) == 0 ? 0 : 100)
                : expenseDifference.multiply(BigDecimal.valueOf(100))
                  .divide(
                          previousMonthExpense,
                          2,
                          RoundingMode.HALF_UP
                  ).doubleValue();
        return new MonthlyReportDTO(year,
                month,
                totalExpense,
                totalTransactions,
                averageExpense,
                highestExpense,
                lowestExpense,
                previousMonthExpense,
                expenseDifference,
                percentageChange,
                categoryBreakdown,
                dailyBreakdown);
    }

}
