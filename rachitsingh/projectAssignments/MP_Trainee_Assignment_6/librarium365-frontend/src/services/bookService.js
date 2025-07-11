import axios from "axios";
const API_BASE = "http://localhost:8080/api/books";

export const getBooks = () => axios.get(API_BASE);
export const addBook = (book) => axios.post(API_BASE, book);
export const updateBook = (book) => axios.put(`${API_BASE}/${book.bookID}`, book);
export const deleteBook = (id) => axios.delete(`${API_BASE}/${id}`);