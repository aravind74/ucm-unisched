package com.project.unischedapi.department.model;

import com.project.unischedapi.user.model.User;
import lombok.Data;

@Data
public class Department extends User {
    private Integer departmentId;
    private String departmentName;
}
