package com.islproject.islproject.services;

import com.islproject.islproject.entities.User;
import com.islproject.islproject.forms.UserForm;

public interface UserServices {
    String saveUser(User user);
    User findUserByEmail(String email);
    User findUserByUsername(String username);
    boolean deleteUser(String username);
    boolean updateUser(UserForm user);
    boolean validateUser(UserForm user);
    String getAPIkey(String username);
    String newAPIkey(String username);
    boolean checkPurchasedKey();
}
