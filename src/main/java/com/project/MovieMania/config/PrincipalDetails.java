package com.project.MovieMania.config;


import com.project.MovieMania.domain.Authority;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// 로그인 한 유저의 정보를 가져오는 것
public class PrincipalDetails implements UserDetails {

    UserService userService;

    public void setUserService(UserService userService){
        this.userService = userService;
    }
    private User user;

    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return user;
    }

    public PrincipalDetails(User user){
        this.user = user;
    }


    // 해당 User 권한들 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> collection = new ArrayList<>();

        List<Authority> list = userService.findAuthorityById(user.getId());

        for (Authority authority : list) {
            collection.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return authority.getName();
                }

                @Override
                public String toString() {
                    return authority.getName();
                }
            });

        }
        return collection;
    }


    public String getEmail(){
        return user.getEmail();
    }

    public long getId(){
        return user.getId();
    }

    // 확인 필요함

    public String getName(){return  user.getName();}

    public String getPhoneNum(){
        return user.getPhoneNum();
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public String getUsername(){
        return user.getUsername();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 credential 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

}