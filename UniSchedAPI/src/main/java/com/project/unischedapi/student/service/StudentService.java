package com.project.unischedapi.student.service;

import com.project.unischedapi.student.model.Student;
import com.project.unischedapi.student.model.StudentRegisterRequest;
import com.project.unischedapi.student.repository.StudentRepository;
import com.project.unischedapi.user.model.User;
import com.project.unischedapi.user.model.UserRegisterRequest;
import com.project.unischedapi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void registerStudent(StudentRegisterRequest request) {
        // Step 1: Create User
        User user = new User();
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmailId(request.getEmailId());
        userRegisterRequest.setPassword(request.getPassword());
        userRegisterRequest.setRoleId(1); // 1 = STUDENT
        userRegisterRequest.setLastUpdatedBy(request.getLastUpdatedBy());

        // Call User Microservice
        Integer userId = restTemplate.postForObject("http://localhost:8081/api/user/register", userRegisterRequest, Integer.class);

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

