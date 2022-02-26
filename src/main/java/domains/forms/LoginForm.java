package domains.forms;

import lombok.Value;

@Value
public class LoginForm {
    String email;
    String password;
}
