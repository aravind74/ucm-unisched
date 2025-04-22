package com.project.unischedapi.department.repository;

import com.project.unischedapi.appointment.model.TimeSlot;
import com.project.unischedapi.department.model.Department;
import com.project.unischedapi.department.model.DepartmentRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class DepartmentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createDepartment(Department request) {
        String sql = "INSERT INTO Department (DepartmentName, EmailID, UserID, LastUpdatedBy) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                request.getDepartmentName(),
                request.getEmailId(),
                request.getUserId(),
                request.getLastUpdatedBy());

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public boolean departmentExistsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Department WHERE EmailID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public void updateDepartmentWithUserId(String emailId, Integer userId, String updatedBy) {
        String sql = """
            UPDATE Department
            SET UserID = ?, LastUpdated = ?, LastUpdatedBy = ?
            WHERE EmailID = ?
        """;
        jdbcTemplate.update(sql,
                userId,
                new Timestamp(System.currentTimeMillis()),
                updatedBy,
                emailId
        );
    }

    public Department getDepartmentDetails(Integer userId) {
        String sql = "SELECT * FROM department WHERE UserID = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Department.class), userId);
    }

    public List<Department> getAllDepartments() {
        String sql = "SELECT DepartmentID, DepartmentName FROM Department WHERE IsActive = true ORDER BY DepartmentID";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Department.class));
    }
}
