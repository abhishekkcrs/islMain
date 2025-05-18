package com.islproject.islproject.Helper;

import com.islproject.islproject.config.JwtAuthentication;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CookieJwtGenerator {

    @Autowired
    JwtAuthentication jwtAuthentication;

    @Autowired
    GetUserName getUserName;

    Logger logger = LoggerFactory.getLogger(CookieJwtGenerator.class);

    public Cookie generateCookie(){
        String token = jwtAuthentication.generateToken(getUserName.findUsername());
        logger.info("Generated Token: {} ", token);
        logger.info("Current Username: {}",getUserName.findUsername());

        // ✅ Create Secure Cookie
        Cookie jwtCookie = new Cookie("Authorization", token);
        jwtCookie.setHttpOnly(true); // Prevent JavaScript access
        jwtCookie.setSecure(false); // Keep it false for HTTP (true for HTTPS)
        jwtCookie.setPath("/"); // Cookie available across the service
        jwtCookie.setMaxAge(3600);
        return jwtCookie;
    }
}
