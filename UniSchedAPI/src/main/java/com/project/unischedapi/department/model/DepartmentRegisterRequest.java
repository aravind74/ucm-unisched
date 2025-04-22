package com.project.unischedapi.department.model;

import lombok.Data;

@Data
public class DepartmentRegisterRequest {
    private String emailId;
    private String password;
    private String departmentName;
    private String lastUpdatedBy;
}
