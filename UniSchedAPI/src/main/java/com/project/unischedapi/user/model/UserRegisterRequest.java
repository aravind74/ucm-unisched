package com.project.unischedapi.user.model;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String emailId;
    private String password;
    private Integer roleId;
    private String lastUpdatedBy;
}