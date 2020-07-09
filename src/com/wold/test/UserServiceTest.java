package com.wold.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wold.pojo.User;
import com.wold.service.UserService;

public class UserServiceTest {
	
	private ApplicationContext applicationContext;

	@Before
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}

	@Test
	public void testGetUserByNameAndPwd() {
		UserService userService=applicationContext.getBean(UserService.class);
		User user=new User();
		user.setName("wold");
		user.setPassword("123");
		userService.getUserByNameAndPwd(user);
		System.out.println(user);
	}

	@Test
	public void testSaveUser() {
		UserService userService=applicationContext.getBean(UserService.class);
		User user=new User();
		user.setName("wold01");
		user.setPassword("123");
		boolean b=userService.saveUser(user);
		System.out.println(b);
	}

}
