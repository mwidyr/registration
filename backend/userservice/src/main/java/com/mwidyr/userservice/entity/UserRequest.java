package com.mwidyr.userservice.entity;

public class UserRequest {
    private Long id;
    private String mobileNumber;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String firstName;
    private String lastName;

    public UserRequest() {
    }

    public String getMobileNumber() {
        if (mobileNumber == null) {
            this.mobileNumber = "";
        }
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDateOfBirth() {
        if (dateOfBirth == null) {
            this.dateOfBirth = "";
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        if (gender == null) {
            this.gender = "";
        }
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        if (email == null) {
            this.email = "";
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRequest(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        if (firstName == null) {
            this.firstName = "";
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if (lastName == null) {
            this.lastName = "";
        }
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserUI{");
        sb.append("id=").append(id);
        sb.append(", mobileNumber='").append(mobileNumber).append('\'');
        sb.append(", dateOfBirth='").append(dateOfBirth).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
