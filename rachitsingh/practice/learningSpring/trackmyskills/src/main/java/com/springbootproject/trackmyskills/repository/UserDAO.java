package com.springbootproject.trackmyskills.repository;

import com.springbootproject.trackmyskills.model.User;

public interface UserDAO {
	User findByUsername(String username);

	User saveUserToDB(String username);
}
