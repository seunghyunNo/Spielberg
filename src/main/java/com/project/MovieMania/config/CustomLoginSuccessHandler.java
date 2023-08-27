package com.project.MovieMania.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public CustomLoginSuccessHandler(String defaultTargetUrl){

        // 로그인 성공 시 기본 url 설정
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws ServletException, IOException{

        // 특정 페이지에서 로그인 성공 시 현재 페이지
        super.onAuthenticationSuccess(request,response,authentication);

    }

    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (ip== null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client_IP");
        }
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("WL-Proxy-Client-IP");
        }

        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getRemoteAddr();
        }
        return ip;
    }
}
