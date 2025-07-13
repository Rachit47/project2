package com.user_service.app.service.impl;

import com.user_service.app.dao.UserDao;
import com.user_service.app.model.User;
import com.user_service.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) {
        return userDao.CreatesUser(user);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

	@Override
	public User updateUser(Long userId, User user) {
		// TODO Auto-generated method stub
		return null;
	}
} 