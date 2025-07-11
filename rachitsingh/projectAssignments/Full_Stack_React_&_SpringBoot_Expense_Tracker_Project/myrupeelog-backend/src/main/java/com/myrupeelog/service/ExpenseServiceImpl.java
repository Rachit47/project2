package com.myrupeelog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myrupeelog.dto.CategoryStatsDTO;
import com.myrupeelog.dto.MonthlyExpenseDTO;
import com.myrupeelog.model.Expense;
import com.myrupeelog.repository.ExpenseDAO;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	@Autowired
	private ExpenseDAO expenseDao;

	@Override
	public void addExpense(Expense expense) {
		expenseDao.insertRecord(expense);
	}

	@Override
	public List<Expense> getAllExpenses() {
		return expenseDao.fetchAllExpenses();
	}

	@Override
	public Double getBiggestExpense() {
		return getAllExpenses().stream().mapToDouble(Expense::getAmount).max().orElse(0.0);
	}

	@Override
	public Integer getTotalEntries() {
		return getAllExpenses().size();
	}

	@Override
	public Double getTotalSpent() {
		return getAllExpenses().stream().mapToDouble(Expense::getAmount).sum();
	}

	@Override
	public String getLastAddedExpenseTitle() {
		List<Expense> allExpenses = getAllExpenses();
		Integer size = allExpenses.size();
		if(size == 0) {
			return "No expenses added yet";
		}
		return allExpenses.get(size - 1).getTitle();
	}

	@Override
	public List<MonthlyExpenseDTO> getMonthlyExpenseStats() {
		List<Expense> allExpenses = getAllExpenses();
		Map<String, Double> monthlyTotals = new HashMap<>();

		for (Expense expense : allExpenses) {
			String month = expense.getDate().getMonth().name().substring(0, 3);

			Double amount = expense.getAmount();
			if (monthlyTotals.containsKey(month)) {
				monthlyTotals.put(month, monthlyTotals.get(month) + amount);
			} else {
				monthlyTotals.put(month, amount);
			}
		}
		List<MonthlyExpenseDTO> monthlyExpenseStats = new ArrayList<>();

		for (Map.Entry<String, Double> entry : monthlyTotals.entrySet()) {
			monthlyExpenseStats.add(new MonthlyExpenseDTO(entry.getKey(), entry.getValue()));
		}
		return monthlyExpenseStats;
	}

	@Override
	public List<CategoryStatsDTO> getCategoryWiseStats() {
		List<Expense> allExpenses = getAllExpenses();
		Map<String, Double> categoryWiseTotals = new HashMap<>();
		Double totalSpent = getTotalSpent();
		for (Expense expense : allExpenses) {
			String category = expense.getCategory();
			Double amount = expense.getAmount();

			if (categoryWiseTotals.containsKey(category)) {
				Double updatedAmount = categoryWiseTotals.get(category) + amount;
				categoryWiseTotals.put(category, updatedAmount);
			} else {
				categoryWiseTotals.put(category, amount);
			}
		}

		List<CategoryStatsDTO> categoryStatsList = new ArrayList<>();
		for (Map.Entry<String, Double> entry : categoryWiseTotals.entrySet()) {
			Double percentage = 0.0;
			if (totalSpent > 0) {
				percentage = (entry.getValue() / totalSpent) * 100;
			}

			categoryStatsList.add(new CategoryStatsDTO(entry.getKey(), percentage));
		}
		return categoryStatsList;
	}
}
