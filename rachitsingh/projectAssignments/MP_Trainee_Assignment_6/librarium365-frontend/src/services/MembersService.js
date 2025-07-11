import axios from "axios";

const BASE_URL = "http://localhost:8080/api/members"

export const getAllMembers = async () => {
  const response = await axios.get(BASE_URL);
  return response.data;
};

export const getMemberById = async (id) => {
  const response = await axios.get(`${BASE_URL}/${id}`);
  return response.data;
};

export const addMember = async (memberData) => {
  const response = await axios.post(BASE_URL, memberData);
  return response.data;
};

export const updateMember = async (memberData) => {
  const response = await axios.put(`${BASE_URL}/update`, memberData);
  return response.data;
};

export const deleteMember = async (id) => {
  const response = await axios.delete(`${BASE_URL}/${id}`);
  return response.data;
};
