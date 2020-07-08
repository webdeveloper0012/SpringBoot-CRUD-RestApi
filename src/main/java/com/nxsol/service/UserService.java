package com.nxsol.service;


import java.util.List;

import com.nxsol.model.User;


public interface UserService {
	
	User findById(long id);
	
	User findByName(String name);
	
	User saveUser(User user);
	
	User updateUser(User user);
	
	void deleteUserById(long id);

	Iterable<User> findAllUsers();
	
	void deleteAllUsers();
	
	boolean isUserExist(User user);

	void deleteByQuery(long id);
	
}
