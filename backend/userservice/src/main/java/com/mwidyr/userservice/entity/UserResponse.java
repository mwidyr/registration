package com.mwidyr.userservice.entity;

import com.mwidyr.userservice.util.Error;

import java.util.List;

public class UserResponse {
    UserRequest userRequest;
    List<Error> errorMessage;

    public UserResponse() {
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public List<Error> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<Error> errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserResponse{");
        sb.append("userRequest=").append(userRequest);
        sb.append(", errorMessage=").append(errorMessage);
        sb.append('}');
        return sb.toString();
    }
}
