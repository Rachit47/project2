package com.user.app.dao;

import java.util.List;
import java.util.Optional;

import com.user.app.model.User;

public interface UserDao {
    User CreatesUser(User user);
    Optional<User> findById(Long userId);
    Optional<User> findByEmail(String emailId);
    List<User> findAll();
} 