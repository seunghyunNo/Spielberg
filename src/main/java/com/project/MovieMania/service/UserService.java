package com.project.MovieMania.service;

import com.project.MovieMania.domain.Authority;
import com.project.MovieMania.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhoneNum(String phoneNum);

    // 회원가입 시 존재 하는지 여부 확인
    boolean isExistUsername(String username);

    // 회원가입 시 존재 하는지 여부 확인
    boolean isExistEmail(String email);

    // 회원가입 시 존재 하는지 여부 확인
    boolean isExistPhoneNum(String phoneNum);

    // 회원가입
    int register(User user);

    // 회원가입
    int usernameCheck(String username);

    // 회원가입
    int mailCheck(String email);

    // 아이디 찾기
    String findUsername(String name, String email, LocalDate birthday);


    // 비밀번호 찾기
    String findPw(String name,String email,String username);


    // 비밀번호 변경
    String updatePw(User user);

    boolean rePw(User user);

    int delete(Long id);

    List<Authority> findAuthorityById(Long id);
}
