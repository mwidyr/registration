package com.mwidyr.userservice.service;

import com.mwidyr.userservice.entity.UserRequest;
import com.mwidyr.userservice.entity.UserResponse;
import com.mwidyr.userservice.entity.UsersResponse;

import java.util.List;

public interface IUserService {
    UserResponse save(UserRequest user);

    UsersResponse findAll();
}
