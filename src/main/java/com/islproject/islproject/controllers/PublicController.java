package com.islproject.islproject.controllers;

import com.islproject.islproject.entities.User;
import com.islproject.islproject.forms.UserForm;
import com.islproject.islproject.services.UserServices;
import com.islproject.islproject.services.UserServicesImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class PublicController {

    @Value("${gateway.url}  ")
    private String gatewayURL;

    @Autowired
    UserServicesImpl userServices;

    @GetMapping("/")
    public String redirectToHome(HttpServletRequest request) {
        String currentURL = request.getRequestURL().toString();      // Full URL without query string
        String queryString = request.getQueryString();               // e.g., ?key=value

        if (queryString != null) {
            currentURL += "?" + queryString;
        }

        System.out.println("Current URL: " + currentURL);

        return "redirect:" + request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + "/home";
    }

    @GetMapping("/predictor")
    public String home(){
        return "predictor";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/learn")
    public String getLearnPage(){ return "startlearn";}

    @GetMapping("/android")
    public String getAndroidPage(){ return "android";}

    @GetMapping("/learner")
    public String getLearner(@RequestParam String lesson, Model model) {
        model.addAttribute("lessonName", lesson);
        return "learner";
    }

    @GetMapping("/playground")
    public String getPlayground(){
        return "playground";
    }

    @GetMapping("/smartmedia")
    public String getMediaPlayer(){
        return "smartmedia";
    }

    @GetMapping("/contact")
    public String getContact() {
        return "contact";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
    @PostMapping("/login")
    public String processLogin() {
        return "home";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute UserForm userForm, Model model) {
        User newUser= new User();
        newUser.setUsername(userForm.getUsername());
        newUser.setPassword(userForm.getPassword());
        newUser.setEmail(userForm.getEmail());
        String result = userServices.saveUser(newUser);
        if(result.equals("Saved User")) {
            return "redirect:/login";
        }
        else{
            model.addAttribute("result", result);
            return "signup";
        }
    }

    @PostMapping("/process")
    public Map<String, String> process(@RequestBody Map<String, String> data) {
        String gesture = data.get("gesture");
        System.out.println("Received Gesture: " + gesture);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Gesture processed: " + gesture);
        return response;
    }



}
