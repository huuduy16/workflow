package com.wfl.domains.responses.error;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ApiError {

    ErrorCode code;
    String message;
}
