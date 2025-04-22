import React from 'react';
import DashboardLayout from '../../components/DashboardLayout';
import DepartmentAppointments from './DepartmentAppointments';

const DepartmentDashboard = () => {
  return (
    <DashboardLayout>
      <h3 className="mb-4">Appointments</h3>
      <DepartmentAppointments />
    </DashboardLayout>
  );
};

export default DepartmentDashboard;
