import React, { useEffect, useState } from "react";
import BookForm from "./BookForm";
import BookList from "./BookList";
import { getBooks, addBook, updateBook, deleteBook } from "../../services/bookService";

const ManageBooks = () => {
  const [books, setBooks] = useState([]);
  const [selectedBook, setSelectedBook] = useState(null);

  const loadBooks = async () => {
    const result = await getBooks();
    setBooks(result.data);
  };

  useEffect(() => {
    loadBooks();
  }, []);

  const handleAddOrUpdate = async (book) => {
    if (book.bookID) {
      await updateBook(book);
    } else {
      await addBook(book);
    }
    setSelectedBook(null);
    loadBooks();
  };

  const handleDelete = async (id) => {
    await deleteBook(id);
    loadBooks();
  };

  return (
    <div className="max-w-4xl mx-auto mt-8 bg-gray-700 text-gray-200">
      <h2 className="text-xl font-bold mb-4 text-center">📚 Librarium365 - Book Management Dashboard</h2>
      <BookForm onSubmit={handleAddOrUpdate} selectedBook={selectedBook} clearSelection={() => setSelectedBook(null)} />
      <BookList books={books} onEdit={setSelectedBook} onDelete={handleDelete} />
    </div>
  );
};

export default ManageBooks;
