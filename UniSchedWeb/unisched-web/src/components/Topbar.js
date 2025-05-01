import React from 'react';
import logo from '../assets/unisched-logo.png';

const Topbar = ({ userDetails }) => {
  const handleLogout = () => {
    localStorage.clear();
    window.location.href = '/';
  };

  const displayName = userDetails?.firstName
    ? `${userDetails.firstName} ${userDetails.lastName}`
    : userDetails?.departmentName || 'Loading...';

  return (
<div className="d-flex justify-content-between align-items-center px-4"
     style={{ height: '56px', backgroundColor: '#ffffff' }}>
  <div className="d-flex align-items-center" style={{ height: '100%' }}>
  <img src={logo} alt="UniSched" style={{ height: '140px', marginTop: '10px', marginBottom: '10px' }} />
  </div>

  <div className="d-flex align-items-center">
    <span className="me-3 text-muted">{displayName}</span>
    <button className="btn btn-outline-danger btn-sm" onClick={handleLogout}>Logout</button>
  </div>
</div>

  );
};

export default Topbar;
