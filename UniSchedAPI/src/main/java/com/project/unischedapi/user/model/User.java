package com.project.unischedapi.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String emailId;
//    @JsonIgnore  // Ignore password when serializing to JSON (don't expose in the response)
    private String password;
    private Integer roleId;
    private String roleName;
    private Timestamp createdAt;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private Boolean isActive;
}
