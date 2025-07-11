import axios from 'axios';
const BASE_URL = "http://localhost:8080/api/issues"

export const issueBook = async (issueData) => {
    const response = await axios.post(`${BASE_URL}/issue`, issueData);
    return response.data;
}
export const returnBook = async (id) =>{
    const response = await axios.post(`${BASE_URL}/return/${id}`);
    return response.data;
}