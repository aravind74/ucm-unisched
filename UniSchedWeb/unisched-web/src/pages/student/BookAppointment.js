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
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState('');
  const [toastType, setToastType] = useState('success');

  useEffect(() => {
    axios.get('http://localhost:8083/api/department/getDepartmentList')
      .then(res => setDepartments(res.data))
      .catch(err => console.error(err));

    axios.get('http://localhost:8084/api/appointment/timeslots')
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
    axios.post('http://localhost:8084/api/appointment/book', {
      ...formData,
      studentId: studentId
    })
      .then(() => {
        setToastMessage("Appointment booked successfully!");
        setToastType("success");
        setShowToast(true);
        setFormData({ departmentId: '', appointmentDate: '', timeSlotId: '', note: '' });
      })
      .catch(err => console.error(err));
  };

  return (
    <>
      {/* Toast */}
      <div className="toast-container position-fixed bottom-0 end-0 p-3" style={{ zIndex: 1055 }}>
        <div className={`toast align-items-center text-bg-${toastType} border-0 ${showToast ? 'show' : ''}`} role="alert" aria-live="assertive" aria-atomic="true">
          <div className="d-flex">
            <div className="toast-body">{toastMessage}</div>
            <button type="button" className="btn-close btn-close-white me-2 m-auto" onClick={() => setShowToast(false)}></button>
          </div>
        </div>
      </div>
      <div className="container py-4">
        <div className="bg-white p-4 rounded shadow-sm">
          <h3 className="mb-4 text-dark">Book Appointment</h3>

          {success && <div className="alert alert-success">{success}</div>}

          <form onSubmit={handleSubmit}>
            <div className="row g-3">
              <div className="col-md-6 col-lg-4">
                <label className="form-label">Department</label>
                <select
                  name="departmentId"
                  className="form-select"
                  value={formData.departmentId}
                  onChange={handleChange}
                  required
                >
                  <option value="">Select Department</option>
                  {departments.map(dept => (
                    <option key={dept.departmentId} value={dept.departmentId}>
                      {dept.departmentName}
                    </option>
                  ))}
                </select>
              </div>

              <div className="col-md-6 col-lg-4">
                <label className="form-label">Appointment Date</label>
                <input
                  type="date"
                  name="appointmentDate"
                  className="form-control"
                  value={formData.appointmentDate}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="col-md-6 col-lg-4">
                <label className="form-label">Time Slot</label>
                <select
                  name="timeSlotId"
                  className="form-select"
                  value={formData.timeSlotId}
                  onChange={handleChange}
                  required
                >
                  <option value="">Select</option>
                  {timeSlots.map(slot => (
                    <option key={slot.timeSlotId} value={slot.timeSlotId}>
                      {slot.timeSlot}
                    </option>
                  ))}
                </select>
              </div>

              <div className="col-12">
                <label className="form-label">Note</label>
                <textarea
                  name="note"
                  className="form-control"
                  value={formData.note}
                  onChange={handleChange}
                  rows="3"
                />
              </div>

              <div className="col-12 text-end">
                <button type="submit" className="btn btn-primary">Book Appointment</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};

export default BookAppointment;
