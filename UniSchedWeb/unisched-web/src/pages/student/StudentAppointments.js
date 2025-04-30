import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';
import ConfirmModal from '../../components/ConfirmModal';
import { FaEdit, FaTrashAlt } from 'react-icons/fa';

const StudentAppointments = () => {
  const [appointments, setAppointments] = useState([]);
  const userDetails = JSON.parse(localStorage.getItem("userDetails")) || {};
  const studentId = userDetails?.studentId;
  const [showModal, setShowModal] = useState(false);
  const [selectedAppointment, setSelectedAppointment] = useState(null);
  const [timeSlots, setTimeSlots] = useState([]);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState('');
  const [toastType, setToastType] = useState('success');
  const [editData, setEditData] = useState({
    appointmentDate: '',
    timeSlotId: ''
  });
  const [confirmModal, setConfirmModal] = useState({
    show: false,
    title: '',
    message: ''
  });

  const confirmCallbackRef = useRef(() => {}); // for generic confirmation

  const fetchAppointments = () => {
    axios
      .get(`http://localhost:8084/api/appointment/student/${studentId}`)
      .then((res) => {
        const adjustedAppointments = res.data.map(appt => ({
          ...appt,
          appointmentDate: appt.appointmentDate.split('T')[0]
        }));
        setAppointments(adjustedAppointments);
      })
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchAppointments();
    axios
      .get('http://localhost:8084/api/appointment/timeslots')
      .then(res => setTimeSlots(res.data))
      .catch(err => console.error(err));
  }, []);

  const getStatusText = (code) => {
    switch (code) {
      case 'P': return 'Pending';
      case 'D': return 'Done';
      case 'C': return 'Cancelled';
      default: return 'Unknown';
    }
  };

  const handleEdit = (appointment) => {
    let dateString = appointment.appointmentDate;
    if (dateString.includes('T')) {
      dateString = dateString.split('T')[0];
    }
    setSelectedAppointment(appointment);
    setEditData({
      appointmentDate: dateString,
      timeSlotId: appointment.timeSlotId
    });
    setShowModal(true);
  };

  const handleCancel = (appointmentId) => {
    confirmCallbackRef.current = async () => {
      try {
        const updatedBy = "system";
        await axios.put(`http://localhost:8084/api/appointment/cancel/${appointmentId}?updatedBy=${updatedBy}`);        
        setToastMessage("Appointment cancelled successfully!");
        setToastType("sucesss");
        setShowToast(true);
        fetchAppointments();
      } catch (err) {
        console.error("Cancel failed", err);
      } finally {
        setConfirmModal(prev => ({ ...prev, show: false }));
      }
    };

    setConfirmModal({
      show: true,
      title: 'Cancel Appointment',
      message: 'Are you sure you want to cancel this appointment?'
    });
  };

  const handleChange = (e) => {
    setEditData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8084/api/appointment/update/${selectedAppointment.appointmentId}`, {
        appointmentDate: editData.appointmentDate,
        timeSlotId: editData.timeSlotId
      });
      setToastMessage("Appointment updated successfully!");
      setToastType("success");
      setShowToast(true);
      setTimeout(() => setShowToast(false), 3000);
      closeModal();
      fetchAppointments();
    } catch (err) {
      console.error("Update failed", err);
    }
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedAppointment(null);
  };

  const sortedAppointments = [...appointments].sort((a, b) => {
    const statusOrder = { 'P': 1, 'D': 2, 'C': 3 };
  
    const statusCompare = statusOrder[a.appointmentStatus] - statusOrder[b.appointmentStatus];
    if (statusCompare !== 0) return statusCompare;
  
    const toDateTime = (appt) => {
      const date = new Date(appt.appointmentDate);
      const startTime = appt.timeSlot.split(' - ')[0]; // e.g., "8:00 AM"
  
      // Convert "8:00 AM" to 24-hr time and set it on the date
      const [time, modifier] = startTime.split(' ');
      let [hours, minutes] = time.split(':').map(Number);
  
      if (modifier === 'PM' && hours !== 12) hours += 12;
      if (modifier === 'AM' && hours === 12) hours = 0;
  
      date.setHours(hours, minutes, 0, 0);
      return date;
    };
  
    return toDateTime(a) - toDateTime(b); // Ascending: earliest first
  });
   

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

      {/* Confirm Modal */}
      <ConfirmModal
        show={confirmModal.show}
        title={confirmModal.title}
        message={confirmModal.message}
        onConfirm={() => confirmCallbackRef.current()}
        onCancel={() => setConfirmModal(prev => ({ ...prev, show: false }))}
      />

      {/* Edit Modal */}
      {showModal && selectedAppointment && (
        <div className="modal fade show d-block" tabIndex="-1" role="dialog">
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Edit Appointment</h5>
                <button type="button" className="btn-close" onClick={closeModal}></button>
              </div>
              <div className="modal-body">
                <form onSubmit={handleUpdate}>
                  <div className="mb-3">
                    <label className="form-label">Department</label>
                    <input type="text" className="form-control" value={selectedAppointment.departmentName} disabled />
                  </div>
                  <div className="mb-3">
                    <label className="form-label">Appointment Date</label>
                    <input
                      type="date"
                      name="appointmentDate"
                      className="form-control"
                      value={editData.appointmentDate}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="mb-3">
                    <label className="form-label">Time Slot</label>
                    <select
                      name="timeSlotId"
                      className="form-select"
                      value={editData.timeSlotId}
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
                  <div className="modal-footer">
                    <button type="submit" className="btn btn-success">Save</button>
                    <button type="button" className="btn btn-secondary" onClick={closeModal}>Cancel</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Appointment Table */}
      <table className="table table-striped mt-4">
        <thead className="table-dark">
          <tr>
            <th>Department</th>
            <th>Date</th>
            <th>Time</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {sortedAppointments.map((appt) => (
            <tr key={appt.appointmentId}>
              <td>{appt.departmentName}</td>
              <td>{appt.appointmentDate}</td>
              <td>{appt.timeSlot}</td>
              <td>
                <span className={`badge px-3 py-2 rounded-pill fw-semibold ${
                  appt.appointmentStatus === 'P' ? 'bg-primary-subtle text-primary' :
                  appt.appointmentStatus === 'D' ? 'bg-success text-white' :
                  appt.appointmentStatus === 'C' ? 'bg-dark text-light' : 'bg-light text-dark'
                }`}>
                  {getStatusText(appt.appointmentStatus)}
                </span>
              </td>
              <td>
                <button className="btn btn-sm btn-outline-secondary me-2" title="Edit Appointment" onClick={() => handleEdit(appt)}>
                  <FaEdit />
                </button>
                <button className="btn btn-sm btn-outline-danger" title="Cancel Appointment" onClick={() => handleCancel(appt.appointmentId)}>
                  <FaTrashAlt />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

    </>
  );
};

export default StudentAppointments;
