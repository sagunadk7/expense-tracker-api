package com.sagun.expense_tracker_api.repository;

import com.sagun.expense_tracker_api.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    Page<Expense> findByUserIdAndExpenseDateBetween(Long UserId,LocalDate start, LocalDate end, Pageable pageable);

}
