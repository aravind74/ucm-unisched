import React, { useEffect, useState } from 'react';
import axios from 'axios';

const DepartmentAppointments = () => {
  const [appointments, setAppointments] = useState([]);

  useEffect(() => {
    const departmentId = localStorage.getItem('departmentId'); // or from context
    axios.get(`http://localhost:8080/api/appointment/department/${departmentId}`)
      .then(res => setAppointments(res.data))
      .catch(err => console.error(err));
  }, []);

  const getStatusText = (code) => {
    switch (code) {
      case 'P':
        return 'Pending';
      case 'D':
        return 'Done';
      case 'C':
        return 'Cancelled';
      default:
        return 'Unknown';
    }
  };

  return (
    <table className="table table-striped">
      <thead className="table-dark">
        <tr>
          <th>Student Name</th>
          <th>Date</th>
          <th>Time</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {appointments.map((appt) => (
          <tr key={appt.appointmentId}>
            <td>{appt.lastName + ', '}{appt.firstName}</td>
            <td>{new Date(appt.appointmentDate).toLocaleDateString()}</td>
            <td>{appt.timeSlot}</td>
            <td>{getStatusText(appt.appointmentStatus)}</td>
            <td>
              <button className="btn btn-sm btn-warning me-2">Edit</button>
              <button className="btn btn-sm btn-danger">Cancel</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default DepartmentAppointments;
