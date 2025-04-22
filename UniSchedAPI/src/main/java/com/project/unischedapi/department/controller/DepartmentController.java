package com.project.unischedapi.department.controller;

import com.project.unischedapi.department.model.Department;
import com.project.unischedapi.department.model.DepartmentRegisterRequest;
import com.project.unischedapi.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/register")
    public ResponseEntity<String> registerDepartment(@RequestBody DepartmentRegisterRequest request) {
        try {
            departmentService.registerDepartment(request);
            return ResponseEntity.ok("Department registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Department> getDepartmentDetails(@PathVariable Integer userId) {
        Department department = departmentService.getDepartmentDetails(userId);
        if (department != null) {
            return ResponseEntity.ok(department);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getDepartmentList")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }
}
