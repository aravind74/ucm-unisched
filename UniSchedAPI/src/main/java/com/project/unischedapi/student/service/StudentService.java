package com.project.unischedapi.student.service;

import com.project.unischedapi.student.model.Student;
import com.project.unischedapi.student.model.StudentRegisterRequest;
import com.project.unischedapi.student.repository.StudentRepository;
import com.project.unischedapi.user.model.User;
import com.project.unischedapi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;

    public void registerStudent(StudentRegisterRequest request) {
        // Step 1: Create User
        User user = new User();
        user.setEmailId(request.getEmailId());
        user.setPassword(request.getPassword());
        user.setRoleId(1); // 1 = STUDENT
        user.setLastUpdatedBy(request.getLastUpdatedBy());

        Integer userId = userService.registerUser(user);

        boolean exists = studentRepository.studentExistsByEmail(request.getEmailId());

        if (exists) {
            // Update existing student with userId
            studentRepository.updateStudentWithUserId(
                    request.getEmailId(),
                    userId,
                    request.getLastUpdatedBy()
            );
        } else {

            // Step 2: Save student
            Student student = new Student();
            student.setUserId(userId);
            student.setFirstName(request.getFirstName());
            student.setLastName(request.getLastName());
            student.setEmailId(request.getEmailId());
            student.setLastUpdatedBy(request.getLastUpdatedBy());

            studentRepository.createStudent(student);
        }
    }


    public Student getStudentDetails(Integer userId) {
        return studentRepository.getStudentDetails(userId);
    }
}

