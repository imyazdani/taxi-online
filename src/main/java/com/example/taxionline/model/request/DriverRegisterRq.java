package com.example.taxionline.model.request;

import lombok.Data;

@Data
public class DriverRegisterRq {
    private String name;
    private String username;
    private String password;
    private String car;
}
