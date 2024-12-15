package org.oasumainline.SpringStarter.services;

import org.oasumainline.SpringStarter.dto.UserRequestDto;
import org.oasumainline.SpringStarter.exceptions.UserAlreadyExistsException;
import org.oasumainline.SpringStarter.exceptions.UserDoesNotExistsOrInvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private UserDetailsManager userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserDetailsService userService, PasswordEncoder passwordEncoder) {
        this.userService = (UserDetailsManager) userService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(UserRequestDto user) {
        return userService.userExists(user.username());
    }

    public UserRequestDto createUser(UserRequestDto userRequest) {
        if(this.userExists(userRequest)){
            throw new UserAlreadyExistsException();
        }
        UserDetails userDetails = User.withUsername(userRequest.username()).password(passwordEncoder.encode(userRequest.password())).authorities("ROLE_USER").build();
        userService.createUser(userDetails);
        return userRequest;
    }

    public UserRequestDto loginUser(UserRequestDto userRequest) {
        if(!this.userExists(userRequest)) {
            throw new UserDoesNotExistsOrInvalidPasswordException();
        }
        UserDetails user = userService.loadUserByUsername(userRequest.username());
        boolean isPasswordMatch = passwordEncoder.matches(userRequest.password(), user.getPassword());
        if(isPasswordMatch) {
            return userRequest;
        }
        throw new UserDoesNotExistsOrInvalidPasswordException();
    }
}
