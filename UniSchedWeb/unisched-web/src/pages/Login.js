import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/unisched-logo.png';

const Login = () => {
  const [formData, setFormData] = useState({ emailId: '', password: '' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8081/api/user/login', formData);
      const { userId, roleName, emailId, token } = response.data;
  
      localStorage.setItem('token', token);
      localStorage.setItem('userId', userId);
      localStorage.setItem('emailId', emailId);
      localStorage.setItem('role', roleName);
  
      // ✅ Fetch full userDetails and store
      const userDetailsUrl = roleName === 'STUDENT'
        ? `http://localhost:8082/api/student/${userId}`
        : `http://localhost:8083/api/department/${userId}`;
  
      const userDetailsRes = await axios.get(userDetailsUrl);
      localStorage.setItem("userDetails", JSON.stringify(userDetailsRes.data));
  
      // ✅ Now navigate after data is available
      navigate(roleName === 'STUDENT' ? '/student/dashboard' : '/department/dashboard');
  
    } catch (err) {
      setError('Invalid email or password');
      setTimeout(() => setError(''), 3000);
    }
  };
  

  return (
    <div className="d-flex justify-content-center align-items-center" style={{ height: '100vh', backgroundColor: '#f7f7f7' }}>
      <div className="auth-card text-center w-100" style={{ maxWidth: 400 }}>
      <img src={logo} alt="UniSched" style={{ height: '140px', marginBottom: '20px' }} />


        {error && <div className="alert alert-danger">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="mb-3 text-start">
            <label className="form-label">Email ID</label>
            <input
              type="email"
              name="emailId"
              className="form-control"
              value={formData.emailId}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3 text-start">
            <label className="form-label">Password</label>
            <input
              type="password"
              name="password"
              className="form-control"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" className="btn btn-primary w-100 mt-2">Login</button>
        </form>

        <div className="mt-4">
          <small className="text-muted">Don't have an account?</small>
          <div className="d-flex justify-content-between mt-2">
            <a href="/student/register" className="btn btn-outline-secondary btn-sm w-50 me-1">Student Signup</a>
            <a href="/department/register" className="btn btn-outline-secondary btn-sm w-50 ms-1">Department Signup</a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
