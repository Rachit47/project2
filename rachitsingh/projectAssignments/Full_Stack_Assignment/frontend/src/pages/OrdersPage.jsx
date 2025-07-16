import React, { useState, useEffect } from "react";
import OrderTable from "../components/OrderTable";
import OrderCard from "../components/OrderCard";

import {
  fetchOrdersByCriteria,
  fetchOrderDetails,
  updateOrder,
} from "../services/orders";
import { useAuth } from "../context/AuthContext";

const OrdersPage = () => {
  const statusLabels = {
    P: "Processing",
    C: "Cancelled",
  };

  const statusOptions = ["P", "C"];

  const [orderIdsList, setOrderIdsList] = useState([]);
  const [filters, setFilters] = useState({
    orderIds: [],
    statuses: [],
    fromDate: "",
    toDate: "",
  });
  const [orders, setOrders] = useState([]);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const { currentUser } = useAuth();
  const customerId = currentUser.userId;

  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize] = useState(5); 
  const [totalCount, setTotalCount] = useState(0);

  const buildCriteria = () => {
    return {
      customerId,
      orderIds: filters.orderIds.length ? filters.orderIds.map(Number) : null,
      orderStatuses: filters.statuses.length ? filters.statuses : null,
      fromDate: filters.fromDate ? filters.fromDate + "T00:00:00" : null,
      toDate: filters.toDate ? filters.toDate + "T23:59:59" : null,
      pageNumber,
      pageSize,
      orderIdsFlag: filters.orderIds.length ? 1 : 0,
      orderStatusFlag: filters.statuses.length ? 1 : 0,
      fromDateFlag: filters.fromDate ? 1 : 0,
      toDateFlag: filters.toDate ? 1 : 0,
    };
  };

  const fetchOrders = async () => {
    try {
      const criteria = buildCriteria();
      console.log("Fetching orders with criteria:", criteria);
      const res = await fetchOrdersByCriteria(criteria);
      console.log("API response:", res.data);
      if (Array.isArray(res.data)) {
        setOrders(res.data);
        const count = res.data.totalCount ?? res.data.length;
        setTotalCount(count);
        console.log(`Got ${res.data.length} orders, total: ${count}`);
      } else if (res.data && res.data.records) {
        setOrders(res.data.records);
        setTotalCount(res.data.totalCount);
        console.log(
          `Got ${res.data.records.length} orders, total: ${res.data.totalCount}`
        );
      } else {
        setOrders([]);
        setTotalCount(0);
      }
    } catch (err) {
      console.error("Error fetching orders:", err);
      setOrders([]);
      setTotalCount(0);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, [pageNumber, pageSize]);


  useEffect(() => {
    console.log("Fetching order IDs for filter...");
    fetchOrdersByCriteria({
      customerId,
      pageNumber: 0, 
      pageSize: 100,
    })
      .then((res) => {
        console.log("Order IDs response:", res.data);
        if (Array.isArray(res.data)) {
          const uniqueIds = [...new Set(res.data.map((o) => o.orderId))];
          setOrderIdsList(uniqueIds);
          console.log("Unique order IDs:", uniqueIds);
        } else if (res.data && res.data.records) {
          const uniqueIds = [
            ...new Set(res.data.records.map((o) => o.orderId)),
          ];
          setOrderIdsList(uniqueIds);
          console.log("Unique order IDs from records:", uniqueIds);
        }
      })
      .catch((err) => console.error("Error loading order IDs:", err));
  }, [customerId]);

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
      fetchOrders();
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
      fetchOrders();
      setSelectedOrder(null);
      alert("Address updated successfully!");
    } catch (err) {
      console.error("Address update failed", err);
      alert("Failed to update address.");
    }
  };

  
  const totalPages = totalCount > 0 ? Math.ceil(totalCount / pageSize) : 1;

  const applyFilters = () => {
    setPageNumber(0);
    fetchOrders();
  };

  const handleReset = () => {
    setFilters({ orderIds: [], statuses: [], fromDate: "", toDate: "" });
    setPageNumber(0);
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
            <button
              className="btn btn-outline-light btn-sm"
              onClick={handleReset}
            >
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

      <div className="d-flex justify-content-between align-items-center mt-3">
        <button
          className="btn btn-outline-light btn-sm"
          onClick={() => setPageNumber((p) => Math.max(p - 1, 0))}
          // disabled={pageNumber === 0}
        >
          Previous
        </button>
        <span>
          Page <strong>{pageNumber + 1}</strong> of{" "}
          <strong>{totalPages}</strong>
        </span>
        <button
          className="btn btn-outline-light btn-sm"
          onClick={() => setPageNumber((p) => p + 1)}
          // disabled={pageNumber >= totalPages - 1}
        >
          Next
        </button>
      </div>

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
