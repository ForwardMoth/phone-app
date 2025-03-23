package com.nexign.phone_service_app.config;

import com.nexign.phone_service_app.domain.exception.BadRequestException;
import com.nexign.phone_service_app.domain.exception.NotFoundException;
import com.nexign.phone_service_app.domain.record.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonException> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CommonException(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonException> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CommonException(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

}
