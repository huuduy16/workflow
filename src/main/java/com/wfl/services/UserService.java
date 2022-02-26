package com.wfl.services;

import com.wfl.domains.entities.User;
import com.wfl.infrastructure.security.UserPrincipal;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wfl.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean isExistEmail(String email) {
        User tmp = userRepository.findByEmail(email);
        return (tmp != null) && (tmp.getEmail().equals(email));
    }

    @Transactional
    public void createUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public UserPrincipal loadUserPrincipalByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
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
        userPrincipal.setPassword(null);
        userPrincipal.setAuthorities(authorities);
        return userPrincipal;
    }
}
