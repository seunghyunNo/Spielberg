package com.project.MovieMania.service;

import com.project.MovieMania.config.PrincipalDetails;
import com.project.MovieMania.domain.Authority;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.UserStatus;
import com.project.MovieMania.repository.AuthorityRepository;
import com.project.MovieMania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
	
	
	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository){ this.userRepository = userRepository; }
	@Autowired
	public void setAuthorityRepository(AuthorityRepository authorityRepository){ this.authorityRepository = authorityRepository; }
	
	
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
	public boolean checkAuthority(){
		try{
			PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			List<Authority> authorities = user.getAuthorities();
			Authority admin = authorityRepository.findByName("ROLE_ADMIN");
			System.out.println(authorities);
			System.out.println(admin);
			if(! authorities.contains(admin)){
				throw new Exception();
			}
		} catch (Exception e){
			return false;
		}
		return true;
	}
	
	
}
