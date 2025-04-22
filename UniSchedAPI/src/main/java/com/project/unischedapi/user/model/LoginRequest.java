package com.project.unischedapi.user.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailId;
    private String password;
}