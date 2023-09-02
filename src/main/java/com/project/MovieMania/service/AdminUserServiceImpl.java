package com.project.MovieMania.service;

import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.UserStatus;
import com.project.MovieMania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
	
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository){ this.userRepository = userRepository; }
	
	
	@Override
	public List<User> list() {
		return userRepository.findAll();
	}
	
	@Override
	public List<User> list(UserStatus userStatus) {
		return userRepository.findByStatus(userStatus);
	}
	
	@Override
	public int statusUpdate(Long id, UserStatus userStatus){
		try{
			User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
			user.setStatus(userStatus);
			userRepository.save(user);
		}catch (RuntimeException e){
			return 0;
		}
		return 1;
	}
}
