package com.islproject.islproject.config;

import com.islproject.islproject.services.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    SecurityUserDetails userDetails;

    @Autowired
    FormSuccessHandler formSuccessHandler;

    @Autowired
    OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetails);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/user/**").authenticated();
            authorize.requestMatchers(("/checkout")).permitAll();
            authorize.anyRequest().permitAll();
        })
        .csrf(csrf->csrf
                .ignoringRequestMatchers("/checkout")
        )
        .formLogin(form -> form
                .loginPage("/login") // URL for login page
                .successHandler(formSuccessHandler) // Redirect after successful login
                .failureHandler((request, response, exception) -> {
                    response.sendRedirect("/login?error=true");
                })
                .permitAll()
        )
        .logout(logout -> logout
                .logoutUrl("/logout") // Endpoint for logout
                .logoutSuccessUrl("/login?logout=true") // Redirect after logout
                .invalidateHttpSession(true) // Invalidate session
                .deleteCookies("JSESSIONID") // Delete session cookies
                .permitAll()
        );

        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(oAuthSuccessHandler);
        });


        return httpSecurity.build();
    }

}
