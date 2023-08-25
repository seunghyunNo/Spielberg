package com.project.MovieMania.service;

import com.project.MovieMania.domain.Authority;
import com.project.MovieMania.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface UserService {

    // 회원 아이디 정보 불러오기
    User findByUsername(String username);



    // 회원가입
    int register(User user);


    // 아이디 찾기
    String findUsername(String name, String email, LocalDate birthday);


    // 비밀번호 찾기
    String findPw(String name,String email,String username);


    // 비밀번호 변경
    String updatePw(User user);


//    int delete(Long id);

    // 로그인 유저의 id로 해당 유저의 authority 가져오기
    List<Authority> findAuthorityById(Long id);
}
