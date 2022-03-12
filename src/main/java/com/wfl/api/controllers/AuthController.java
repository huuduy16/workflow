package com.wfl.api.controllers;

import com.wfl.domains.entities.User;
import com.wfl.domains.forms.LoginForm;
import com.wfl.domains.responses.success.CreatedResponse;
import com.wfl.domains.responses.success.ResourceResponse;
import com.wfl.exceptions.FieldValidateException;
import com.wfl.services.UserService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if (!user.isValid()) {
            throw new FieldValidateException("User information is not valid");
        }
        return new CreatedResponse<>(userService.createUser(user));
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm) {
        return new ResourceResponse<>(userService.doLogin(loginForm));
    }
}
