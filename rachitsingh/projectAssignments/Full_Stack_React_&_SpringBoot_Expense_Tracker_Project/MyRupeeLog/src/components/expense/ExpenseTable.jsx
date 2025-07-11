function ExpenseTable({ expenses }) {
  return (
    <div className="bg-gray-700 p-4 rounded-lg shadow-md overflow-auto">
      <h3 className="text-lg font-semibold mb-4 text-gray-300">
        Expense History
      </h3>

      {expenses.length === 0 ? (
        <p className="text-gray-500 text-sm">No expenses added as of now.</p>
      ) : (
        <table className="min-w-full text-sm text-gray-200">
          <thead className="bg-gray-700">
            <tr>
              <th className="px-4 py-2 text-left">Title</th>
              <th className="px-4 py-2 text-left">Amount</th>
              <th className="px-4 py-2 text-left">Category</th>
              <th className="px-4 py-2 text-left">Date</th>
            </tr>
          </thead>

          <tbody>
            {expenses.map((expense, index) => (
              <tr key={index} className="border-t hover:bg-gray-600">
                <td className="px-4 py-2">{expense.title}</td>
                <td className="px-4 py-2">₹{expense.amount}</td>
                <td className="px-4 py-2">{expense.category}</td>
                <td className="px-4 py-2">{expense.date}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default ExpenseTable;
