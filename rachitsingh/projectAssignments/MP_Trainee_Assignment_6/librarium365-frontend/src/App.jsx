import React from "react";
import { BrowserRouter, Routes, Route} from "react-router-dom";
import BooksPage from "./pages/BooksPage";
import MembersPage from "./pages/MembersPage";
import IssueBookPage from "./pages/IssueBookPage";
import ReportsPage from "./pages/ReportsPage";
import DashboardPage from "./pages/DashboardPage";
const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path = "/admin" element = {<DashboardPage/>}/>
        <Route path="/books/manage" element={<BooksPage />} />
        <Route path="/members/manage" element={<MembersPage/>}/>
        <Route path="/issueBook/manage" element={<IssueBookPage/>}/>
        <Route path="/reports/manage" element = {<ReportsPage/>}/>
      </Routes>
    </BrowserRouter>
  );
};

export default App;
