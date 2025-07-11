package com.springbootproject.librarium365.enums;

public enum AvailabilityStatus {
	AVAILABLE("A", "Available"), ISSUED("I", "Issued");

	private final String code;
	private final String label;

	AvailabilityStatus(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static AvailabilityStatus fromLabel(String label) {
		for (AvailabilityStatus status : AvailabilityStatus.values()) {
			if (status.getLabel().equalsIgnoreCase(label)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Unknown availability status label: " + label);
	}

	public static AvailabilityStatus fromCode(String code) {
		for (AvailabilityStatus status : AvailabilityStatus.values()) {
			if (status.getCode().equalsIgnoreCase(code)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Unknown availability status code: " + code);
	}
}
