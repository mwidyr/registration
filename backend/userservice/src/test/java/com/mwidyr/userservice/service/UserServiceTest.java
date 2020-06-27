package com.mwidyr.userservice.service;

import com.mwidyr.userservice.entity.User;
import com.mwidyr.userservice.entity.UserRequest;
import com.mwidyr.userservice.entity.UserResponse;
import com.mwidyr.userservice.entity.UsersResponse;
import com.mwidyr.userservice.repository.UserRepository;
import com.mwidyr.userservice.util.Error;
import com.mwidyr.userservice.util.Util;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll() {
        final String firstName = "first-name";
        final String lastName = "last-name";
        final String mobileNumber = "mobileNumber";
        final String email = "email";

        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMobileNumber(mobileNumber);
        user.setEmail(email);
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        UsersResponse usersResponse = userService.findAll();

        assertEquals(1, usersResponse.getUserRequests().size());
        assertEquals(firstName, usersResponse.getUserRequests().get(0).getFirstName());
        assertEquals(lastName, usersResponse.getUserRequests().get(0).getLastName());
        assertEquals(mobileNumber, usersResponse.getUserRequests().get(0).getMobileNumber());
        assertEquals(email, usersResponse.getUserRequests().get(0).getEmail());

    }

    @Test
    public void allRequeestEmpty() {
        UserRequest userRequest = new UserRequest();

        UserResponse userResponse = userService.save(userRequest);

        assertNotNull(userResponse.getErrorMessage());
        assertEquals(4, userResponse.getErrorMessage().size());
        assertEquals(Error.REGISTER_USER_FIRST_NAME_EMPTY, userResponse.getErrorMessage().get(0));
        assertEquals(Error.REGISTER_USER_LAST_NAME_EMPTY, userResponse.getErrorMessage().get(1));
        assertEquals(Error.REGISTER_USER_MOBILE_NUMBER_NAME_EMPTY, userResponse.getErrorMessage().get(2));
        assertEquals(Error.REGISTER_USER_EMAIL_EMPTY, userResponse.getErrorMessage().get(3));
    }

    @Test
    public void mobileNumberNotValid() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("first-name");
        userRequest.setLastName("last-name");
        userRequest.setMobileNumber("0123456");
        userRequest.setEmail("johndoe@gmail.com");

        UserResponse userResponse = userService.save(userRequest);

        assertNotNull(userResponse.getErrorMessage());
        assertEquals(1, userResponse.getErrorMessage().size());
        assertEquals(Error.REGISTER_USER_MOBILE_NUMBER_NOT_VALID, userResponse.getErrorMessage().get(0));

        userRequest.setMobileNumber("+66802323");
        userResponse = userService.save(userRequest);

        assertNotNull(userResponse.getErrorMessage());
        assertEquals(1, userResponse.getErrorMessage().size());
        assertEquals(Error.REGISTER_USER_MOBILE_NUMBER_NOT_VALID, userResponse.getErrorMessage().get(0));
    }

    @Test
    public void emailNotValid() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("first-name");
        userRequest.setLastName("last-name");
        userRequest.setMobileNumber("082228989");
        userRequest.setEmail("johndoe@s");

        UserResponse userResponse = userService.save(userRequest);

        assertNotNull(userResponse.getErrorMessage());
        assertEquals(1, userResponse.getErrorMessage().size());
        assertEquals(Error.REGISTER_USER_EMAIL_NOT_VALID, userResponse.getErrorMessage().get(0));
    }

    @Test
    public void allRequestValidEmailAlreadyExist() {
        final String firstName = "first-name";
        final String lastName = "last-name";
        final String mobileNumber = "082220056868";
        final String email = "johndoe@gmail.com";

        final UserRequest userRequest = new UserRequest();
        userRequest.setFirstName(firstName);
        userRequest.setLastName(lastName);
        userRequest.setMobileNumber(mobileNumber);
        userRequest.setEmail(email);

        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMobileNumber(mobileNumber);
        user.setEmail(email);

        List<User> usersFromRepo = new ArrayList<>();
        usersFromRepo.add(user);

        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(usersFromRepo);
        when(userRepository.findByMobileNumber(Mockito.anyString())).thenReturn(new ArrayList<>());

        UserResponse userResponse = userService.save(userRequest);

        assertNotNull(userResponse.getErrorMessage());
        assertEquals(1, userResponse.getErrorMessage().size());
        assertEquals(Error.REGISTER_USER_EMAIL_ALREADY_EXIST, userResponse.getErrorMessage().get(0));
    }

    @Test
    public void allRequestValidMobileNumberAlreadyExist() {
        final String firstName = "first-name";
        final String lastName = "last-name";
        final String mobileNumber = "082220056868";
        final String email = "johndoe@gmail.com";

        final UserRequest userRequest = new UserRequest();
        userRequest.setFirstName(firstName);
        userRequest.setLastName(lastName);
        userRequest.setMobileNumber(mobileNumber);
        userRequest.setEmail(email);

        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMobileNumber(mobileNumber);
        user.setEmail(email);

        List<User> usersFromRepo = new ArrayList<>();
        usersFromRepo.add(user);

        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(new ArrayList<>());
        when(userRepository.findByMobileNumber(Mockito.anyString())).thenReturn(usersFromRepo);

        UserResponse userResponse = userService.save(userRequest);

        assertNotNull(userResponse.getErrorMessage());
        assertEquals(1, userResponse.getErrorMessage().size());
        assertEquals(Error.REGISTER_USER_MOBILE_NUMBER_ALREADY_EXIST, userResponse.getErrorMessage().get(0));
    }

    @Test
    public void allRequestValidDOBNotValidFormat() {
        final String firstName = "first-name";
        final String lastName = "last-name";
        final String mobileNumber = "+6282220056";
        final String email = "johndoe@gmail.com";
        final String dateOfBirth = "02-00-";

        final UserRequest userRequest = new UserRequest();
        userRequest.setFirstName(firstName);
        userRequest.setLastName(lastName);
        userRequest.setMobileNumber(mobileNumber);
        userRequest.setEmail(email);
        userRequest.setDateOfBirth(dateOfBirth);

        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(new ArrayList<>());
        when(userRepository.findByMobileNumber(Mockito.anyString())).thenReturn(new ArrayList<>());

        UserResponse userResponse = userService.save(userRequest);

        assertNotNull(userResponse.getErrorMessage());
        assertEquals(1, userResponse.getErrorMessage().size());
        assertEquals(Error.REGISTER_USER_DATE_OF_BIRTH_ERROR_FORMAT, userResponse.getErrorMessage().get(0));
    }

    @Test
    public void allRequestValidSaveSuccessful() {
        final String firstName = "first-name";
        final String lastName = "last-name";
        final String mobileNumber = "082220056868";
        final String email = "johndoe@gmail.com";
        final String gender = "male";
        final Date dateOfBirthFromRepo = new Date();
        final String dateOfBirth = new SimpleDateFormat(Util.DATE_OF_BIRTH_FORMAT).format(dateOfBirthFromRepo);

        final UserRequest userRequest = new UserRequest();
        userRequest.setFirstName(firstName);
        userRequest.setLastName(lastName);
        userRequest.setMobileNumber(mobileNumber);
        userRequest.setEmail(email);
        userRequest.setDateOfBirth(dateOfBirth);
        userRequest.setGender(gender);

        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMobileNumber(mobileNumber);
        user.setEmail(email);
        user.setDateOfBirth(dateOfBirthFromRepo);
        user.setGender(gender);

        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(new ArrayList<>());
        when(userRepository.findByMobileNumber(Mockito.anyString())).thenReturn(new ArrayList<>());
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserResponse userResponse = userService.save(userRequest);

        assertNotNull(userResponse.getUserRequest());
        assertEquals(0, userResponse.getErrorMessage().size());
        assertNotNull(userResponse.getErrorMessage());
        assertEquals(userRequest.getFirstName(), userResponse.getUserRequest().getFirstName());
        assertEquals(userRequest.getLastName(), userResponse.getUserRequest().getLastName());
        assertEquals(userRequest.getMobileNumber(), userResponse.getUserRequest().getMobileNumber());
        assertEquals(userRequest.getEmail(), userResponse.getUserRequest().getEmail());
        assertEquals(userRequest.getGender(), userResponse.getUserRequest().getGender());
        assertEquals(userRequest.getDateOfBirth(), userResponse.getUserRequest().getDateOfBirth());
    }

    @Test
    public void simpleDateFormat() throws ParseException {
        String dobFormat = "01-12-2012";
        Date dateOfBirth = new SimpleDateFormat(Util.DATE_OF_BIRTH_FORMAT).parse(dobFormat);
        System.out.println(dateOfBirth);
    }
}
