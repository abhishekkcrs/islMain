package com.islproject.islproject.services;

import com.islproject.islproject.config.FeignClientConfig;
import com.islproject.islproject.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ISL-PAYMENTS", configuration = FeignClientConfig.class)
public interface PaymentClient {

    @GetMapping("/payment/fetchorders")
    public List<Payment> getPayments();

}