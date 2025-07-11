import React from "react";
import { Link } from "react-router-dom";
function Sidebar() {
  return (
    <div className="w-64 bg-white shadow-md h-full">
      <div className="p-6 text-2xl font-bold text-blue-600 border-b">
        ByteBudget
        <nav className="p-4 space-y-4">
          <Link
            to="/"
            className="block text-gray-700 hover:text-blue-600 font-medium"
          >
            Dashboard
          </Link>
          <Link
            to="/analytics"
            className="block text-gray-700 hover:text-blue-600 font-medium"
          >
            Analytics
          </Link>
          <Link
            to="/settings"
            className="block text-gray-700 hover:text-blue-600 font-medium"
          >
            Settings
          </Link>
        </nav>
      </div>
    </div>
  );
}

export default Sidebar;
