package com.project.unischedapi.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Integer userId;
    private String emailId;
    private String password;
    private Integer roleId;
    private String roleName;
    private Timestamp createdAt;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private Boolean isActive;
}
