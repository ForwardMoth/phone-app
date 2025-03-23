package com.nexign.phone_service_app.domain.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BadRequestException extends RuntimeException {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

}