package com.project.unischedapi.appointment.service;

import com.project.unischedapi.appointment.model.Appointment;
import com.project.unischedapi.appointment.model.TimeSlot;
import com.project.unischedapi.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public int create(Appointment request) {
        Appointment appointment = new Appointment();
        appointment.setStudentId(request.getStudentId());
        appointment.setDepartmentId(request.getDepartmentId());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setTimeSlotId(request.getTimeSlotId());
        appointment.setNote(request.getNote());
        appointment.setLastUpdatedBy(request.getLastUpdatedBy());

        return appointmentRepository.create(appointment);
    }

    public List<Appointment> getByStudentId(Integer studentId) {
        return appointmentRepository.findByStudentId(studentId);
    }

    public List<Appointment> getByDepartmentId(Integer departmentId) {
        return appointmentRepository.findByDepartmentId(departmentId);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.update(appointment);
    }

    public void clearAppointmentHistory(Integer departmentId) {
        appointmentRepository.deleteAppointmentHistoryByDepartmentId(departmentId);
    }

    public void cancelAppointment(Integer appointmentId, String updatedBy) {
        appointmentRepository.cancel(appointmentId, updatedBy);
    }

    public List<TimeSlot> getTimeSlots() {
        return appointmentRepository.getTimeSlots();
    }
}

