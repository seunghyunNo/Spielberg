package com.project.MovieMania.util;

import com.project.MovieMania.config.PrincipalDetails;
import com.project.MovieMania.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class U {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return  attributes.getRequest();
    }


    public static HttpSession getSession() {
        return getRequest().getSession();

    }


}