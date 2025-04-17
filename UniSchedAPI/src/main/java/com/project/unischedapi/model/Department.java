package com.project.unischedapi.model;

import lombok.Data;

@Data
public class Department extends User {
    private Integer departmentId;
    private String departmentName;
}
