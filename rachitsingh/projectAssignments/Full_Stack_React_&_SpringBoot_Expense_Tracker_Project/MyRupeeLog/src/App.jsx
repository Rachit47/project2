import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Sidebar from "./components/Sidebar";
import Header from "./components/Header";
import Dashboard from "./pages/Dashboard";
function App() {
  return (
    <Router>
      <div className="flex h-screen bg-gray-800">
        {/* <Sidebar /> */}
        <div className="flex flex-col flex-1">
          <Header />
          <main className="flex-1 p-4 overflow-auto">
            <Routes>
              <Route path="/" element={<Dashboard />} />
              {/* More pages in progress....*/}
            </Routes>
          </main>
        </div>
      </div>
    </Router>
  );
}

export default App;
