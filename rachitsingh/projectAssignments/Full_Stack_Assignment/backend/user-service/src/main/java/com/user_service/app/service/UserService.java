package com.user_service.app.service;

import com.user_service.app.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long userId);
    List<User> getAllUsers();
    User updateUser(Long userId, User user);
} 