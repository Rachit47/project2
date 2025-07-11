import React, { useState, useEffect } from "react";
import ExpenseLineChart from "../components/charts/ExpenseLineChart";
import ExpenseForm from "../components/expense/ExpenseForm";
import ExpenseTable from "../components/expense/ExpenseTable";
import ExpensePieChart from "../components/charts/ExpensePieChart";

function Dashboard() {
  const [expenses, setExpenses] = useState([]);
  useEffect(() => {
    fetch("/api/expenses/all")
      .then((result) => result.json())
      .then((data) => setExpenses(data))
      .catch((error) => console.error("Error fetching expenses: ", error));
  }, []);

  const handleAddExpense = async (newExpense) => {
    try {
      const result = await fetch("/api/expenses/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newExpense),
      });

      if (!result.ok) throw new Error("Failed to add expense");

      await fetchAllExpenses();
      await fetchTotalSpent();
      await fetchBiggestExpense();
      await fetchLastAdded();
      await fetchChartData();
      await fetchPieChartData();

      alert("✅ Expense added successfully!");
    } catch (error) {
      console.error("Error adding expense:", error);
      alert("❌ Error adding expense: " + error.message);
    }
  };

  const fetchAllExpenses = async () => {
    const result = await fetch("/api/expenses/all");
    const data = await result.json();
    setExpenses(data);
  };

  const fetchBiggestExpense = async () => {
    const result = await fetch("/api/expenses/biggest-expense");
    const data = await result.json();
    setBiggestExpense(data);
  };

  const fetchLastAdded = async () => {
    const result = await fetch("/api/expenses/last-added");
    const data = await result.text();
    setLastAdded(data);
  };

  const fetchChartData = async () => {
    const result = await fetch("/api/expenses/monthly-stats");
    const data = await result.json();
    setChartData(data);
  };

  const fetchTotalSpent = async () => {
    const result = await fetch("/api/expenses/total-spent");
    const data = await result.json();
    setTotalSpent(data);
  };

  const fetchPieChartData = async () => {
    const res = await fetch("/api/expenses/category-stats");
    const data = await res.json();
    setPieChartData(data);
  };
  const [totalSpent, setTotalSpent] = useState(0.0);

  const [biggestExpense, setBiggestExpense] = useState(0.0);

  const [lastAdded, setLastAdded] = useState(["N/A"]);

  const [chartData, setChartData] = useState([]);

  const [pieChartData, setPieChartData] = useState([]);

  useEffect(() => {
    fetchAllExpenses();
    fetchTotalSpent();
    fetchLastAdded();
    fetchBiggestExpense();
    fetchChartData();
    fetchPieChartData();
  }, []);
  return (
    <div className="p-2 sm:p-4 space-y-6 bg-gray-800">
      <h2 className="text-2xl font-bold text-gray-300">Welcome Back!</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <div className="bg-gradient-to-r from-blue-100 to-blue-200 shadow rounded-lg p-4">
          <h4 className="text-sm text-gray-600 font-bold">Total Spent</h4>
          <p className="text-xl font-bold text-blue-800">₹ {totalSpent}</p>
        </div>
        <div className="bg-gradient-to-r from-green-100 to-green-200 shadow rounded-lg p-4">
          <h4 className="text-sm text-gray-600 font-bold">Entries</h4>
          <p className="text-xl font-bold text-green-800">{expenses.length}</p>
        </div>
        <div className="bg-gradient-to-r from-yellow-100 to-yellow-200 shadow rounded-lg p-4">
          <h4 className="text-sm text-gray-600 font-bold">Last Added</h4>
          <p className="text-xl font-bold text-yellow-800">{lastAdded}</p>
        </div>
        <div className="bg-gradient-to-r from-red-100 to-red-200 shadow rounded-lg p-4">
          <h4 className="text-sm text-gray-600 font-bold">Biggest Expense</h4>
          <p className="text-xl font-bold text-red-800">{biggestExpense}</p>
        </div>
      </div>

      <ExpenseForm onAddExpense={handleAddExpense} />

      <div className="flex flex-col lg:flex-row gap-8">
        <div className="w-full lg:w-1/2">
          <ExpenseLineChart data={chartData} />
        </div>
        <div className="w-full lg:w-1/2">
          <ExpensePieChart data={pieChartData} />
        </div>
      </div>

      <ExpenseTable expenses={expenses} />
    </div>
  );
}

export default Dashboard;
