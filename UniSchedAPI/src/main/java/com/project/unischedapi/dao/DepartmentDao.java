package com.project.unischedapi.dao;

import com.project.unischedapi.model.Department;
import com.project.unischedapi.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class DepartmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertDepartment(Department department) {
        String sql = "INSERT INTO Department (DepartmentName, EmailID, UserID, IsActive, LastUpdatedBy, LastUpdated) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                department.getDepartmentName(),
                department.getEmailId(),
                department.getUserId(),
                true,
                "system",
                new Timestamp(System.currentTimeMillis())
        );
    }
}
