package com.project.unischedapi.appointment.controller;

import com.project.unischedapi.appointment.model.Appointment;
import com.project.unischedapi.appointment.model.TimeSlot;
import com.project.unischedapi.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<String> create(@RequestBody Appointment request) {
        int appointmentId = appointmentService.create(request);
        return ResponseEntity.ok("Appointment created with ID: " + appointmentId);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Appointment>> getByStudent(@PathVariable Integer studentId) {
        return ResponseEntity.ok(appointmentService.getByStudentId(studentId));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Appointment>> getByDepartment(@PathVariable Integer departmentId) {
        return ResponseEntity.ok(appointmentService.getByDepartmentId(departmentId));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Appointment updated) {
        try {
            updated.setAppointmentId(id);
            appointmentService.updateAppointment(updated);
            return ResponseEntity.ok("Appointment updated successfully");
        } catch (Exception e) {
            // Log the exception if you have a logger, or print the stack trace
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update appointment: " + e.getMessage());
        }
    }

    @DeleteMapping("/department/history/{departmentId}")
    public ResponseEntity<String> clearHistory(@PathVariable Integer departmentId) {
        try {
            appointmentService.clearAppointmentHistory(departmentId);
            return ResponseEntity.ok("Appointment history cleared successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to clear appointment history.");
        }
    }

    @PutMapping("cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable Integer id, @RequestParam String updatedBy) {
        appointmentService.cancelAppointment(id, updatedBy);
        return ResponseEntity.ok("Appointment cancelled successfully");
    }

    @GetMapping("/timeslots")
    public ResponseEntity<List<TimeSlot>> getTimeSlots() {
        return ResponseEntity.ok(appointmentService.getTimeSlots());
    }

}
