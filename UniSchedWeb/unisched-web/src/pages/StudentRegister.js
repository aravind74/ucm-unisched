import React, { useState } from 'react';
import axios from 'axios';
import logo from '../assets/unisched-logo.png'; // Use your logo here

const StudentRegister = () => {
  const [formData, setFormData] = useState({
    emailId: '',
    password: '',
    confirmPassword: '',
    firstName: '',
    lastName: '',
    lastUpdatedBy: 'system'
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      setError("Passwords do not match.");
      setTimeout(() => setError(''), 3000);
      return;
    }

    try {
      await axios.post('http://localhost:8080/api/student/register', formData);
      setSuccess("Student registered successfully!");
      setTimeout(() => setSuccess(''), 3000);
      setFormData({ emailId: '', password: '', confirmPassword: '', firstName: '', lastName: '', lastUpdatedBy: 'system' });
    } catch (err) {
      setError(err.response?.data || "Something went wrong.");
      setTimeout(() => setError(''), 3000);
    }
  };

  return (
    <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: '100vh', backgroundColor: '#f7f7f7' }}>

      <div className="auth-card w-100" style={{ maxWidth: 400 }}>
        <h3 className="auth-title">Student Sign Up</h3>

        {error && <div className="alert alert-danger">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Email ID</label>
            <input type="email" name="emailId" className="form-control" value={formData.emailId} onChange={handleChange} required />
          </div>

          <div className="mb-3">
            <label className="form-label">Password</label>
            <input type="password" name="password" className="form-control" value={formData.password} onChange={handleChange} required />
          </div>

          <div className="mb-3">
            <label className="form-label">Confirm Password</label>
            <input type="password" name="confirmPassword" className="form-control" value={formData.confirmPassword} onChange={handleChange} required />
          </div>

          <div className="mb-3">
            <label className="form-label">First Name</label>
            <input type="text" name="firstName" className="form-control" value={formData.firstName} onChange={handleChange} required />
          </div>

          <div className="mb-3">
            <label className="form-label">Last Name</label>
            <input type="text" name="lastName" className="form-control" value={formData.lastName} onChange={handleChange} required />
          </div>

          <button type="submit" className="btn btn-primary w-100 mt-2">Register</button>
        </form>
      </div>
    </div>
  );
};

export default StudentRegister;
