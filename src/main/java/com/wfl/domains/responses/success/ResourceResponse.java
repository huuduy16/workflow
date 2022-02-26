package com.wfl.domains.responses.success;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResourceResponse<T> extends ResponseEntity<T> {

    public ResourceResponse(T body) {
        super(body, HttpStatus.OK);
    }
}
