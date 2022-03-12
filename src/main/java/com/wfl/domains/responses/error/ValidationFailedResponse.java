package com.wfl.domains.responses.error;

import com.wfl.domains.responses.error.ValidationFailedResponse.Payload;
import java.util.List;
import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ValidationFailedResponse extends ResponseEntity<Payload> {

    public ValidationFailedResponse(@NonNull List<ApiError> errors) {
        super(new Payload(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors),
            HttpStatus.BAD_REQUEST);
    }

    @Data
    public static class Payload {

        @NonNull
        Integer status;
        @NonNull
        String message;
        @NonNull
        List<ApiError> errors;
    }
}
