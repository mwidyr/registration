package com.mwidyr.userservice.controller;

import com.mwidyr.userservice.entity.User;
import com.mwidyr.userservice.entity.UserRequest;
import com.mwidyr.userservice.entity.UserResponse;
import com.mwidyr.userservice.entity.UsersResponse;
import com.mwidyr.userservice.service.UserService;
import com.mwidyr.userservice.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        final String firstName = "first-name";
        final String lastName = "last-name";
        final String mobileNumber = "082220056868";
        final String email = "johndoe@gmail.com";
        final Date dateOfBirthFromRepo = new Date();
        final String dateOfBirth = new SimpleDateFormat(Util.DATE_OF_BIRTH_FORMAT).format(dateOfBirthFromRepo);

        UserResponse userResponse = new UserResponse();
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName(firstName);
        userRequest.setLastName(lastName);
        userRequest.setMobileNumber(mobileNumber);
        userRequest.setEmail(email);
        userRequest.setDateOfBirth(dateOfBirth);
        userResponse.setUserRequest(userRequest);

        when(userService.save(Mockito.any(UserRequest.class))).thenReturn(userResponse);

        UserResponse createResponse = userController.create(new UserRequest());

        assertNotNull(createResponse);
        assertNotNull(createResponse.getUserRequest());
        assertEquals(userResponse.getUserRequest().getFirstName(), createResponse.getUserRequest().getFirstName());
        assertEquals(userResponse.getUserRequest().getLastName(), createResponse.getUserRequest().getLastName());
        assertEquals(userResponse.getUserRequest().getMobileNumber(), createResponse.getUserRequest().getMobileNumber());
        assertEquals(userResponse.getUserRequest().getEmail(), createResponse.getUserRequest().getEmail());
        assertEquals(userResponse.getUserRequest().getGender(), createResponse.getUserRequest().getGender());
        assertEquals(userResponse.getUserRequest().getDateOfBirth(), createResponse.getUserRequest().getDateOfBirth());
    }

    @Test
    public void findAll() {
        final String firstName = "first-name";
        final String lastName = "last-name";
        final String mobileNumber = "082220056868";
        final String email = "johndoe@gmail.com";
        final Date dateOfBirthFromRepo = new Date();
        final String dateOfBirth = new SimpleDateFormat(Util.DATE_OF_BIRTH_FORMAT).format(dateOfBirthFromRepo);

        UserResponse userResponse = new UserResponse();
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName(firstName);
        userRequest.setLastName(lastName);
        userRequest.setMobileNumber(mobileNumber);
        userRequest.setEmail(email);
        userRequest.setDateOfBirth(dateOfBirth);
        userResponse.setUserRequest(userRequest);
        List<UserRequest> usersResponseList = new ArrayList<>();
        usersResponseList.add(userRequest);

        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setUserRequests(usersResponseList);
        when(userService.findAll()).thenReturn(usersResponse);

        UsersResponse createResponse = userController.findAll();

        assertNotNull(createResponse);
        assertNotNull(createResponse.getUserRequests());
        assertEquals(1, createResponse.getUserRequests().size());
        assertEquals(userResponse.getUserRequest().getFirstName(), createResponse.getUserRequests().get(0).getFirstName());
        assertEquals(userResponse.getUserRequest().getLastName(), createResponse.getUserRequests().get(0).getLastName());
        assertEquals(userResponse.getUserRequest().getMobileNumber(), createResponse.getUserRequests().get(0).getMobileNumber());
        assertEquals(userResponse.getUserRequest().getEmail(), createResponse.getUserRequests().get(0).getEmail());
        assertEquals(userResponse.getUserRequest().getGender(), createResponse.getUserRequests().get(0).getGender());
        assertEquals(userResponse.getUserRequest().getDateOfBirth(), createResponse.getUserRequests().get(0).getDateOfBirth());
    }
}
