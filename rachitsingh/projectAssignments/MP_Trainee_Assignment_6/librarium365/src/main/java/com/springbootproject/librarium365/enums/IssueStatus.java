package com.springbootproject.librarium365.enums;

public enum IssueStatus {
	ISSUED("I", "Issued"), RETURNED("R", "Returned");

	private final String code;
	private final String label;

	IssueStatus(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static IssueStatus fromCode(String code) {
	    for (IssueStatus status : IssueStatus.values()) {
	        if (status.getCode().equalsIgnoreCase(code)) {
	            return status;
	        }
	    }
	    throw new IllegalArgumentException("Unknown status code: " + code);
	}


	public static IssueStatus fromLabel(String label) {
		for (IssueStatus status : IssueStatus.values()) {
			if (status.getLabel().equalsIgnoreCase(label)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Unknown condition label: " + label);
	}
}