package com.project.MovieMania.service;

import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.UserStatus;

import java.util.List;

public interface AdminUserService {
	List<User> list();
	List<User> list(UserStatus userStatus);
	
	int statusUpdate(Long id, UserStatus userStatus);
}
