package com.project.MovieMania.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private final String DEFAULT_FAILURE_FORWARD_URL = "/user/loginError";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException{

        String errMessage = null;

        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errMessage = "아이디 또는 비밀번호가 맞기 않습니다";
        }
        // disable
        else if(exception instanceof DisabledException){
            errMessage = "계정이 신고로 인해 비활성화 됐습니다.";
        }

        else if(exception instanceof CredentialsExpiredException){
            errMessage = "비밀번호 유효기간이 만료 되었습니다";
        }
        else{
            errMessage = "로그인에 실패하였습니다";
        }

        // 에러 메세지와 username 을 담아 redirect
        request.setAttribute("errMessage",errMessage);
        request.setAttribute("username",request.getParameter("username"));

        // forward url 로 redirect
        request.getRequestDispatcher(DEFAULT_FAILURE_FORWARD_URL).forward(request,response);
    }
}
