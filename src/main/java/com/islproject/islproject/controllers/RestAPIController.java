package com.islproject.islproject.controllers;

import com.islproject.islproject.Helper.GetUserName;
import com.islproject.islproject.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RestAPIController {

    @Autowired
    UserServicesImpl userServices;

    @Autowired
    GetUserName getUserName;

    // Get API Key
    @GetMapping("/user/api-key")
    public Map<String, String> getApiKey() {
        String apiKey = userServices.getAPIkey(getUserName.findUsername());
        return Map.of("apiKey", apiKey);
    }

    // Generate New API Key
    @GetMapping("/user/newapi-key")
    public Map<String, String> generateNewApiKey() {
        String newApiKey = userServices.newAPIkey(getUserName.findUsername());
        return Map.of("apiKey", newApiKey);
    }
}
