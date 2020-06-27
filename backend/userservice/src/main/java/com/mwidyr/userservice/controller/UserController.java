package com.mwidyr.userservice.controller;

import com.mwidyr.userservice.entity.UserRequest;
import com.mwidyr.userservice.entity.UserResponse;
import com.mwidyr.userservice.entity.UsersResponse;
import com.mwidyr.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    IUserService userService;

    @CrossOrigin
    @PostMapping("/create")
    public UserResponse create(@RequestBody UserRequest user) {
        return userService.save(user);
    }

    @CrossOrigin
    @GetMapping("/findall")
    public UsersResponse findAll() {
        return userService.findAll();
    }

}
