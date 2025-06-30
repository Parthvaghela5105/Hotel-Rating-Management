package com.mcwd.user.service.services;

import com.mcwd.user.service.entities.User;

import java.util.List;

public interface UserService {
    //user operations
    //Create
    User saveUser(User user);

    // get All user
    List<User> getAllUser();

    //get single user with user
    User getUser(String userId);


}
