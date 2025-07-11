import { useState } from "react";
function ExpenseForm({ onAddExpense }) {
  const [formData, setFormData] = useState({
    title: "",
    amount: "",
    category: "",
    date: "",
  });

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (
      !formData.title ||
      !formData.amount ||
      !formData.category ||
      !formData.date
    )
      return;
    onAddExpense(formData);
    setFormData({ title: "", amount: "", category: "", date: "" });
  };
  return (
    <div>
      <form
        action=""
        onSubmit={handleSubmit}
        className="bg-gray-700 p-4 rounded-lg shadow-md mb-6"
      >
        <h3 className="text-lg font-semibold mb-4 text-gray-300">
          Add New Expense
        </h3>
        <div className="grid grid-cols-1 text-gray-300 md:grid-cols-2 gap-4">
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
            placeholder="Expense Title"
            className="border border-gray-300 rounded px-3 py-2 text-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
          <select
            name="category"
            value={formData.category}
            onChange={handleChange}
            className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          >
            <option
              value=""
              className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              Select a Category
            </option>
            <option
              value="Food & Dining"
              className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              Food & Dining
            </option>
            <option
              value="Travel"
              className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              Travel
            </option>
            <option
              value="Shopping"
              className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              Shopping
            </option>
            <option
              value="Utilities & Bills"
              className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              Utilities & Bills
            </option>
            <option
              value="Entertainment"
              className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              Entertainment
            </option>
            <option
              value="Healthcare"
              className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              Healthcare
            </option>
            <option
              value="Other"
              className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              Other
            </option>
          </select>
          <input
            type="number"
            name="amount"
            value={formData.amount}
            onChange={handleChange}
            placeholder="Amount (₹)"
            className="border border-gray-300  text-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
          <input
            type="date"
            name="date"
            value={formData.date}
            onChange={handleChange}
            className="border border-gray-300  text-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <button
          type="submit"
          className="mt-4 bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-2 rounded-lg transition"
        >
          Add Expense
        </button>
      </form>
    </div>
  );
}

export default ExpenseForm;
