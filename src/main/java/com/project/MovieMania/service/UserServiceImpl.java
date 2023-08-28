package com.project.MovieMania.service;

import com.project.MovieMania.domain.Authority;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.UserStatus;
import com.project.MovieMania.repository.AuthorityRepository;
import com.project.MovieMania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    private AuthorityRepository authorityRepository;


    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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

        user.setPassword(passwordEncoder.encode(user.getPassword()));   // 비밀번호를 가져와서 암호화

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
    public User findUsernameId(String name, String email, LocalDate birthday){
        User user =  userRepository.findByNameAndEmailAndBirthday(name, email, birthday);
        return user;
    }

    @Override
    public User findPw(String username, String name, String email) {

        User user = userRepository.findByNameAndUsernameAndEmail(name,username,email);
        return user;
    }

    @Override
    public List<Authority> findAuthorityById(Long id) {

        User user = userRepository.findById(id).orElseThrow();

//        List<Authority> authorityList = user.getAuthorities();

        return user.getAuthorities();
    }

    @Override
    public User changePw(Long userId, String password){

        User user = userRepository.findById(userId).orElseThrow();

        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);


    }

    @Override
    public User findByUserId(Long userId) {

        User user= userRepository.findById(userId).orElseThrow();

        return user;
    }

    @Override
    public boolean pwCheck(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow();

        if (passwordEncoder.matches(password, user.getPassword())) {
            userRepository.delete(user);
            return true;
        } else {
            return false;
        }

    }


}
