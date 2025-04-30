package com.project.unischedapi.user.service;

import com.project.unischedapi.department.service.DepartmentService;
import com.project.unischedapi.student.service.StudentService;
import com.project.unischedapi.user.model.User;
import com.project.unischedapi.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmailId(email);

        if (user == null || !user.getIsActive()) {
            throw new RuntimeException("Invalid credentials or inactive account");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }
        return user;
    }

    public Integer registerUser(User user) {
        if (userRepository.emailExists(user.getEmailId())) {
            throw new RuntimeException("Email already exists");
        }
        // Hash password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        return userRepository.createUser(user);
    }
}
