package com.project.unischedapi.appointment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
public class Appointment {
    private Integer appointmentId;
    private Integer studentId;
    private String studentName;
    private Integer departmentId;
    private String departmentName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
    private Integer timeSlotId;
    private String timeSlot;
    private String note;
    private String appointmentStatus;
    private Timestamp createdAt;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
}
