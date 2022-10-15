package com.dbms.insti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbms.insti.dao.UserDao;
import com.dbms.insti.models.Users;

@Service
public class UserServiceUtil implements UserService{
    @Autowired
    private UserDao userdao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public void save(Users user) {
		user.setPsw(bCryptPasswordEncoder.encode(user.getPsw()));
		if(!userdao.userExists(user.getEmail_id()))
			userdao.save(user);
	}

	@Override
	public Users findByEmail(String email_id) {
		return userdao.findByEmail(email_id);
	}

	@Override
	public boolean userExists(String email_id) {
		return userdao.userExists(email_id);
	}

	@Override
	public Users findByUserId(int user_id) {
		return userdao.findByUserId(user_id);
	}
	
}
