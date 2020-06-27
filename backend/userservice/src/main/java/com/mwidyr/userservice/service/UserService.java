package com.mwidyr.userservice.service;

import com.mwidyr.userservice.entity.User;
import com.mwidyr.userservice.entity.UserRequest;
import com.mwidyr.userservice.entity.UserResponse;
import com.mwidyr.userservice.entity.UsersResponse;
import com.mwidyr.userservice.repository.UserRepository;
import com.mwidyr.userservice.util.Error;
import com.mwidyr.userservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements IUserService {
    List<String> mobileNumberBaseFormat = new ArrayList<>(Arrays.asList("+628", "08"));

    @Autowired
    UserRepository repository;

    @Override
    public UserResponse save(UserRequest user) {
        UserResponse userResponse = validateSaveUserParameter(user);
        if (!userResponse.getErrorMessage().isEmpty()) {
            return userResponse;
        }
        if (!validateRegistrationRequirement(userResponse).getErrorMessage().isEmpty()) {
            return userResponse;
        }
        try {
            userResponse.setUserRequest(saveUser(user));
        } catch (ParseException ex) {
            System.out.println(ex.toString());
            List<Error> errorMessages = new ArrayList<>();
            errorMessages.add(Error.REGISTER_USER_DATE_OF_BIRTH_ERROR_FORMAT);
            userResponse.setErrorMessage(errorMessages);
        }
        return userResponse;
    }

    private UserRequest saveUser(UserRequest user) throws ParseException {
        User successSaveUser = repository.save(wrapUser(user));
        return wrapUserResponse(successSaveUser);
    }

    @Override
    public UsersResponse findAll() {
        List<User> Users = repository.findAll();
        List<UserRequest> userResponseList = new ArrayList<>();
        for (User user : Users) {
            userResponseList.add(wrapUserResponse(user));
        }
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setUserRequests(userResponseList);
        return usersResponse;
    }

    private User wrapUser(UserRequest user) throws ParseException {
        User userWrapper = new User();
        userWrapper.setFirstName(user.getFirstName());
        userWrapper.setLastName(user.getLastName());
        userWrapper.setMobileNumber(wrapMobileNumberToValidMobileNumber(user.getMobileNumber()));
        userWrapper.setEmail(user.getEmail());
        userWrapper.setGender(user.getGender());
        if (user.getDateOfBirth() != null &&
                !user.getDateOfBirth().equalsIgnoreCase("")) {
            Date dateOfBirth = new SimpleDateFormat(Util.DATE_OF_BIRTH_FORMAT).parse(user.getDateOfBirth());
            userWrapper.setDateOfBirth(dateOfBirth);
        }
        return userWrapper;
    }

    private UserRequest wrapUserResponse(User user) {
        UserRequest userResponseWrapper = new UserRequest();
        userResponseWrapper.setFirstName(user.getFirstName());
        userResponseWrapper.setLastName(user.getLastName());
        userResponseWrapper.setMobileNumber(user.getMobileNumber());
        userResponseWrapper.setEmail(user.getEmail());
        userResponseWrapper.setGender(user.getGender());
        if (user.getDateOfBirth() != null) {
            String dateOfBirth = new SimpleDateFormat(Util.DATE_OF_BIRTH_FORMAT).format(user.getDateOfBirth());
            userResponseWrapper.setDateOfBirth(dateOfBirth);
        }
        return userResponseWrapper;
    }

    // validate request param
    private UserResponse validateSaveUserParameter(UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();
        List<Error> errorMessage = new ArrayList<>();
        if (userRequest.getFirstName().equalsIgnoreCase("")) {
            errorMessage.add(Error.REGISTER_USER_FIRST_NAME_EMPTY);
        }
        if (userRequest.getLastName().equalsIgnoreCase("")) {
            errorMessage.add(Error.REGISTER_USER_LAST_NAME_EMPTY);
        }
        if (userRequest.getMobileNumber().equalsIgnoreCase("")) {
            errorMessage.add(Error.REGISTER_USER_MOBILE_NUMBER_NAME_EMPTY);
        } else {
            boolean isPhoneValid = false;
            if (Util.isValidIndonesiaMobileNumber(userRequest.getMobileNumber())) {
                // if match then recheck if it is mobile number
                if (userRequest.getMobileNumber().substring(0, 4).equalsIgnoreCase("+628")) {
                    isPhoneValid = true;
                }
                if (userRequest.getMobileNumber().substring(0, 2).equalsIgnoreCase("08")) {
                    isPhoneValid = true;
                }
            }
            if (!isPhoneValid) {
                errorMessage.add(Error.REGISTER_USER_MOBILE_NUMBER_NOT_VALID);
            }
        }
        if (userRequest.getEmail().equalsIgnoreCase("")) {
            errorMessage.add(Error.REGISTER_USER_EMAIL_EMPTY);
        } else if (!Util.isEmailValid(userRequest.getEmail())) {
            errorMessage.add(Error.REGISTER_USER_EMAIL_NOT_VALID);

        }
        userResponse.setErrorMessage(errorMessage);
        userResponse.setUserRequest(userRequest);
        return userResponse;
    }

    // validate logic requirement
    private UserResponse validateRegistrationRequirement(UserResponse userResponse) {
        List<Error> errorMessage = new ArrayList<>();
        List<User> Users = repository.findByEmail(userResponse.getUserRequest().getEmail());
        if (!Users.isEmpty()) {
            errorMessage.add(Error.REGISTER_USER_EMAIL_ALREADY_EXIST);
        }
        List<User> UsersPhoneNumber = repository.findByMobileNumber(wrapMobileNumberToValidMobileNumber(userResponse.getUserRequest().getMobileNumber()));
        if (!UsersPhoneNumber.isEmpty()) {
            errorMessage.add(Error.REGISTER_USER_MOBILE_NUMBER_ALREADY_EXIST);
        }
        userResponse.setErrorMessage(errorMessage);
        return userResponse;
    }

    private String wrapMobileNumberToValidMobileNumber(String mobileNumber) {
        mobileNumber.replaceAll("\\s+", "");
        if (mobileNumber.substring(0, 4).equalsIgnoreCase(mobileNumberBaseFormat.get(0))) {
            return "0" + mobileNumber.substring(3);
        }
        if (mobileNumber.substring(0, 2).equalsIgnoreCase(mobileNumberBaseFormat.get(1))) {
            return mobileNumber;
        }
        return mobileNumber;
    }
}
