package com.wfl.domains.responses.error;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ApiError {

    @NonNull
    private ErrorCode code;
    private String message;
}
