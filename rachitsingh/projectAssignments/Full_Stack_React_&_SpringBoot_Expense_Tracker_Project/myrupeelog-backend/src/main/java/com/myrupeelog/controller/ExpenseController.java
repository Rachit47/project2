package com.myrupeelog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myrupeelog.dto.CategoryStatsDTO;
import com.myrupeelog.dto.MonthlyExpenseDTO;
import com.myrupeelog.model.Expense;
import com.myrupeelog.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {
	private final ExpenseService expenseService;

	@Autowired
	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@GetMapping("/all")
	public List<Expense> getAllExpenses() {
		return expenseService.getAllExpenses();
	}

	@PostMapping("/add")
	public String addExpense(@RequestBody Expense expense) {
		expenseService.addExpense(expense);
		return "Expense added successfully.";
	}

	@GetMapping("/total-spent")
	public Double getTotalSpent() {
		return expenseService.getTotalSpent();
	}

	@GetMapping("/total-entries")
	public Integer getTotalEntries() {
		return expenseService.getTotalEntries();
	}

	@GetMapping("/biggest-expense")
	public Double getBiggestExpense() {
		return expenseService.getBiggestExpense();
	}

	@GetMapping("/last-added")
	public String getLastAddedExpenseTitle() {
		return expenseService.getLastAddedExpenseTitle();
	}

	@GetMapping("/monthly-stats")
	public List<MonthlyExpenseDTO> getMonthlyExpenseStats() {
		return expenseService.getMonthlyExpenseStats();
	}

	@GetMapping("/category-stats")
	public List<CategoryStatsDTO> getCategoryWiseStats() {
		return expenseService.getCategoryWiseStats();
	}

}
