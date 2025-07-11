import React from 'react';
import { useNavigate } from 'react-router-dom';
const DashboardPage = () =>{
    const navigate = useNavigate();
    const modules = [
        { title: "📚 Manage Books", path: "/books/manage" },
        { title: "👥 Manage Members", path: "/members/manage" },
        { title: "📖 Issue/Return Books", path: "/issueBook/manage" },
        { title: "📊 Reports", path: "/reports/manage" }
    ]

    return (
        <div className='min-h-screen bg-gray-900 text-white flex flex-col items-center p-6'>
            <h1 className="text-3xl font-bold mb-8">Librarium365 - Control Room</h1>
            <div className='grid grid-cols-1 sm:grid-cols-2 gap-6 w-full max-w-3xl'>
                {modules.map((module,index) => (
                    <div key = {index} className="bg-gray-700 p-6 rounded-lg shadow hover:bg-gray-600 cursor-pointer transition-all" onClick={() => navigate(module.path)}>
                        <h2 className="text-xl font-semibold text-center">{module.title}</h2>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default DashboardPage