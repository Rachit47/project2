package com.myrupeelog.model;

import java.time.LocalDate;

public class Expense {
	private Integer id;
	private String title;
	private Double amount;
	private String category;
	private LocalDate date;

	public Expense(Integer id, String title, Double amount, String category, LocalDate date) {
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.category = category;
		this.date = date;
	}

	public Expense() {
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
