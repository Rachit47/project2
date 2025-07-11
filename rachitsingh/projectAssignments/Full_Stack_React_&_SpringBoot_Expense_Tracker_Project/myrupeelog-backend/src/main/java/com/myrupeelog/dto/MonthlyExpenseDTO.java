package com.myrupeelog.dto;

public class MonthlyExpenseDTO {
	private String month;
	private Double amount;

	public MonthlyExpenseDTO(String month, Double amount) {
		this.month = month;
		this.amount = amount;
	}

	public MonthlyExpenseDTO() {
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
