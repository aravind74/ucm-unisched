package com.project.unischedapi.controller;

import com.project.unischedapi.model.Department;
import com.project.unischedapi.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lookup")
public class LookupController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/departmentList")
    public List<Department> getDepartmentList() {
        String sql = "SELECT DepartmentID, DepartmentName FROM Department WHERE IsActive = TRUE ORDER BY DepartmentID";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Department dept = new Department();
            dept.setDepartmentId(rs.getInt("DepartmentId"));
            dept.setDepartmentName(rs.getString("DepartmentName"));
            return dept;
        });
    }

    @GetMapping("/timeSlotList")
    public List<TimeSlot> getTimeSlotList() {
        String sql = "SELECT TimeSlotId, TimeSlot FROM TimeSlot ORDER BY TimeSlotId";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setTimeSlotId(rs.getInt("TimeSlotId"));
            timeSlot.setTimeSlot(rs.getString("TimeSlot"));
            return timeSlot;
        });
    }
}
