package com.islproject.islproject.controllers;

import com.islproject.islproject.Helper.CookieJwtGenerator;
import com.islproject.islproject.Helper.GetUserName;
import com.islproject.islproject.entities.Payment;
import com.islproject.islproject.entities.User;
//import com.islproject.islproject.services.PaymentClient;
import com.islproject.islproject.services.UserServicesImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
//
//    @Autowired
//    PaymentClient paymentClient;

    @Autowired
    UserServicesImpl userServices;

    @Autowired
    CookieJwtGenerator cookieJwtGenerator;

    @Autowired
    GetUserName userName;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/dashboard")
    private String getDashBoard(){
        return "/user/dashboard";
    }

    @GetMapping("/logout")
    private String logout(){
        return "login";
    }

    @GetMapping("/myapi")
    private String apikeys(){
        return "/user/apikey";
    }

    @GetMapping("/payment")
    private String checkPayment(){
        if(userServices.checkPurchasedKey())
            return "redirect:/user/myapi";
        else
            return "redirect:/checkout";
    }

//    @GetMapping("/fetchorders")
//    private String getOrders(HttpServletResponse response,Model model   ){
//        response.addCookie(cookieJwtGenerator.generateCookie());
//        List<Payment> allPayments=paymentClient.getPayments();
//        if (allPayments != null) {
//            allPayments.forEach(payment -> logger.info("Payment ID: {}, Amount: {}, Status: {}",
//                    payment.getId(), payment.getAmount(), payment.getPaymentStatus()));
//        } else {
//            logger.warn("No payments received from Payment Microservice");
//        }
//
//        allPayments = allPayments.stream().map(payment -> {
//            //payment.setUsername(payment.getUsername() != null ? payment.getUsername() : "N/A");
//            payment.setOrderId(payment.getOrderId() != null ? payment.getOrderId() : "N/A");
//            payment.setCurrency(payment.getCurrency() != null ? payment.getCurrency() : "N/A");
//            payment.setPaymentStatus(payment.getPaymentStatus() != null ? payment.getPaymentStatus() : "PENDING");
//            payment.setTransactionId(payment.getTransactionId() != null ? payment.getTransactionId() : "N/A");
//            return payment;
//        }).toList();
//
//        model.addAttribute("payments", allPayments);
//        return "user/orders";
//    }

}
