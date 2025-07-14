import React from "react";
import { BrowserRouter as Router } from "react-router";
import EcomRoutes from "./EcomRoutes";
import { AuthProvider } from "./context/AuthContext";

const App = () => {
  return (
    <Router>
      <AuthProvider>
        <EcomRoutes />
      </AuthProvider>
    </Router>
  );
};

export default App;
