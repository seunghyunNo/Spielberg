package com.project.MovieMania.config;

import com.project.MovieMania.domain.User;
import com.project.MovieMania.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        // db 조회
        User user = userService.findByUsername(username);

        if(user != null){
            PrincipalDetails userDetails = new PrincipalDetails(user);
            userDetails.setUserService(userService);
            return userDetails;
        }

        throw new UsernameNotFoundException(username);
    }


}
