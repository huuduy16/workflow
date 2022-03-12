package com.wfl.domains.forms;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreatedResponse {
    private Long id;
    private String email;
    private String nickname;
}
