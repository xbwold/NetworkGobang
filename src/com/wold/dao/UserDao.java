package com.wold.dao;

import com.wold.pojo.User;

public interface UserDao {
	User getUserByNameAndPwd(User user);
	
	boolean setUser(User user);
}
