import React, { useEffect, useState } from "react";
import {
  createProductRequest,
  searchProductRequests,
} from "../services/ProductRequestService";

const ProductRequestDashboard = () => {
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(10); // Or 5, 20 based on preference
  const [totalFetched, setTotalFetched] = useState(0);
  const [productRequests, setProductRequests] = useState([]);
  const [allRequestIds, setAllRequestIds] = useState([]);
  const [allProductNames, setAllProductNames] = useState([]);
  const [filters, setFilters] = useState({
    requestIds: [],
    productNames: [],
    status: "",
    requestedBy: "",
  });

  const [requestForm, setRequestForm] = useState({
    productName: "",
    description: "",
    price: "",
    quantity: "",
    requestedBy: "",
  });

  const statusOptions = ["PENDING", "APPROVED", "DECLINED"];

  useEffect(() => {
    applyFilters();
  }, [pageNumber, pageSize]);

  const loadRequests = async () => {
    try {
      const res = await searchProductRequests({
        pageNumber,
        pageSize,
      });

      if (Array.isArray(res.data)) {
        setProductRequests(res.data);
        setTotalFetched(res.data.length);
        setAllRequestIds([...new Set(res.data.map((r) => r.productRequestId))]);
        setAllProductNames([...new Set(res.data.map((r) => r.productName))]);
      }
    } catch (err) {
      if (err.response?.status === 404) {
        setProductRequests([]);
        setTotalFetched(0);
      } else {
        alert(
          "Failed to load product requests: " +
            (err.response?.data?.message || err.message)
        );
      }
    }
  };

  const applyFilters = async () => {
    try {
      const payload = {
        pageNumber,
        pageSize,
        requestIds:
          filters.requestIds.length > 0 ? filters.requestIds.map(Number) : null,
        productNames:
          filters.productNames.length > 0 ? filters.productNames : null,
        status: filters.status || null,
        loggedInUserId: filters.requestedBy
          ? Number(filters.requestedBy)
          : null,
      };

      const res = await searchProductRequests(payload);
      if (Array.isArray(res.data) && res.data.length > 0) {
        setProductRequests(res.data);
        setTotalFetched(res.data.length);
      } else {
        setProductRequests([]);
        setTotalFetched(0);
        alert("No product requests found for the given filter criteria.");
      }
    } catch (err) {
      if (err.response?.status === 404) {
        setProductRequests([]);
        setTotalFetched(0);
        alert("No product requests found for the given filter criteria.");
      } else {
        alert(
          "Something went wrong while filtering: " +
            (err.response?.data?.message || err.message)
        );
      }
    }
  };

  const resetFilters = () => {
    setFilters({
      requestIds: [],
      productNames: [],
      status: "",
      requestedBy: "",
    });
    loadRequests();
  };

  const handleFormChange = (e) => {
    setRequestForm({ ...requestForm, [e.target.name]: e.target.value });
  };

  const handleSubmitRequest = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...requestForm,
        price: parseFloat(requestForm.price),
        quantity: parseInt(requestForm.quantity, 10),
        requestedBy: parseInt(requestForm.requestedBy, 10),
      };
      await createProductRequest(payload);
      alert("Product request submitted successfully!");
      setRequestForm({
        productName: "",
        description: "",
        price: "",
        quantity: "",
        requestedBy: "",
      });
      loadRequests();
    } catch (err) {
      alert(
        "Submission failed: " + (err.response?.data?.message || err.message)
      );
    }
  };

  return (
    <div className="container-fluid bg-dark text-light min-vh-100 py-4 px-3">
      {/* Header */}
      <div className="border-bottom border-secondary pb-3 mb-4 d-flex justify-content-between">
        <h2 className="h4 fw-bold">Product Dashboard</h2>
        <button className="btn btn-outline-light btn-sm" onClick={loadRequests}>
          Refresh
        </button>
      </div>

      {/* Filter Panel */}
      <div className="text-light p-3 rounded mb-4 bg-dark border">
        <h5 className="fw-semibold mb-3">Filter Product Requests</h5>
        <div className="row g-3 align-items-end">
          <div className="col-md-3">
            <label className="form-label">Request IDs</label>
            <select
              className="form-select"
              multiple
              value={filters.requestIds}
              onChange={(e) =>
                setFilters({
                  ...filters,
                  requestIds: Array.from(
                    e.target.selectedOptions,
                    (opt) => opt.value
                  ),
                })
              }
            >
              {allRequestIds.map((id) => (
                <option key={id} value={id}>
                  #{id}
                </option>
              ))}
            </select>
          </div>

          <div className="col-md-3">
            <label className="form-label">Product Names</label>
            <select
              className="form-select"
              multiple
              value={filters.productNames}
              onChange={(e) =>
                setFilters({
                  ...filters,
                  productNames: Array.from(
                    e.target.selectedOptions,
                    (opt) => opt.value
                  ),
                })
              }
            >
              {allProductNames.map((name, idx) => (
                <option key={idx} value={name}>
                  {name}
                </option>
              ))}
            </select>
          </div>

          <div className="col-md-2">
            <label className="form-label">Status</label>
            <select
              className="form-select"
              value={filters.status}
              onChange={(e) =>
                setFilters({ ...filters, status: e.target.value })
              }
            >
              <option value="">All</option>
              {statusOptions.map((status) => (
                <option key={status} value={status}>
                  {status}
                </option>
              ))}
            </select>
          </div>

          <div className="col-md-2">
            <label className="form-label">Requested By</label>
            <input
              type="number"
              className="form-control"
              value={filters.requestedBy}
              onChange={(e) =>
                setFilters({ ...filters, requestedBy: e.target.value })
              }
            />
          </div>

          <div className="col-md-2 d-flex gap-2">
            <button
              className="btn btn-primary btn-sm mt-3"
              onClick={applyFilters}
            >
              Apply
            </button>
            <button
              className="btn btn-outline-light btn-sm mt-3"
              onClick={resetFilters}
            >
              Reset
            </button>
          </div>
        </div>
      </div>

      {/* Table */}
      <div className="table-responsive bg-dark rounded border border-secondary">
        <table className="table table-dark table-hover align-middle mb-0 text-sm">
          <thead className="table-dark text-dark text-uppercase small">
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Quantity</th>
              <th>Price</th>
              <th>Status</th>
              <th>Requested By</th>
              <th>Approved By</th>
              <th>Created At</th>
            </tr>
          </thead>
          <tbody>
            {productRequests.length === 0 ? (
              <tr>
                <td colSpan="8" className="text-center text-secondary">
                  No matching requests found.
                </td>
              </tr>
            ) : (
              productRequests.map((req) => (
                <tr key={req.productRequestId}>
                  <td>#{req.productRequestId}</td>
                  <td>{req.productName}</td>
                  <td>{req.quantity}</td>
                  <td>₹{req.price}</td>
                  <td>
                    <span
                      className={`badge rounded-pill ${
                        req.status === "APPROVED"
                          ? "bg-success"
                          : req.status === "DECLINED"
                          ? "bg-danger"
                          : "bg-warning text-dark"
                      }`}
                    >
                      {req.status}
                    </span>
                  </td>
                  <td>{req.requestedBy}</td>
                  <td>{req.approvedBy || "-"}</td>
                  <td>{new Date(req.createdAtDate).toLocaleString()}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
        {/* Pagination Controls */}
        <div className="d-flex justify-content-between align-items-center mt-3">
          <div>
            Showing page <strong>{pageNumber + 1}</strong> — Fetched:{" "}
            <strong>{totalFetched}</strong> records
          </div>
          <div className="d-flex gap-2">
            <button
              className="btn btn-sm btn-outline-light"
              onClick={() => {
                if (pageNumber > 0) {
                  setPageNumber(pageNumber - 1);
                }
              }}
              disabled={pageNumber === 0}
            >
              ← Previous
            </button>
            <button
              className="btn btn-sm btn-outline-light"
              onClick={() => {
                if (totalFetched === pageSize) {
                  setPageNumber(pageNumber + 1);
                }
              }}
              disabled={totalFetched < pageSize}
            >
              Next →
            </button>
          </div>
        </div>
        <div className="d-flex align-items-center gap-2">
          <label className="form-label m-0">Rows per page:</label>
          <select
            className="form-select form-select-sm w-auto"
            value={pageSize}
            onChange={(e) => {
              setPageSize(Number(e.target.value));
              setPageNumber(0);
            }}
          >
            {[5, 10, 20, 50].map((size) => (
              <option key={size} value={size}>
                {size}
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* Create Request Form */}
      <div className="mt-5 bg-dark text-light p-4 rounded border">
        <h4 className="mb-3 fw-semibold">Create New Product Request</h4>
        <form onSubmit={handleSubmitRequest} className="row g-3">
          <div className="col-md-6">
            <input
              type="text"
              name="productName"
              placeholder="Product Name"
              className="form-control"
              value={requestForm.productName}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="col-md-6">
            <input
              type="number"
              name="quantity"
              placeholder="Quantity"
              className="form-control"
              value={requestForm.quantity}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="col-12">
            <textarea
              name="description"
              placeholder="Description"
              className="form-control"
              rows={3}
              value={requestForm.description}
              onChange={handleFormChange}
              required
            ></textarea>
          </div>
          <div className="col-md-6">
            <input
              type="number"
              step="0.01"
              name="price"
              placeholder="Price"
              className="form-control"
              value={requestForm.price}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="col-md-6">
            <input
              type="number"
              name="requestedBy"
              placeholder="Requested By (User ID)"
              className="form-control"
              value={requestForm.requestedBy}
              onChange={handleFormChange}
              required
            />
          </div>
          <div className="col-12 text-end">
            <button type="submit" className="btn btn-success btn-sm">
              Submit Request
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ProductRequestDashboard;
