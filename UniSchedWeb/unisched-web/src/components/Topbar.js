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
      <div className="d-flex justify-content-between align-items-center px-4 py-2 shadow-sm" style={{ backgroundColor: '#ffffff' }}>
        <div className="d-flex align-items-center">
          <img src={logo} alt="UniSched" style={{ height: '36px', marginRight: '12px' }} />
          <h5 className="m-0 text-dark">UniSched</h5>
        </div>
        <div className="d-flex align-items-center">
          <span className="me-3 text-muted">{displayName}</span>
          <button className="btn btn-outline-danger btn-sm" onClick={handleLogout}>Logout</button>
        </div>
      </div>
    );
  };
  

export default Topbar;
