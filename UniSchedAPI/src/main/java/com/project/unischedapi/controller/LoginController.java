package com.project.unischedapi.controller;

import com.project.unischedapi.dao.DepartmentDao;
import com.project.unischedapi.dao.StudentDao;
import com.project.unischedapi.dao.UserDao;
import com.project.unischedapi.model.Department;
import com.project.unischedapi.model.LoginRequest;
import com.project.unischedapi.model.Student;
import com.project.unischedapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

@RestController
@RequestMapping("/api")

public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDAO;

    @Autowired
    private StudentDao studentDAO;
    @Autowired
    private DepartmentDao departmentDao;


    @PostMapping("/signup/student")
    public ResponseEntity<Map<String, Object>> signUpStudent(@RequestBody Student studentRequest) {
        String hashedPassword = passwordEncoder.encode(studentRequest.getPassword());

        User user = new User();
        user.setEmailId(studentRequest.getEmailId());
        user.setPassword(hashedPassword);
        user.setRoleId(studentRequest.getRoleId());
        user.setLastUpdatedBy("system");
        user.setIsActive(true);

        int userId = userDAO.insertUser(user);

        Student student = new Student();
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setEmailId(studentRequest.getEmailId());
        student.setUserId(userId);
        student.setLastUpdatedBy("system");

        studentDAO.insertStudent(student);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("roleId", studentRequest.getRoleId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/department")
    public ResponseEntity<Map<String, Object>> signUpDepartment(@RequestBody Department departmentRequest) {
        String hashedPassword = passwordEncoder.encode(departmentRequest.getPassword());

        User user = new User();
        user.setEmailId(departmentRequest.getEmailId());
        user.setPassword(hashedPassword);
        user.setRoleId(departmentRequest.getRoleId()); // Assuming 1 = STUDENT
        user.setLastUpdatedBy("system");
        user.setIsActive(true);

        int userId = userDAO.insertUser(user);

        Department department = new Department();
        department.setDepartmentName(departmentRequest.getDepartmentName());
        department.setEmailId(departmentRequest.getEmailId());
        department.setUserId(userId);
        department.setLastUpdatedBy("system");

        departmentDao.insertDepartment(department);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("roleId", departmentRequest.getRoleId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userDAO.getUserLogin(loginRequest.getEmailId());

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid credentials"));
            }

            // Optional: generate JWT here if needed later

            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("userRoleId", user.getRoleId());
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error: " + e.getMessage()));
        }
    }

}
