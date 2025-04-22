package com.project.unischedapi.appointment.repository;

import com.project.unischedapi.appointment.model.Appointment;
import com.project.unischedapi.appointment.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppointmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int create(Appointment appointment) {
        String sql = """
            INSERT INTO Appointment (StudentID, DepartmentID, AppointmentDate, TimeSlotID, Note, AppointmentStatus, CreatedAt, LastUpdated, LastUpdatedBy)
            VALUES (?, ?, ?, ?, ?, 'P', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?)
        """;
        jdbcTemplate.update(sql,
                appointment.getStudentId(),
                appointment.getDepartmentId(),
                appointment.getAppointmentDate(),
                appointment.getTimeSlotId(),
                appointment.getNote(),
                appointment.getLastUpdatedBy());

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public List<Appointment> findByStudentId(Integer studentId) {
        String sql = "SELECT a.*, d.DepartmentName, t.TimeSlot " +
                "FROM Appointment a " +
                "JOIN Department d ON a.DepartmentID = d.DepartmentID " +
                "JOIN TimeSlot t ON a.TimeSlotID = t.TimeSlotID " +
                "WHERE a.StudentID = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Appointment.class), studentId);
    }

    public List<Appointment> findByDepartmentId(Integer departmentId) {
        String sql = "SELECT a.*, CONCAT(s.LastName, ', ', s.FirstName) AS studentName, t.TimeSlot " +
                "FROM Appointment a " +
                "JOIN Department d ON a.DepartmentID = d.DepartmentID " +
                "JOIN Student s ON a.StudentID = s.StudentID " +
                "JOIN TimeSlot t ON a.TimeSlotID = t.TimeSlotID " +
                "WHERE a.StudentID = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Appointment.class), departmentId);
    }

    public void update(Appointment appointment) {
        String sql = """
            UPDATE Appointment
            SET AppointmentDate = ?, TimeSlotID = ?, Note = ?, LastUpdated = CURRENT_TIMESTAMP, LastUpdatedBy = ?
            WHERE AppointmentID = ?
        """;
        jdbcTemplate.update(sql,
                appointment.getAppointmentDate(),
                appointment.getTimeSlotId(),
                appointment.getNote(),
                appointment.getLastUpdatedBy(),
                appointment.getAppointmentId());
    }

    public void cancel(Integer appointmentId, String updatedBy) {
        String sql = """
            UPDATE Appointment
            SET AppointmentStatus = 'C', LastUpdated = CURRENT_TIMESTAMP, LastUpdatedBy = ?
            WHERE AppointmentID = ?
        """;
        jdbcTemplate.update(sql, updatedBy, appointmentId);
    }

    public List<TimeSlot> getTimeSlots() {
        String sql = "SELECT * FROM TimeSlot ORDER BY TimeSlotID";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TimeSlot.class));
    }

}

