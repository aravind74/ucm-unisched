package com.project.unischedapi.model;

import lombok.Data;

@Data
public class Student extends User {
    private String studentId;
    private String firstName;
    private String lastName;
}
