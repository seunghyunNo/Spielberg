package com.project.MovieMania.repository;

import com.project.MovieMania.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhoneNum(String phoneNum);

    // 아이디(유저) 찾기
    User findByNameAndEmailAndBirthday(String name, String email, LocalDate birthday);

    // 비밀번호 찾기
    User findByNameAndUsernameAndEmail(String name,String username, String email);



}
