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
import org.springframework.stereotype.Service;

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

}
