package com.project.unischedapi.dao;

import com.project.unischedapi.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class StudentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertStudent(Student student) {
        String sql = "INSERT INTO Student (FirstName, LastName, EmailID, UserID, IsActive, LastUpdatedBy, LastUpdated) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                student.getFirstName(),
                student.getLastName(),
                student.getEmailId(),
                student.getUserId(),
                true,
                "system",
                new Timestamp(System.currentTimeMillis())
        );
    }
}
