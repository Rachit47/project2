// src/pages/members/MemberList.jsx
import React from "react";

const MemberList = ({ members, onEdit, onDelete }) => {
  return (
    <div className="overflow-x-auto">
      <table className="w-full text-left table-auto bg-gray-800 rounded">
        <thead>
          <tr className="bg-gray-600 text-white">
            <th className="p-2">ID</th>
            <th className="p-2">Name</th>
            <th className="p-2">Email</th>
            <th className="p-2">Mobile</th>
            <th className="p-2">Gender</th>
            <th className="p-2">Address</th>
            <th className="p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {members.length === 0 ? (
            <tr>
              <td colSpan="7" className="p-4 text-center text-gray-300">
                No members found
              </td>
            </tr>
          ) : (
            members.map((m) => (
              <tr key={m.memberId} className="border-t border-gray-700 hover:bg-gray-700">
                <td className="p-2">{m.memberId}</td>
                <td className="p-2">{m.name}</td>
                <td className="p-2">{m.email}</td>
                <td className="p-2">{m.mobile}</td>
                <td className="p-2">{m.gender}</td>
                <td className="p-2">{m.address}</td>
                <td className="p-2 flex gap-2">
                  <button onClick={() => onEdit(m)} className="text-red-500">
                    Edit
                  </button>
                  <button onClick={() => onDelete(m.memberId)} className="text-blue-500">
                    Delete
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default MemberList;
