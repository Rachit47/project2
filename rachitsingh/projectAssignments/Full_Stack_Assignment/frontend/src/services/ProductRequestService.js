import axios from "axios";

const API_BASE = "http://localhost:8080/api/product-requests";

export const createProductRequest = (data) =>
  axios.post(`${API_BASE}/create`, data);

export const searchProductRequests = (criteria) =>
  axios.post(`${API_BASE}/search`, criteria);

export const updateProductRequest = (data) =>
  axios.put(`${API_BASE}/update`, data);
