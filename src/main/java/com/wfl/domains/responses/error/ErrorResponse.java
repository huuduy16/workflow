package com.wfl.domains.responses.error;

import com.wfl.domains.responses.error.ErrorResponse.Payload;
import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse extends ResponseEntity<Payload> {

    public ErrorResponse(@NonNull HttpStatus status, @NonNull ApiError error) {
        super(new Payload(status.value(), error), status);
    }

    @Data
    public static class Payload {

        @NonNull
        private Integer status;
        @NonNull
        private ApiError error;
    }
}
