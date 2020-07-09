package com.wold.service;

import com.wold.pojo.User;

public interface UserService {
	User getUserByNameAndPwd(User user); 
	
	boolean saveUser(User user);
}
