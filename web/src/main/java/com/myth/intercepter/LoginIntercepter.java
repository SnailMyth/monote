package com.myth.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginIntercepter implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().equals("/login")||request.getRequestURI().equals("/account/check") )
        {
            return true;
        }

        Object session_account = request.getSession().getAttribute("session_account");
        if (session_account == null) {
            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
