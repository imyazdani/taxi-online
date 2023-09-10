package com.example.taxionline.model.request;

import lombok.Data;

@Data
public class PassengerRegisterRq {
    private String name;
    private String username;
    private String password;
}
