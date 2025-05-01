import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';
import ConfirmModal from '../../components/ConfirmModal';

const DepartmentAppointments = () => {
  const [appointments, setAppointments] = useState([]);
  const [confirmModal, setConfirmModal] = useState({
    show: false,
    title: '',
    message: ''
  });
  const confirmCallbackRef = useRef(() => { }); // for generic confirmation
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState('');
  const [toastType, setToastType] = useState('success');

  useEffect(() => {
    const storedDetails = JSON.parse(localStorage.getItem("userDetails"));
    const departmentId = storedDetails?.departmentId;

    if (departmentId) {
      axios.get(`http://localhost:8084/api/appointment/department/${departmentId}`)
        .then(res => setAppointments(res.data))
        .catch(err => console.error(err));
    }
  }, []);


  const getStatusText = (code) => {
    switch (code) {
      case 'P': return 'Upcoming';
      case 'D': return 'Done';
      case 'C': return 'Cancelled';
      default: return 'Unknown';
    }
  };

  const handleStatusChange = (appointmentId, newStatus) => {
    const apptToUpdate = appointments.find(appt => appt.appointmentId === appointmentId);
    if (!apptToUpdate) return;

    confirmCallbackRef.current = async () => {
      const updatedAppointment = {
        ...apptToUpdate,
        appointmentStatus: newStatus
      };

      try {
        await axios.put(`http://localhost:8084/api/appointment/update/${appointmentId}`, updatedAppointment);

        setAppointments(prev =>
          prev.map(appt =>
            appt.appointmentId === appointmentId ? { ...appt, appointmentStatus: newStatus } : appt
          )
        );
        setToastMessage("Status updated successfully");
        setToastType("success");
      } catch (err) {
        console.error("Status update failed", err);
        setToastMessage("Failed to update status");
        setToastType("danger");
      } finally {
        setShowToast(true);
        setConfirmModal(prev => ({ ...prev, show: false }));
      }
    };

    setConfirmModal({
      show: true,
      title: 'Confirm Status Change',
      message: `Are you sure you want to mark this appointment as ${getStatusText(newStatus)}?`
    });
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

      {/* Confirm Modal */}
      <ConfirmModal
        show={confirmModal.show}
        title={confirmModal.title}
        message={confirmModal.message}
        onConfirm={() => confirmCallbackRef.current()}
        onCancel={() => setConfirmModal(prev => ({ ...prev, show: false }))}
      />
      <div className="p-3">
        <>
          <h3 className="mb-4">Appointments</h3>
        </>

        <table className="table table-striped">
          <thead className="table-dark">
            <tr>
              <th>Student Name</th>
              <th>Date</th>
              <th>Time</th>
              <th>Note</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {appointments
              .filter(appt => appt.appointmentStatus === 'P')
              .map((appt) => (

                <tr key={appt.appointmentId}>
                  <td>{appt.studentName}</td>
                  <td>{new Date(appt.appointmentDate).toLocaleDateString()}</td>
                  <td>{appt.timeSlot}</td>
                  <td>{appt.note}</td>
                  <td>
                    {appt.appointmentStatus === 'P' ? (
                      <select
                        className="form-select form-select-sm"
                        value={appt.appointmentStatus}
                        onChange={(e) => handleStatusChange(appt.appointmentId, e.target.value)}
                      >
                        <option value="P">Upcoming</option>
                        <option value="D">Done</option>
                        <option value="C">Cancelled</option>
                      </select>
                    ) : (
                      <span className={`badge ${appt.appointmentStatus === 'D' ? 'bg-success' : 'bg-secondary'
                        }`}>
                        {getStatusText(appt.appointmentStatus)}
                      </span>
                    )}
                  </td>

                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default DepartmentAppointments;
