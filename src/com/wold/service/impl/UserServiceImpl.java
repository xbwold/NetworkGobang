package com.wold.service.impl;

import com.wold.dao.UserDao;
import com.wold.pojo.User;
import com.wold.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getUserByNameAndPwd(User user) {
		return userDao.getUserByNameAndPwd(user);
	}

	@Override
	public boolean saveUser(User user) {
		User u = userDao.getUserByNameAndPwd(user);
		if (u != null) {
			return false;
		} else {
			try {
				userDao.setUser(user);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}
}
