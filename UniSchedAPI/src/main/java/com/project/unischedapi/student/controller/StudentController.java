package com.project.unischedapi.student.controller;

import com.project.unischedapi.student.model.Student;
import com.project.unischedapi.student.model.StudentRegisterRequest;
import com.project.unischedapi.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody StudentRegisterRequest request) {
        try {
            studentService.registerStudent(request);
            return ResponseEntity.ok("Student registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Student> getStudentDetails(@PathVariable Integer userId) {
        Student student = studentService.getStudentDetails(userId);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
