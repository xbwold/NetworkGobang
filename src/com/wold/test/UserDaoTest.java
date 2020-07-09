package com.wold.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wold.dao.UserDao;
import com.wold.pojo.User;

public class UserDaoTest {
	private ApplicationContext applicationContext;

	@Before
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}

	@Test
	public void test() {
		try {
			UserDao userDao=applicationContext.getBean(UserDao.class);
			User user=new User();
			user.setName("wold");
			user.setPassword("1234");
			User u=userDao.getUserByNameAndPwd(user);
			if(u==null) {
				System.out.println("===============");
				System.out.println("Пе");
			}else {
				System.out.println(u);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
