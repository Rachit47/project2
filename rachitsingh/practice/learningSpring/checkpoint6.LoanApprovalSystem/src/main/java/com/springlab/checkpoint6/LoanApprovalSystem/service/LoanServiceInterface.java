package com.springlab.checkpoint6.LoanApprovalSystem.service;

import com.springlab.checkpoint6.LoanApprovalSystem.model.LoanApplication;

public interface LoanServiceInterface {
	void applyLoan(LoanApplication app);
	void approveLoan(String ID);
	void rejectLoan(String ID);
	LoanApplication getApplication(String ID);
}
