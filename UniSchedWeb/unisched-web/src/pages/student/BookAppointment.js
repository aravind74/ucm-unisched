import React, { useState, useEffect } from 'react';
import axios from 'axios';

const BookAppointment = () => {
  const [departments, setDepartments] = useState([]);
  const [timeSlots, setTimeSlots] = useState([]);
  const [formData, setFormData] = useState({
    departmentId: '',
    appointmentDate: '',
    timeSlotId: '',
    note: ''
  });
  const [success, setSuccess] = useState('');
  const studentId = JSON.parse(localStorage.getItem("userDetails"))?.studentId;

  useEffect(() => {
    axios.get('http://localhost:8080/api/department/getDepartmentList')
      .then(res => setDepartments(res.data))
      .catch(err => console.error(err));

    axios.get('http://localhost:8080/api/appointment/timeslots')
      .then(res => setTimeSlots(res.data))
      .catch(err => console.error(err));
  }, []);

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post('http://localhost:8080/api/appointment/book', {
      ...formData,
      studentId: studentId
    })
    .then(res => {
      setSuccess('Appointment booked successfully!');
      setFormData({ departmentId: '', appointmentDate: '', timeSlotId: '', note: '' });
      setTimeout(() => setSuccess(''), 3000);
    })
    .catch(err => console.error(err));
  };

  return (
    <div className="p-4">
      <h3 className="mb-4 text-dark">Book Appointment</h3>

      {success && <div className="alert alert-success">{success}</div>}

      <form onSubmit={handleSubmit} className="row g-3" style={{ maxWidth: '1000px' }}>
        <div className="col-md-4">
          <label className="form-label">Department</label>
          <select name="departmentId" className="form-select" value={formData.departmentId} onChange={handleChange} required>
            <option value="">Select Department</option>
            {departments.map(dept => (
              <option key={dept.departmentId} value={dept.departmentId}>{dept.departmentName}</option>
            ))}
          </select>
        </div>

        <div className="col-md-3">
          <label className="form-label">Appointment Date</label>
          <input type="date" name="appointmentDate" className="form-control" value={formData.appointmentDate} onChange={handleChange} required />
        </div>

        <div className="col-md-2">
          <label className="form-label">Time Slot</label>
          <select name="timeSlotId" className="form-select" value={formData.timeSlotId} onChange={handleChange} required>
            <option value="">Select</option>
            {timeSlots.map(slot => (
              <option key={slot.timeSlotId} value={slot.timeSlotId}>{slot.timeSlot}</option>
            ))}
          </select>
        </div>

        <div className="col-12">
          <label className="form-label">Note</label>
          <textarea name="note" className="form-control" value={formData.note} onChange={handleChange} rows="3" />
        </div>

        <div className="col-12">
          <button type="submit" className="btn btn-primary">Book Appointment</button>
        </div>
      </form>
    </div>
  );
};

export default BookAppointment;
