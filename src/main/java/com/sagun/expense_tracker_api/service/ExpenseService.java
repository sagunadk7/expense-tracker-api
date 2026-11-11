package com.sagun.expense_tracker_api.service;

import com.sagun.expense_tracker_api.customExceptions.UserNotFoundException;
import com.sagun.expense_tracker_api.dto.ExpenseRequestDto;
import com.sagun.expense_tracker_api.dto.ExpenseResponseDto;
import com.sagun.expense_tracker_api.entity.Expense;
import com.sagun.expense_tracker_api.entity.User;
import com.sagun.expense_tracker_api.principal.UserPrincipal;
import com.sagun.expense_tracker_api.repository.ExpenseRepository;
import com.sagun.expense_tracker_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseResponseDto addExpense(ExpenseRequestDto requestDto, UserPrincipal userPrincipal){
        User user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(() -> new UserNotFoundException("User does not exists"));
        Expense expense = ExpenseRequestDto.toExpense(requestDto, user);
        Expense expense1 = expenseRepository.save(expense);
        return ExpenseResponseDto.from(expense1);
    }

    public Page<ExpenseResponseDto> getExpensesByMonthAndYear(UserPrincipal principals,int month, int year, Pageable pageable){
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        return expenseRepository.findByUserIdAndExpenseDateBetween(principals.getId(),startOfMonth,endOfMonth,pageable).map(
                ExpenseResponseDto::from
        );
    }

    public void monthlyReports(UserPrincipal principal){



    }

}
