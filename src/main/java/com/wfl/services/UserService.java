package com.wfl.services;

import com.wfl.domains.entities.User;
import com.wfl.domains.forms.LoginForm;
import com.wfl.domains.forms.UserCreatedResponse;
import com.wfl.exceptions.AuthenticationFailedException;
import com.wfl.exceptions.ResourceAlreadyExistException;
import com.wfl.exceptions.ResourceNotFoundException;
import com.wfl.infrastructure.security.JwtUtils;
import com.wfl.infrastructure.security.UserPrincipal;
import com.wfl.repositories.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository,
        JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public UserCreatedResponse createUser(User user) {
        //must use repository due to @transactional
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistException(
                "Already exist account with email:  " + user.getEmail());
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User savedUser = userRepository.saveAndFlush(user);
        return UserCreatedResponse.builder().id(savedUser.getId()).email(savedUser.getEmail())
            .nickname(savedUser.getNickname()).build();
    }

    public Boolean isExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public String doLogin(LoginForm loginForm) {
        User user = userRepository.findByEmail(loginForm.getEmail());
        UserPrincipal userPrincipal = loadUserPrincipalByEmail(user.getEmail());
        if (!new BCryptPasswordEncoder().matches(loginForm.getPassword(),
            userPrincipal.getPassword())) {
            throw new AuthenticationFailedException("Wrong email or password");
        }
        return jwtUtils.generateToken(userPrincipal);
    }

    //make sure return not null
    public UserPrincipal loadUserPrincipalByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (ObjectUtils.isEmpty(user)) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        UserPrincipal userPrincipal = new UserPrincipal();
        Set<String> authorities = new HashSet<>();
        if (user.getRoles() != null) {
            user.getRoles().forEach(r -> {
                authorities.add(r.getRoleKey());
                r.getPermissions().forEach(p -> authorities.add(p.getPermissionKey()));
            });
        }
        userPrincipal.setUserId(user.getId());
        userPrincipal.setEmail(email);
        userPrincipal.setPassword(user.getPassword());
        userPrincipal.setAuthorities(authorities);
        return userPrincipal;
    }
}
