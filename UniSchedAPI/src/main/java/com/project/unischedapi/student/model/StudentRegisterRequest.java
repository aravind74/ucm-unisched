package com.project.unischedapi.student.model;

import lombok.Data;

@Data
public class StudentRegisterRequest {
    private String emailId;
    private String password;
    private String firstName;
    private String lastName;
    private String lastUpdatedBy;
}
