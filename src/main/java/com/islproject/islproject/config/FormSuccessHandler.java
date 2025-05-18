package com.islproject.islproject.config;

import com.islproject.islproject.services.UserServicesImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FormSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserServicesImpl userServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        HttpSession session = request.getSession();
        session.setAttribute("username",username);
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }
}
