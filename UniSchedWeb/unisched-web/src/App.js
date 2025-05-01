import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Login from './pages/Login';
import StudentRegister from './pages/StudentRegister';
import DepartmentRegister from './pages/DepartmentRegister';

import DashboardLayout from './components/DashboardLayout';
import StudentDashboard from './pages/student/StudentDashboard';
import BookAppointment from './pages/student/BookAppointment';
import DepartmentDashboard from './pages/department/DepartmentDashboard';
import DepartmentHistory from './pages/department/DepartmentHistory';

function App() {
  return (
    <Router>
      <Routes>
        {/* Public routes */}
        <Route path="/" element={<Login />} />
        <Route path="/student/register" element={<StudentRegister />} />
        <Route path="/department/register" element={<DepartmentRegister />} />

        {/* Student routes with layout */}
        <Route
          path="/student/dashboard"
          element={
            <DashboardLayout>
              <StudentDashboard />
            </DashboardLayout>
          }
        />
        <Route
          path="/student/book"
          element={
            <DashboardLayout>
              <BookAppointment />
            </DashboardLayout>
          }
        />

        {/* Department dashboard */}
        <Route
          path="/department/dashboard"
          element={
            <DashboardLayout>
              <DepartmentDashboard />
            </DashboardLayout>
          }
        />

        <Route
          path="/department/history"
          element={
            <DashboardLayout>
              <DepartmentHistory />
            </DashboardLayout>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
