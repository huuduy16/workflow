package com.wfl.domains.responses.success;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreatedResponse<T> extends ResponseEntity<T> {

    public CreatedResponse(T body) {
        super(body, HttpStatus.CREATED);
    }
}
