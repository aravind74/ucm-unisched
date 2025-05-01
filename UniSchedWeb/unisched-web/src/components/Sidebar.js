import React from 'react';
import { Link } from 'react-router-dom';
import { FaCalendarAlt, FaPlusCircle, FaHistory, FaBars } from 'react-icons/fa';

const Sidebar = ({ isOpen, setSidebarOpen }) => {
  const role = localStorage.getItem('role'); // "STUDENT" or "DEPARTMENT"

const menuItems = [
  {
    path: role === "STUDENT" ? "/student/dashboard" : "/department/dashboard",
    icon: <FaCalendarAlt />,
    label: "Appointments"
  },
  ...(role === "STUDENT"
    ? [{ path: "/student/book", icon: <FaPlusCircle />, label: "Book Appointment" }]
    : []),
  ...(role === "DEPARTMENT"
    ? [{
        path: "/department/history",
        icon: <FaHistory />,
        label: "History"
      }]
    : [])
];

  return (
    <div
      style={{
        width: isOpen ? '220px' : '70px',
        backgroundColor: '#333C4D',
        color: '#fff',
        height: '100vh',
        transition: 'width 0.3s',
        position: 'fixed',
        zIndex: 999,
        overflow: 'hidden'
      }}
      className="d-flex flex-column pt-3"
    >
      <div className="px-3 mb-4">
        <button
          onClick={() => setSidebarOpen(!isOpen)}
          className="btn btn-sm btn-outline-light"
        >
          <FaBars />
        </button>
      </div>

      <nav className="nav flex-column w-100">
        {menuItems.map((item, index) => (
          <Link
            key={index}
            to={item.path}
            className="nav-link text-white d-flex align-items-center px-3 py-2"
            style={{ fontSize: '15px' }}
          >
            <span style={{ fontSize: '18px', marginRight: isOpen ? '12px' : '0', display: 'inline-block', width: '24px', textAlign: 'center' }}>
              {item.icon}
            </span>
            {isOpen && <span>{item.label}</span>}
          </Link>
        ))}
      </nav>
    </div>
  );
};

export default Sidebar;
