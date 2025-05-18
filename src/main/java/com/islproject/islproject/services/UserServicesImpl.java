package com.islproject.islproject.services;

import com.islproject.islproject.Helper.GetUserName;
import com.islproject.islproject.entities.User;
import com.islproject.islproject.forms.UserForm;
import com.islproject.islproject.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserServicesImpl implements UserServices{

    @Autowired
    UserRepository userRepository;

    @Autowired
    GetUserName getUserName;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String saveUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            return "UserName Exists";
        else if(userRepository.findByUsername(user.getUsername()).isPresent())
                return "Email Exists";
        else{
            user.setUserId(UUID.randomUUID().toString());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "Saved User";
        }
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user=userRepository.findByUsername(username).orElse(null);
        return user;
    }

    @Override
    public boolean deleteUser(String username) {
        User user = findUserByUsername(username);
        if(user == null)
            return false;
        userRepository.delete(user);
        return true;
    }

    @Override
    public boolean updateUser(UserForm userForm) {
        User user= findUserByUsername(userForm.getUsername());
        if(user == null)
            return false;

        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean validateUser(UserForm userForm) {
        User user = userRepository.findByUsername(userForm.getUsername()).orElse(null);
        if (user != null)
            return bCryptPasswordEncoder.matches(userForm.getPassword(),user.getPassword());
        return false;
    }

    @Override
    public String getAPIkey(String username) {
        User user= findUserByUsername(username);
        String apiKey = user.getApiKey();
        if(apiKey == null && checkPurchasedKey()){
            apiKey = UUID.randomUUID().toString();
            user.setApiKey(apiKey);
            userRepository.save(user);
        }
        return apiKey;
    }

    @Override
    public String newAPIkey(String username) {
        User user= findUserByUsername(username);
        String apiKey = null;
        if(checkPurchasedKey()) {
            apiKey = UUID.randomUUID().toString();
            user.setApiKey(apiKey);
            userRepository.save(user);
        }
        return apiKey;
    }

    @Override
    public boolean checkPurchasedKey() {
        User user= findUserByUsername(getUserName.findUsername());
        log.info("Username giving error is: " + user.toString());
        if(user.isPurchasedKey())
            return true;
        return false;
    }

    public void updatePurchasedKey(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null){
            user.setPurchasedKey(true);
            userRepository.save(user);
        }
    }

}
