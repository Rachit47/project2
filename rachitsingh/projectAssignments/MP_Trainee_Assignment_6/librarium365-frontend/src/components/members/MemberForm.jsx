// src/pages/members/MemberForm.jsx
import React, { useState, useEffect } from "react";

const MemberForm = ({ onSubmit, selectedMember, clearSelection }) => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    mobile: "",
    gender: "",
    address: "",
  });

  useEffect(() => {
    if (selectedMember) {
      setFormData(selectedMember);
    } else {
      setFormData({
        name: "",
        email: "",
        mobile: "",
        gender: "",
        address: "",
      });
    }
  }, [selectedMember]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((f) => ({ ...f, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.name || !formData.email || !formData.mobile || !formData.gender || !formData.address) return;
    onSubmit(formData);
    setFormData({
      name: "",
      email: "",
      mobile: "",
      gender: "",
      address: "",
    });
  };

  return (
    <form onSubmit={handleSubmit} className="mb-6 space-y-4 rounded-md p-4">
      <div className="bg-gray-800 p-4 rounded-md grid grid-cols-2 gap-4">
        <input name="name" value={formData.name} onChange={handleChange} placeholder="Name" className="p-2 rounded border bg-gray-800 text-white" />
        <input name="email" value={formData.email} onChange={handleChange} placeholder="Email" className="p-2 rounded border bg-gray-800 text-white" />
        <input name="mobile" value={formData.mobile} onChange={handleChange} placeholder="Mobile" type="number" className="p-2 border rounded bg-gray-800 text-white" />
        <input name="gender" value={formData.gender} onChange={handleChange} placeholder="Gender (M/F)" className="p-2 rounded  border bg-gray-800 text-white" />
        <input name="address" value={formData.address} onChange={handleChange} placeholder="Address" className="p-2 col-span-2 border rounded bg-gray-800 text-white" />
      </div>
      <div className="bg-gray-800 p-4 flex items-center gap-4">
        <button type="submit" className="px-4 py-2 bg-blue-600 rounded hover:bg-blue-700">
          {selectedMember ? "Update Member" : "Add Member"}
        </button>
        {selectedMember && (
          <button type="button" onClick={clearSelection} className="px-4 py-2 bg-gray-500 rounded hover:bg-gray-600">
            Cancel
          </button>
        )}
      </div>
    </form>
  );
};

export default MemberForm;
