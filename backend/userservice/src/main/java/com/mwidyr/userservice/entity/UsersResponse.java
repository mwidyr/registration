package com.mwidyr.userservice.entity;

import java.util.List;

public class UsersResponse {
    List<UserRequest> userRequests;
    List<String> errorMessage;

    public UsersResponse() {
    }

    public List<UserRequest> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(List<UserRequest> userRequests) {
        this.userRequests = userRequests;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UsersResponse{");
        sb.append("userRequests=").append(userRequests);
        sb.append(", errorMessage=").append(errorMessage);
        sb.append('}');
        return sb.toString();
    }
}
