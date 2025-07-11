import React from "react";

const BookList = ({ books, onEdit, onDelete }) => {
  return (
    <table className="w-full mt-4 border text-sm text-gray-200">
      <thead className="bg-gray-700">
        <tr>
          <th className="p-2">ID</th>
          <th className="p-2">Title</th>
          <th className="p-2">Author</th>
          <th className="p-2">Category</th>
          <th className="p-2">Condition</th>
          <th className="p-2">Availability</th>
          <th className="p-2">Actions</th>
        </tr>
      </thead>
      <tbody>
        {books.length === 0 ? (
          <tr>
            <td colSpan="7" className="p-4 text-center text-gray-300">No books found</td>
          </tr>
        ) : (
        books.map((b) => (
          <tr key={b.bookID} className="text-center border-b text-gray-200">
            <td className="p-1">{b.bookID}</td>
            <td className="p-1">{b.title}</td>
            <td className="p-1">{b.author}</td>
            <td className="p-1">{b.category}</td>
            <td className="p-1">{b.condition}</td>
            <td className="p-1">{b.availability}</td>
            <td className="p-1 space-x-2">
              <button onClick={() => onEdit(b)} className="text-blue-500">Edit</button>
              <button onClick={() => onDelete(b.bookID)} className="text-red-500">Delete</button>
            </td>
          </tr>
        )))}
      </tbody>
    </table>
  );
};

export default BookList;
