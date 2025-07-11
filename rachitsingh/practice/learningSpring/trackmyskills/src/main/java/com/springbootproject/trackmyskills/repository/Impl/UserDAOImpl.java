package com.springbootproject.trackmyskills.repository.Impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.springbootproject.trackmyskills.model.User;
import com.springbootproject.trackmyskills.repository.UserDAO;

@Repository
public class UserDAOImpl implements UserDAO {
	private final JdbcTemplate jdbcTemplate;

	public UserDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User(resultSet.getInt("user_id"),
			resultSet.getString("username"));

	@Override
	public User findByUsername(String username) {
		String sqlQuery = "SELECT user_id, username FROM users WHERE username = ?";
		return jdbcTemplate.queryForObject(sqlQuery, userRowMapper, username);
	}

	@Override
	public User saveUserToDB(String username) {
		String sqlQuery = "INSERT INTO users (username) VALUES (?)";
		jdbcTemplate.update(sqlQuery, username);

		return findByUsername(username);
	}
}
