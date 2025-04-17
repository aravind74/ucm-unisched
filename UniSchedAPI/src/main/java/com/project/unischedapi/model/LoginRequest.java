package com.project.unischedapi.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailId;
    private String password;
}