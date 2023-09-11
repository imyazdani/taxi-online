package com.example.taxionline.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(
            UserNotFoundException ex, HttpServletRequest request) {

        var body = createBody(ex, request, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<Object> userDuplicateException(
            UserDuplicateException ex, HttpServletRequest request) {

        var body = createBody(ex, request, HttpStatus.CONFLICT);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<Object> tripNotFoundException(
            TripNotFoundException ex, HttpServletRequest request) {

        var body = createBody(ex, request, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(TripIsNotChangeableException.class)
    public ResponseEntity<Object> tripIsNotChangeableException(
            TripIsNotChangeableException ex, HttpServletRequest request) {

        var body = createBody(ex, request, HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DriverException.class)
    public ResponseEntity<Object> DriverException(
            DriverException ex, HttpServletRequest request) {

        var body = createBody(ex, request, HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> generalException(
            Exception ex, HttpServletRequest request) {

        var body = createBody(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private Map<String, Object> createBody(Exception ex, HttpServletRequest request, HttpStatus httpStatus) {
        log.error(ex.getClass().getSimpleName(), ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", httpStatus.value());
        body.put("error", ex.getClass().getSimpleName());
        body.put("message", ex.getMessage());
        body.put("path", request.getServletPath());

        return body;
    }
}
