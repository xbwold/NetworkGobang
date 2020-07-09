package com.wold.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.wold.dao.UserDao;
import com.wold.pojo.User;


public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	@Override
	public User getUserByNameAndPwd(User user) {
		SqlSession sqlSessoin=super.getSqlSession(); 
		User u=sqlSessoin.selectOne("user.getUserByNameAndPwd",user);
		return u;
	}

	@Override
	public boolean setUser(User user) {
		try {
			SqlSession sqlSessoin=super.getSqlSession(); 
			sqlSessoin.insert("user.setUser", user);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
