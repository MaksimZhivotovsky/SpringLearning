package com.example.springlearning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserRegistrationDto() {

    }
}
