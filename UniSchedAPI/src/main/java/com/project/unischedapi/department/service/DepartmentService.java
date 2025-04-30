package com.project.unischedapi.department.service;

import com.project.unischedapi.appointment.model.TimeSlot;
import com.project.unischedapi.department.model.Department;
import com.project.unischedapi.department.model.DepartmentRegisterRequest;
import com.project.unischedapi.department.repository.DepartmentRepository;
import com.project.unischedapi.student.model.Student;
import com.project.unischedapi.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void registerDepartment(DepartmentRegisterRequest request) {
        // Step 1: Create User
        User user = new User();
        user.setEmailId(request.getEmailId());
        user.setPassword(request.getPassword());
        user.setRoleId(2); // 2 = Department
        user.setLastUpdatedBy(request.getLastUpdatedBy());

        // Call User Microservice
        Integer userId = restTemplate.postForObject("http://localhost:8081/api/user/register", user, Integer.class);

        boolean exists = departmentRepository.departmentExistsByEmail(request.getEmailId());

        if (exists) {
            // Only update UserID in existing department row
            departmentRepository.updateDepartmentWithUserId(
                    request.getEmailId(),
                    userId,
                    request.getLastUpdatedBy()
            );
        } else {

            // Step 2: Save department
            Department department = new Department();
            department.setUserId(userId);
            department.setDepartmentName(request.getDepartmentName());
            department.setEmailId(request.getEmailId());
            department.setLastUpdatedBy(request.getLastUpdatedBy());

            departmentRepository.createDepartment(department);
        }
    }

    public Department getDepartmentDetails(Integer userId) {
        return departmentRepository.getDepartmentDetails(userId);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }
}
