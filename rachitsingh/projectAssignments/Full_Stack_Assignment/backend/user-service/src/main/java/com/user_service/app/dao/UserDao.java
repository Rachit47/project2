package com.user_service.app.dao;

import com.user_service.app.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    User CreatesUser(User user);
    Optional<User> findById(Long userId);
    List<User> findAll();
} 