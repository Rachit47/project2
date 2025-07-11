package com.springbootproject.librarium365.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Member {
	private Integer memberId;

	@NotBlank(message = "Name is required")
	private String name;

	@Email(message = "Invalid email format")
	private String email;

	@Min(value = 1000000000L, message = "Mobile must be valid")
	private long mobile;

	@Pattern(regexp = "[MmFfOo]", message = "Gender must be M, F or O")
	private char gender;

	@NotBlank(message = "Address is required")
	private String address;

	public Member() {
	}

	public Member(int memberId, String name, String email, long mobile, char gender, String address) {
		this.memberId = memberId;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
		this.address = address;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", name=" + name + ", email=" + email + ", mobile=" + mobile
				+ ", gender=" + gender + ", address=" + address + "]";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
