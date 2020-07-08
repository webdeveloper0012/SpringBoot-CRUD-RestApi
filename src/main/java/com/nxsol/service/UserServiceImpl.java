package com.nxsol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxsol.model.User;
import com.nxsol.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	public Iterable<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	public User findById(long id) {
		return userRepository.findOne(id);
	}
	
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
	
	public User saveUser(User user) {
		User db = userRepository.save(user);
		return db;
	}

	public User updateUser(User user) {
		User db = userRepository.save(user);
		return db;
	}

	public void deleteUserById(long id) {
		userRepository.delete(id);
	}

	public boolean isUserExist(User user) {
		User db = userRepository.findByName(user.getName());
		if(null != db){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public void deleteAllUsers() {
		userRepository.deleteAll();
	}

	@Override
	public void deleteByQuery(long id) {
		// TODO Auto-generated method stub
		userRepository.deleteUserByQuery(id);
		
	}
}
