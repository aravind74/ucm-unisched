package com.project.unischedapi.student.model;

import com.project.unischedapi.user.model.User;
import lombok.Data;

@Data
public class Student extends User {
    private String studentId;
    private String firstName;
    private String lastName;
}
