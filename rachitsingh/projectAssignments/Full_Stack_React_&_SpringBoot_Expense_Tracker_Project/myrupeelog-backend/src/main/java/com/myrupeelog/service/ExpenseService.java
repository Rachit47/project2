package com.myrupeelog.service;

import java.util.List;

import com.myrupeelog.dto.CategoryStatsDTO;
import com.myrupeelog.dto.MonthlyExpenseDTO;
import com.myrupeelog.model.Expense;

public interface ExpenseService {
	void addExpense(Expense expense);

	List<Expense> getAllExpenses();

	Double getBiggestExpense();

	String getLastAddedExpenseTitle();

	Double getTotalSpent();

	Integer getTotalEntries();

	List<MonthlyExpenseDTO> getMonthlyExpenseStats();

	List<CategoryStatsDTO> getCategoryWiseStats();
}
