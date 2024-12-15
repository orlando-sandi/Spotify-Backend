package org.oasumainline.SpringStarter.controllers;


import org.oasumainline.SpringStarter.dto.UserRequestDto;
import org.oasumainline.SpringStarter.services.AuthService;
import org.oasumainline.SpringStarter.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Message> signUp(@RequestBody UserRequestDto user) {
        authService.createUser(user);
        return ResponseEntity.ok(new Message("The user was registered successfully"));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserRequestDto> signIn(@RequestBody UserRequestDto user) {
        authService.loginUser(user);
        return ResponseEntity.ok(user);
    }
}
