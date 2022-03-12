package com.wfl.domains.forms;

import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginForm {

    @NotBlank
    String email;
    @NotBlank
    String password;
}
