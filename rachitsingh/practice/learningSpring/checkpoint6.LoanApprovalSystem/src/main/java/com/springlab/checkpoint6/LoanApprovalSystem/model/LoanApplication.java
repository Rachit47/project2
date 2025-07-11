package com.springlab.checkpoint6.LoanApprovalSystem.model;

public class LoanApplication {
	private String ID;
	private String applicantName;
	private Double amount;
	private LoanStatus status;

	public LoanApplication() {
	}
    public LoanApplication(String ID, String applicantName, Double amount, LoanStatus status) {
        this.ID = ID;
        this.applicantName = applicantName;
        this.amount = amount;
        this.status = status;
    }

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}
	 @Override
	    public String toString() {
	        return "LoanApplication{ID='" + ID + "', ApplicantName='" + applicantName + "', Amount=" + amount + ", LoanStatus='" + status + "'}";
	    }
}
