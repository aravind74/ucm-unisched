import React, { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

const DashboardLayout = ({ children }) => {
  const [userDetails, setUserDetails] = useState(null);
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const userId = localStorage.getItem("userId");
  const role = localStorage.getItem("role");

  // Role-based redirection if wrong route is accessed
  useEffect(() => {
    if (role === 'DEPARTMENT' && location.pathname.includes('/student')) {
      navigate('/department/dashboard');
    }
    if (role === 'STUDENT' && location.pathname.includes('/department')) {
      navigate('/student/dashboard');
    }
  }, [location.pathname, role, navigate]);

  // Fetch user details
  useEffect(() => {
    if (userId && role) {
      const endpoint = role === 'STUDENT'
        ? `http://localhost:8082/api/student/${userId}`
        : `http://localhost:8083/api/department/${userId}`;

      axios.get(endpoint)
        .then(res => {
          localStorage.setItem("userDetails", JSON.stringify(res.data));
          setUserDetails(res.data);
        })
        .catch(err => console.error("Failed to fetch user details", err));
    }
  }, [userId, role]);

  return (
    <div className="d-flex" style={{ minHeight: '100vh', backgroundColor: '#f7f7f7' }}>
      <Sidebar isOpen={sidebarOpen} setSidebarOpen={setSidebarOpen} />
      <div className="flex-grow-1" style={{ marginLeft: sidebarOpen ? 220 : 70, transition: 'margin 0.3s' }}>
        <Topbar toggleSidebar={() => setSidebarOpen(!sidebarOpen)} userDetails={userDetails} />
        <div className="p-4">
          {children}
        </div>
      </div>
    </div>
  );
};

export default DashboardLayout;
