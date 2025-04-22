package com.project.unischedapi.student.repository;

import com.project.unischedapi.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createStudent(Student student) {
        String sql = "INSERT INTO Student (FirstName, LastName, EmailID, UserID, LastUpdatedBy) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                student.getFirstName(),
                student.getLastName(),
                student.getEmailId(),
                student.getUserId(),
                student.getLastUpdatedBy());

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public Student getStudentDetails(Integer userId) {
        String sql = "SELECT * FROM student WHERE UserID = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class), userId);
    }

    public boolean studentExistsByEmail(String emailId) {
        String sql = "SELECT COUNT(*) FROM Student WHERE EmailID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, emailId);
        return count != null && count > 0;
    }

    public void updateStudentWithUserId(String emailId, Integer userId, String updatedBy) {
        String sql = """
            UPDATE Student
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
}
