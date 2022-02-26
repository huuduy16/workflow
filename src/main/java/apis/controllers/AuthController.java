package apis.controllers;

import domains.entities.User;
import domains.forms.LoginForm;
import domains.responses.error.ApiError;
import domains.responses.error.ErrorCode;
import domains.responses.error.ErrorResponse;
import domains.responses.success.CreatedResponse;
import domains.responses.success.NoContentResponse;
import infrastructure.security.JwtUtils;
import infrastructure.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import services.UserService;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (userService.isExistEmail(user.getEmail())) {
            return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED,
                new ApiError(ErrorCode.METHOD_NOT_ALLOWED, "Account is already existed"));
        }
        userService.createUser(user);
        return new CreatedResponse<>(user.getNickname());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm user) {
        if (user == null) {
            return new NoContentResponse();
        }
        UserPrincipal userPrincipal = userService.loadUserPrincipalByEmail(user.getEmail());
        if (userPrincipal == null || !new BCryptPasswordEncoder().matches(user.getPassword(),
            userPrincipal.getPassword())) {
            return ResponseEntity.badRequest().body("Email or password is wrong");
        }
        String token = jwtUtils.generateToken(userPrincipal);
        return ResponseEntity.ok(token);
    }
}
