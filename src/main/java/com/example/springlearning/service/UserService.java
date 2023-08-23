package com.example.springlearning.service;
import java.util.List;

import com.example.springlearning.dto.UserRegistrationDto;
import com.example.springlearning.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);
    List<User> getAll();
}
