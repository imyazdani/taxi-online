package com.example.taxionline.exception;

public class TripNotFoundException extends RuntimeException {

    public TripNotFoundException(Long id) {
        super(String.format("Trip %s not found.", id));
    }
}
