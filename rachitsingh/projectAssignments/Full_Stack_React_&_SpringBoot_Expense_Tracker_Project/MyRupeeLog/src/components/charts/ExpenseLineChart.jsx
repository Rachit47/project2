import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";

function ExpenseLineChart({ data }) {
  return (
    <div className="bg-gray-700 rounded-xl shadow-lg p-4 w-full h-[350px]">
      <h3 className="text-lg font-semibold text-gray-300 mb-4">
        Monthly Expenses
      </h3>
      <ResponsiveContainer width="100%" height="90%">
        {/* <LineChart data={data}>
          <CartesianGrid strokeDasharray="3 3" stroke="#e5e7eb" />
          <XAxis dataKey="month" />
          <YAxis />
          <Tooltip />
          <Line
            type="monotone"
            dataKey="amount"
            stroke="#3b82f6"
            strokeWidth={2}
          />
        </LineChart> */}
        <AreaChart
          data={data}
          margin={{ top: 10, right: 30, left: 0, bottom: 0 }}
        >
          <defs>
            {/* You only need one gradient if you have one Area */}
            <linearGradient id="colorAmount" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="#3b82f6" stopOpacity={0.8} />{" "}
              {/* A blue shade for expenses */}
              <stop offset="95%" stopColor="#3b82f6" stopOpacity={0} />
            </linearGradient>
          </defs>
          <XAxis dataKey="month" stroke="#e5e7eb" /> <YAxis stroke="#e5e7eb" />
          <CartesianGrid strokeDasharray="3 3" stroke="#4b5563" />{" "}
          <Tooltip
            contentStyle={{
              backgroundColor: "#1f2937",
              borderColor: "#4b5563",
              color: "#e5e7eb",
            }}
            itemStyle={{ color: "#e5e7eb" }}
          />
          <Area
            type="monotone"
            dataKey="amount"
            stroke="#4efc97"
            fillOpacity={1}
            fill="url(#colorAmount)"
          />
        </AreaChart>
      </ResponsiveContainer>
    </div>
  );
}
export default ExpenseLineChart;
