package com.islproject.islproject.config;

import com.islproject.islproject.entities.Providers;
import com.islproject.islproject.entities.User;
import com.islproject.islproject.services.UserServicesImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthSuccessHandler.class);

    @Autowired
    UserServicesImpl userServices;

    @Value("${gateway.url}")
    private String gatewayURL;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthSuccessHandler");

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String platform = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(platform);

        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

//        logger.info(user.getName());
//        user.getAttributes().forEach((key,value)->{
//            logger.info("{} =>{}",key,value);
//        });
//        logger.info(user.getAuthorities().toString());
        User newUser = new User();
        if(platform.equalsIgnoreCase("google")) {

            newUser.setEmail(user.getAttribute("email").toString());
            newUser.setPassword("password");
            newUser.setUsername(user.getAttribute("email").toString());
            newUser.setEnabled(true);
            newUser.setEmailVerified(true);
            newUser.setProviders(Providers.GOOGLE);
            newUser.setProviderUserId(user.getName());
        }
        else if(platform.equalsIgnoreCase("github")){
            if(user.getAttribute("email") != null) {
                newUser.setEmail(user.getAttribute("email").toString());
            }
            newUser.setUsername(user.getAttribute("login").toString());
            newUser.setPassword(UUID.randomUUID().toString());
            newUser.setEnabled(true);
            newUser.setEmailVerified(true);
            newUser.setProviders(Providers.GITHUB);
            newUser.setProviderUserId(user.getName());
        }

        logger.info("{}{}", platform, userServices.saveUser(newUser));

        HttpSession session = request.getSession();
        session.setAttribute("username",newUser.getUsername());
        String contextPath = request.getContextPath();
        new DefaultRedirectStrategy().sendRedirect(request,response, contextPath + "/user/dashboard");
    }
}
