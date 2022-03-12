package com.wfl.domains.responses.success;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NoContentResponse extends ResponseEntity<Object> {

    public NoContentResponse() {
        super(HttpStatus.NO_CONTENT);
    }
}
