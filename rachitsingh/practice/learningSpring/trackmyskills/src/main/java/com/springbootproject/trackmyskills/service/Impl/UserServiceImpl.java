package com.springbootproject.trackmyskills.service.Impl;

import org.springframework.stereotype.Service;

import com.springbootproject.trackmyskills.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public Integer getCurrentUserId() {
		return 1;
	}
}