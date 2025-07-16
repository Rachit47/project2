import React, { useEffect, useState } from "react";
import {
  createProductRequest,
  searchProductRequests,
} from "../services/ProductRequestService";
import { Dropdown, Form } from "react-bootstrap";

const ProductRequestDashboard = () => {
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalCount, setTotalCount] = useState(0);
  const [productRequests, setProductRequests] = useState([]);
  const [allRequestIds, setAllRequestIds] = useState([]);
  const [allProductNames, setAllProductNames] = useState([]);
  const [filters, setFilters] = useState({
    requestIds: [],
    productNames: [],
    status: [],
    requestedBy: [],
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
    fetchFilteredData();
  }, [pageNumber, pageSize]);

  const fetchFilteredData = async () => {
    try {
      const payload = {
        pageNumber,
        pageSize,
        requestIds:
          filters.requestIds.length > 0 ? filters.requestIds.map(Number) : null,
        productNames:
          filters.productNames.length > 0 ? filters.productNames : null,
        status: filters.status.length > 0 ? filters.status : null,
        requestedBy:
          filters.requestedBy.length > 0
            ? filters.requestedBy.map(Number)
            : null,
      };

      const res = await searchProductRequests(payload);
      const data = res.data.records || res.data;
      const count = res.data.totalCount ?? data.length;

      setProductRequests(data);
      setTotalCount(count);

      setAllRequestIds([...new Set(data.map((r) => r.productRequestId))]);
      setAllProductNames([...new Set(data.map((r) => r.productName))]);

      if (data.length === 0) {
        alert("No product requests found for the given filter criteria.");
      }
    } catch (err) {
      if (err.response?.status === 404) {
        setProductRequests([]);
        setTotalCount(0);
        alert("No product requests found for the given filter criteria.");
      } else {
        alert(
          "Something went wrong while fetching data: " +
            (err.response?.data?.message || err.message)
        );
      }
    }
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
      setPageNumber(0);
      fetchFilteredData();
    } catch (err) {
      alert(
        "Submission failed: " + (err.response?.data?.message || err.message)
      );
    }
  };

  const resetFilters = () => {
    setFilters({
      requestIds: [],
      productNames: [],
      status: [],
      requestedBy: [],
    });
    setPageNumber(0);
    fetchFilteredData();
  };

  const handleApplyFilters = () => {
    setPageNumber(0);
    fetchFilteredData();
  };

  // Calculate total pages, ensure at least 1 page if there are records
  const totalPages = totalCount > 0 ? Math.ceil(totalCount / pageSize) : 1;
  // Ensure pageNumber is always valid
  useEffect(() => {
    if (pageNumber >= totalPages) {
      setPageNumber(totalPages - 1);
    }
    // eslint-disable-next-line
  }, [totalPages]);
  return (
    <div className="container-fluid bg-dark text-light min-vh-100 py-4 px-3">
      {/* Header */}
      <div className="border-bottom border-secondary pb-3 mb-4 d-flex justify-content-between">
        <h2 className="h4 fw-bold">Product Dashboard</h2>
        <button
          className="btn btn-outline-light btn-sm"
          onClick={fetchFilteredData}
        >
          Refresh
        </button>
      </div>

      {/* Filter Panel */}
      <div className="text-light p-3 rounded mb-4 bg-dark border">
        <h5 className="fw-semibold mb-3">Filter Product Requests</h5>
        <div className="row g-3 align-items-end">
          <div className="col-md-3">
            <label className="form-label">Request IDs</label>
            <Dropdown autoClose="outside">
              <Dropdown.Toggle
                className="form-control text-start"
                variant="secondary"
                id="dropdown-request-ids"
              >
                {filters.requestIds.length > 0
                  ? `${filters.requestIds.length} selected`
                  : "Select Request IDs"}
              </Dropdown.Toggle>
              <Dropdown.Menu style={{ maxHeight: "200px", overflowY: "auto" }}>
                {allRequestIds.map((id) => (
                  <Form.Check
                    key={id}
                    type="checkbox"
                    className="px-3"
                    label={`#${id}`}
                    value={id}
                    checked={filters.requestIds.includes(String(id))}
                    onChange={(e) => {
                      const value = e.target.value;
                      setFilters((prev) => {
                        const newIds = prev.requestIds.includes(value)
                          ? prev.requestIds.filter((v) => v !== value)
                          : [...prev.requestIds, value];
                        return { ...prev, requestIds: newIds };
                      });
                    }}
                  />
                ))}
              </Dropdown.Menu>
            </Dropdown>
          </div>

          <div className="col-md-3">
            <label className="form-label">Product Names</label>
            <Dropdown autoClose="outside">
              <Dropdown.Toggle
                className="form-control text-start"
                variant="secondary"
                id="dropdown-product-names"
              >
                {filters.productNames.length > 0
                  ? `${filters.productNames.length} selected`
                  : "Select Product Names"}
              </Dropdown.Toggle>
              <Dropdown.Menu style={{ maxHeight: "200px", overflowY: "auto" }}>
                {allProductNames.map((name) => (
                  <Form.Check
                    key={name}
                    type="checkbox"
                    className="px-3"
                    label={name}
                    value={name}
                    checked={filters.productNames.includes(name)}
                    onChange={(e) => {
                      const value = e.target.value;
                      setFilters((prev) => {
                        const newNames = prev.productNames.includes(value)
                          ? prev.productNames.filter((v) => v !== value)
                          : [...prev.productNames, value];
                        return { ...prev, productNames: newNames };
                      });
                    }}
                  />
                ))}
              </Dropdown.Menu>
            </Dropdown>
          </div>

          <div className="col-md-2">
            <label className="form-label">Status</label>
            <Dropdown autoClose="outside">
              <Dropdown.Toggle
                className="form-control text-start"
                variant="secondary"
                id="dropdown-status"
              >
                {filters.status.length > 0
                  ? `${filters.status.length} selected`
                  : "Select Status"}
              </Dropdown.Toggle>
              <Dropdown.Menu>
                {statusOptions.map((status) => (
                  <Form.Check
                    key={status}
                    type="checkbox"
                    className="px-3"
                    label={status}
                    value={status}
                    checked={filters.status.includes(status)}
                    onChange={(e) => {
                      const value = e.target.value;
                      setFilters((prev) => {
                        const newStatus = prev.status.includes(value)
                          ? prev.status.filter((v) => v !== value)
                          : [...prev.status, value];
                        return { ...prev, status: newStatus };
                      });
                    }}
                  />
                ))}
              </Dropdown.Menu>
            </Dropdown>
          </div>

          <div className="col-md-2">
            <label className="form-label">Requested By</label>
            <input
              type="text"
              className="form-control"
              placeholder="Comma-separated IDs"
              value={filters.requestedBy.join(",")}
              onChange={(e) =>
                setFilters({
                  ...filters,
                  requestedBy: e.target.value
                    .split(",")
                    .map((id) => id.trim())
                    .filter((id) => id),
                })
              }
            />
          </div>
          <div className="col-md-2 d-flex gap-2">
            <button
              className="btn btn-primary btn-sm mt-3"
              onClick={handleApplyFilters}
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
              <th>Qty</th>
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

        {/* Pagination */}
        <div className="d-flex justify-content-between align-items-center mt-3 px-2">
          <div>
            Page <strong>{pageNumber + 1}</strong> of{" "}
            <strong>{totalPages}</strong>
          </div>
          <div className="d-flex gap-2">
            <button
              className="btn btn-sm btn-outline-light cursor-pointer"
              onClick={() => setPageNumber((prev) => prev - 1)}
              disabled={pageNumber <= 0}
            >
              ← Prev
            </button>
            <button
              className="btn btn-sm btn-outline-light cursor-pointer"
              onClick={() => {
                setPageNumber((prev) => prev + 1);
                console.log("hello");
              }}
              // disabled={pageNumber >= totalPages - 1}
            >
              Next →
            </button>
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
              {[5, 10, 20].map((size) => (
                <option key={size} value={size}>
                  {size}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>

      {/* Create Form */}
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
