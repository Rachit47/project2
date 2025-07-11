import axios from 'axios';
const BASE_URL = "http://localhost:8080/api/reports"
export const fetchOverdueBooks = async () => {
    const response = await axios.get(`${BASE_URL}/overdue-books`);
    return response.data;
}
export const fetchBookCountByCategory = async () => {
    const response = await axios.get(`${BASE_URL}/book-count-by-category`);
    return response.data;
}

export const fetchMembersWithActiveIssues = async () => {
    const response = await axios.get(`${BASE_URL}/members-with-active-issues`);
    return response.data;
}