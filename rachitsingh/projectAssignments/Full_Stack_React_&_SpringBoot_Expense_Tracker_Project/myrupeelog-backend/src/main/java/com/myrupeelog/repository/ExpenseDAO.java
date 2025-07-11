package com.myrupeelog.repository;

import java.util.List;

import com.myrupeelog.model.Expense;

public interface ExpenseDAO {
	void insertRecord(Expense expense);

	List<Expense> fetchAllExpenses();

	Expense findById(Integer id);

	void deleteRecord(Integer id);
}
