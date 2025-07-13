package com.user_service.app.dao.impl;

import com.user_service.app.dao.UserDao;
import com.user_service.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                rs.getLong("UserId"),
                rs.getString("Username"),
                rs.getString("Email"),
                rs.getString("FullName"),
                rs.getString("Phone"),
                rs.getString("Gender"),
                rs.getInt("Age"),
                rs.getString("Password"),
                rs.getString("Role"),
                rs.getTimestamp("CreatedAt").toLocalDateTime()
            );
        }
    }

    @Override
    public User CreatesUser(User user) {
        String sql = "INSERT INTO users (Username, Email, FullName, Phone, Gender, Age, Password, Role, CreatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getGender(),
                user.getAge(),
                user.getPassword(),
                user.getRole(),
                user.getCreatedAt() != null ? user.getCreatedAt() : LocalDateTime.now()
        );
        return findById(user.getUserId()).orElse(null);
    }

    @Override
    public Optional<User> findById(Long userId) {
        String sql = "SELECT * FROM users WHERE UserId = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), userId);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }


    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }
} 