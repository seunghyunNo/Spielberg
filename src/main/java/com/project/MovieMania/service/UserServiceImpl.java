package com.project.MovieMania.service;

import com.project.MovieMania.domain.Authority;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.repository.AuthorityRepository;
import com.project.MovieMania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    //AuthorityRepository 작성
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public  User findByPhoneNum(String phoneNum){
        return userRepository.findByPhoneNum(phoneNum);
    }
    @Override
    public boolean isExistUsername(String username) {

        User user = findByUsername(username);

        return (user!=null)? true: false;
    }

    @Override
    public boolean isExistEmail(String email) {

        User user = findByEmail(email);

        return (user!=null)?true:false;
    }

    @Override
    public boolean isExistPhoneNum(String phoneNum) {

        User user = findByPhoneNum(phoneNum);

        return (user!=null)? true:false;
    }

    @Override
    public int register(User user) {

        // 암호화해서 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Authority authority = authorityRepository.findByName("ROLE_MEMBER");
        user.addAuthorities(authority);
        userRepository.save(user);
        return 1;
    }


    @Override
    public int usernameCheck(String username) {
        return userRepository.usernameCheck(username);
    }

    @Override
    public int mailCheck(String email) {
        return userRepository.mailCheck(email);
    }

    @Override
    public String findUsername(String name, String email, LocalDate birthday) {
        return userRepository.findUsername(name,email,birthday);
    }

    @Override
    public String findPw(String name, String email, String username) {
        return userRepository.findPw(name,email,username);
    }

    @Override
    public String updatePw(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.updatePw(user);
    }

    @Override
    public boolean rePw(User user) {
        String encodePassword = userRepository.getEncodePassword(user);
        return passwordEncoder.matches(user.getPassword(),encodePassword);
    }

    @Override
    public int delete(Long id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public List<Authority> findAuthorityById(Long id) {


        // 해당 유저의 Authority 를 담아옴
        return authorityRepository.findAuthorityById(id);
    }
}
