import { Navigate, useLocation } from "react-router-dom";

export const AuthGuard = ({ children }) => {
  const location = useLocation();
  const token = localStorage.getItem("token");

  // Simple JWT decode (payload is second part)
  let role = null;
  if (token) {
    try {
      const payload = token.split(".")[1];
      const decoded = atob(payload);
      const parsed = JSON.parse(decoded);
      role = parsed.role || parsed.roles?.[0]; // Adjust based on your token structure
    } catch (e) {
      console.error("Failed to decode token", e);
    }
  }

  const isAuthenticated = !!token;
  const isAdmin = role === "ADMIN";

  if (!isAuthenticated || !isAdmin) {
    // Redirect to login or home, preserving the intended location
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return children;
};