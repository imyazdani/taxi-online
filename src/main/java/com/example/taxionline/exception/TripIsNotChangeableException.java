package com.example.taxionline.exception;

public class TripIsNotChangeableException extends RuntimeException {

    public TripIsNotChangeableException(Long id) {
        super(String.format("Trip state %s is not changeable.", id));
    }
}
