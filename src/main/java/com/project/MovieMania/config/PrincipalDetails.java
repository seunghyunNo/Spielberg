package com.project.MovieMania.config;

import com.project.MovieMania.domain.Authority;
import com.project.MovieMania.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//시큐리티가 /user/login (POST) 주소요청이 오면 낚아채서 로그인을 진행시킨다.

// User 정보를 꺼내려면 Security Session 에서 Authentication 객체를 꺼내고, 그 안에서 UserDetails 정보를 꺼낸다
public class PrincipalDetails implements UserDetails {

    private UserService userService;

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    private User user;

    public void setUser(User user){
        this.user=user;
    }

    public User getUser(){
        return user;
    }

    public PrincipalDetails(User user){
        this.user = user;
    }

    // 현재 로그인한 사용자의 정보가 필요할 때마다 호출됨

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> collect = new ArrayList<>();

        List<Authority> list = userService.findAuthorityById(user.get);


    }

}
