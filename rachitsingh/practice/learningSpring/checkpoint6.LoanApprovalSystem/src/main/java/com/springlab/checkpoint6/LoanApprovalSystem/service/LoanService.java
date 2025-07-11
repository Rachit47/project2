package com.springlab.checkpoint6.LoanApprovalSystem.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.springlab.checkpoint6.LoanApprovalSystem.model.LoanApplication;
import com.springlab.checkpoint6.LoanApprovalSystem.model.LoanStatus;

@Service
public class LoanService implements LoanServiceInterface {
	private Map<String, LoanApplication> applicationData = new HashMap<>();

	@Override
	
	public void applyLoan(LoanApplication application) {
		applicationData.put(application.getID(), application);
		System.out.println("Loan applied: " + applicationData);
	}

	@Override
	public void approveLoan(String ID) {
		LoanApplication application = applicationData.get(ID);
		if (application != null) {
			application.setStatus(LoanStatus.APPROVED);
			System.out.println("Loan approved for: " + application);
		} else {
			System.out.println("Loan Application not found for ID: " + ID);
		}
	}

	@Override
	public void rejectLoan(String ID) {
		LoanApplication application = applicationData.get(ID);
		if (application != null) {
			application.setStatus(LoanStatus.REJECTED);
			System.out.println("Loan Application rejected for: " + application);
		} else {
			System.out.println("Loan Application not found for ID: " + ID);
		}
	}

	@Override
	public LoanApplication getApplication(String ID) {
		return applicationData.get(ID);
	}
}
