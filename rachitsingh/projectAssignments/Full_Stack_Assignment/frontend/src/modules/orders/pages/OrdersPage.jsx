import React, { useState, useEffect } from "react";
import OrderTable from "../components/OrderTable";
import OrderCard from "../components/OrderCard";
import {
  fetchOrdersByCriteria,
  fetchOrderDetails,
  updateOrder,
} from "../api/orders";

const OrdersPage = () => {
  const statusLabels = {
    P: "Processing",
    C: "Cancelled",
    D: "Delivered",
  };

  const [orderIdsList, setOrderIdsList] = useState([]);
  const [statusOptions, setStatusOptions] = useState(["P", "C", "D"]);
  const [filters, setFilters] = useState({
    orderIds: [],
    statuses: [],
    fromDate: "",
    toDate: "",
  });
  const [orders, setOrders] = useState([]);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const customerId = 5;

  const fetchOrders = async () => {
    const criteria = {
      customerId,
      pageNumber: 0,
      pageSize: 10,
    };

    try {
      const res = await fetchOrdersByCriteria(criteria);
      if (Array.isArray(res.data)) {
        setOrders(res.data);
      } else {
        console.error("Expected array but got:", res.data);
        setOrders([]);
      }
    } catch (err) {
      console.error("Error fetching orders:", err);
      setOrders([]);
    }
  };

  useEffect(() => {
    fetchOrders();
    fetchOrdersByCriteria({ customerId, pageSize: 100 })
      .then((res) => {
        if (Array.isArray(res.data)) {
          const uniqueIds = [...new Set(res.data.map((o) => o.orderId))];
          setOrderIdsList(uniqueIds);
        }
      })
      .catch((err) => console.error("Error loading order IDs:", err));
  }, []);

  const handleView = async (orderId) => {
    try {
      const res = await fetchOrderDetails(orderId, customerId);
      if (res?.data) {
        setSelectedOrder(res.data);
      }
    } catch (err) {
      console.error("Failed to fetch order details", err);
      alert("Could not load order details.");
    }
  };

  const handleCancel = async (orderId) => {
    try {
      const payload = {
        customerId,
        orderIds: [orderId],
        addressFlag: false,
      };

      await updateOrder(payload);
      await fetchOrders();
      setSelectedOrder(null);
      alert("Order cancelled successfully!");
    } catch (err) {
      console.error("Cancel failed", err);
      alert("Failed to cancel order.");
    }
  };

  const handleAddressUpdate = async (newAddress) => {
    try {
      const payload = {
        customerId,
        orderIds: [selectedOrder.orderId],
        address: newAddress,
        addressFlag: true,
      };

      await updateOrder(payload);
      await fetchOrders();
      setSelectedOrder(null);
      alert("Address updated successfully!");
    } catch (err) {
      console.error("Address update failed", err);
      alert("Failed to update address.");
    }
  };

  const applyFilters = async () => {
    const criteria = {
      customerId,
      orderIds: filters.orderIds.length ? filters.orderIds.map(Number) : null,
      orderStatuses: filters.statuses.length > 0 ? filters.statuses : null,
      fromDate: filters.fromDate || null,
      toDate: filters.toDate || null,
      pageNumber: 0,
      pageSize: 10,
    };

    try {
      const res = await fetchOrdersByCriteria(criteria);
      if (Array.isArray(res.data)) {
        setOrders(res.data);
      } else {
        setOrders([]);
      }
    } catch (err) {
      console.error("Error applying filters:", err);
      setOrders([]);
    }
  };

  const handleReset = () => {
    setFilters({ orderIds: [], statuses: [], fromDate: "", toDate: "" });
    fetchOrders();
  };

  return (
    <div className="container-fluid bg-dark text-light min-vh-100 py-4 px-3">
      <div className="d-flex justify-content-between align-items-center mb-4 border-bottom pb-2 border-secondary">
        <h1 className="h3 fw-bold">Your Orders</h1>
        <button
          onClick={fetchOrders}
          className="btn btn-primary btn-sm shadow-sm"
        >
          Refresh
        </button>
      </div>

      <div className="mb-4 border rounded p-3 bg-secondary text-light">
        <div className="row g-3 align-items-end">
          <div className="col-md-3">
            <label className="form-label">Order ID</label>
            <select
              className="form-select"
              multiple
              value={filters.orderIds}
              onChange={(e) => {
                const selected = Array.from(
                  e.target.selectedOptions,
                  (option) => option.value
                );
                setFilters({ ...filters, orderIds: selected });
              }}
            >
              {orderIdsList.map((id) => (
                <option key={id} value={id}>
                  #{id}
                </option>
              ))}
            </select>
          </div>

          <div className="col-md-3">
            <label className="form-label">Status</label>
            {statusOptions.map((status) => (
              <div key={status} className="form-check">
                <input
                  type="checkbox"
                  className="form-check-input"
                  checked={filters.statuses.includes(status)}
                  onChange={() => {
                    setFilters((prev) => {
                      const exists = prev.statuses.includes(status);
                      return {
                        ...prev,
                        statuses: exists
                          ? prev.statuses.filter((s) => s !== status)
                          : [...prev.statuses, status],
                      };
                    });
                  }}
                  id={`check-${status}`}
                />
                <label className="form-check-label" htmlFor={`check-${status}`}>
                  {statusLabels[status] || status}
                </label>
              </div>
            ))}
          </div>

          <div className="col-md-3">
            <label className="form-label">From Date</label>
            <input
              type="date"
              className="form-control"
              value={filters.fromDate}
              onChange={(e) =>
                setFilters({ ...filters, fromDate: e.target.value })
              }
            />
          </div>

          <div className="col-md-3">
            <label className="form-label">To Date</label>
            <input
              type="date"
              className="form-control"
              value={filters.toDate}
              onChange={(e) =>
                setFilters({ ...filters, toDate: e.target.value })
              }
            />
          </div>

          <div className="col-12 mt-3 d-flex gap-2">
            <button className="btn btn-primary btn-sm" onClick={applyFilters}>
              Search
            </button>
            <button className="btn btn-outline-light btn-sm" onClick={handleReset}>
              Reset
            </button>
          </div>
        </div>
      </div>

      <OrderTable
        orders={orders}
        onView={handleView}
        onCancel={handleCancel}
        statusLabels={statusLabels}
      />

      {selectedOrder && (
        <div
          className="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center"
          style={{ backgroundColor: "rgba(0, 0, 0, 0.6)", zIndex: 1050 }}
        >
          <div
            className="w-100"
            style={{ maxWidth: "720px", padding: "0 1rem" }}
          >
            <OrderCard
              order={selectedOrder}
              onUpdateAddress={handleAddressUpdate}
              onClose={() => setSelectedOrder(null)}
              statusLabels={statusLabels}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default OrdersPage;
