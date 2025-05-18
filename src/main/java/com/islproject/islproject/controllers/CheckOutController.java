package com.islproject.islproject.controllers;

import com.islproject.islproject.Helper.GetUserName;
import com.islproject.islproject.config.JwtAuthentication;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class CheckOutController {

    @Autowired
    GetUserName getUserName;

    @Autowired
    JwtAuthentication jwtAuthentication;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(CheckOutController.class);


    @GetMapping("/checkout")
    public String checkout(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {

            String username = getUserName.findUsername();
            String orderId = UUID.randomUUID().toString(); // Generate random order ID
            long amount = 50; // Fixed amount
            String currency = "USD";

            // ✅ Add to the model to pass to the frontend
            model.addAttribute("username", username);
            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", amount);
            model.addAttribute("currency", currency);

            return "checkout";
        }
        else {
            return "login";
        }
    }


    @PostMapping("/checkout")
    public String doCheckout(@RequestParam("username") String username,
                             @RequestParam("orderId") String orderId,
                             @RequestParam("amount") Long amount,
                             @RequestParam("currency") String currency,
                             Model model,
                             HttpServletResponse response) {

        // ✅ Generate JWT token
        String token = jwtAuthentication.generateToken(getUserName.findUsername());
        logger.info("Generated Token: {} ", token);
        logger.info("Current Username: {}",getUserName.findUsername());

        // ✅ Create Secure Cookie
        Cookie jwtCookie = new Cookie("Authorization", token);
        jwtCookie.setHttpOnly(true); // Prevent JavaScript access
        jwtCookie.setSecure(false); // Keep it false for HTTP (true for HTTPS)
        jwtCookie.setPath("/"); // Cookie available across the service
        jwtCookie.setMaxAge(3600); // Expiry time in seconds (1 hour)

        // ✅ Add Cookie to Response
        response.addCookie(jwtCookie);

        // ✅ Redirect using GET request (because browser follows 302 with GET)
        return "redirect:http://localhost:8082/payment?username=" + username +
                "&orderId=" + orderId +
                "&amount=" + amount +
                "&currency=" + currency;
    }
}
