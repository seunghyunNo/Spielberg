package com.project.MovieMania.repository;

import com.project.MovieMania.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhoneNum(String phoneNum);
    int register(User user);

    int usernameCheck(String username);

    int mailCheck(String email);

    String findUsername(String name, String email, LocalDate birthday);

    String findPw(String name,String username, String email);

    String updatePw(User user);

    String getEncodePassword(User user);

    int deleteUser(Long id);

}
