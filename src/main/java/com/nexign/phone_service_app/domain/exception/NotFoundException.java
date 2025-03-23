package com.nexign.phone_service_app.domain.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

}