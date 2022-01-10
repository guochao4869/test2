package com.example.test1.service;

import com.example.test1.pojo.Result;
import com.example.test1.pojo.User;

import java.util.Map;

/**
 * @author OuLa-test
 */
public interface UserService {

    Result login(User user);


    Result getOneUser(String username);

    Result addUser(User user);


}
