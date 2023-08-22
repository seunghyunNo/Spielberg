package com.project.MovieMania.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws IOException, ServletException{

        String redirectUrl = "/user/login?logoutHandler";

        if(request.getParameter("ret_url")!=null){
            redirectUrl = request.getParameter("ret_url");
        }

        response.sendRedirect(redirectUrl);
    }
}
