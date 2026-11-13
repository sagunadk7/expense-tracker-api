package com.sagun.expense_tracker_api.controller;

import com.sagun.expense_tracker_api.dto.ExpenseRequestDto;
import com.sagun.expense_tracker_api.dto.ExpenseResponseDto;
import com.sagun.expense_tracker_api.dto.MonthlyReportDTO;
import com.sagun.expense_tracker_api.entity.Expense;
import com.sagun.expense_tracker_api.principal.UserPrincipal;
import com.sagun.expense_tracker_api.service.MonthlyReportService;
import com.sagun.expense_tracker_api.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService service;
    private final MonthlyReportService monthlyReportService;

    @PostMapping
    public ExpenseResponseDto createExpense(@Valid  @RequestBody ExpenseRequestDto dto, @AuthenticationPrincipal UserPrincipal principal){
        return service.addExpense(dto,principal);
    }

    @GetMapping
    public Page<ExpenseResponseDto> getExpenses(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam int month,
            @RequestParam int year,
            Pageable pageable
    ){
        return service.getExpensesByMonthAndYear(principal,month,year,pageable);
    }

    @GetMapping("/monthly/{year}/{month}")
    public MonthlyReportDTO getMonthlyReport(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable int year,
            @PathVariable int month
    ){
        return monthlyReportService.generateMonthlyReport(principal,year,month);
    }

    @GetMapping("/categories")
    public List<Expense.Category> getCategories(){
        return Arrays.stream(Expense.Category.values()).toList();
    }
}
