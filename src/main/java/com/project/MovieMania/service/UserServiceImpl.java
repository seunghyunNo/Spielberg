package com.project.MovieMania.service;

import com.project.MovieMania.domain.Authority;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.UserStatus;
import com.project.MovieMania.repository.AuthorityRepository;
import com.project.MovieMania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    private AuthorityRepository authorityRepository;


    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }


    @Autowired
    public  void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isExistUsername(String username) {
       User user =userRepository.findByUsername(username);
       if(user != null){
           return true;
       }
       return false;
    }

    @Override
    public boolean isExistEmail(String email) {
        User user= userRepository.findByEmail(email);
        if(user !=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistPhoneNum(String phoneNum) {
        User user = userRepository.findByPhoneNum(phoneNum);
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public int register(User user) {

        user.setStatus(UserStatus.ACTIVE);

        User user1 = userRepository.save(user);


        User user2 = userRepository.findById(user1.getId()).orElseThrow();

        if(user1 != null){
            return 1;
        }
        return 0;
    }

    // 유저 entity 넘겨주는
    @Override
//    @Transactional
    public String findUsernameId(String name, String email, LocalDate birthday) {
        return userRepository.findByNameAndEmailAndBirthday(name, email, birthday);
    }

    @Override
    public String findPw(String username, String name, String email) {
        return userRepository.findByNameAndUsernameAndEmail(name,username,email);
    }

    @Override
    public List<Authority> findAuthorityById(Long id) {

        User user = userRepository.findById(id).orElseThrow();

//        List<Authority> authorityList = user.getAuthorities();

        return user.getAuthorities();
    }



}
