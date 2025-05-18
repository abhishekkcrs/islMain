package com.islproject.islproject.Helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component("GetUserName")
public class GetUserName {

    Logger logger= LoggerFactory.getLogger(GetUserName.class);

    public String findUsername(){
        String username=null;
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            String platform = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

            if(platform.equalsIgnoreCase("google")) {
                username = user.getAttribute("email").toString();
                logger.info("It is a google account");
            }
            else if (platform.equalsIgnoreCase("github")) {
                username = user.getAttribute("login");
                logger.info("It is a github account");
            }
        }
        else{
            username = authentication.getName();
            logger.info("It is an Email account");
        }
        return username;
    }
}
