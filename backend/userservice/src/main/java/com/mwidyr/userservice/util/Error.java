package com.mwidyr.userservice.util;

public enum Error {
    REGISTER_USER_FIRST_NAME_EMPTY(1, "First Name is empty"),
    REGISTER_USER_LAST_NAME_EMPTY(2, "Last Name is empty"),
    REGISTER_USER_MOBILE_NUMBER_NAME_EMPTY(3, "Mobile Number is empty"),
    REGISTER_USER_MOBILE_NUMBER_NOT_VALID(4, "Mobile Number is not valid"),
    REGISTER_USER_EMAIL_EMPTY(5, "Email is empty"),
    REGISTER_USER_EMAIL_NOT_VALID(6, "Please use valid email format"),
    REGISTER_USER_EMAIL_ALREADY_EXIST(7, "Email is already exist"),
    REGISTER_USER_MOBILE_NUMBER_ALREADY_EXIST(8, "Mobile Number is already exist"),
    REGISTER_USER_DATE_OF_BIRTH_ERROR_FORMAT(8, "Error Format Date of Birth");

    private final int code;
    private final String description;

    private Error(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
