import React, { useEffect, useState } from 'react'
const BookForm = ({onSubmit, selectedBook, clearSelection}) => {
  const [book, setBook] = useState({
    title: "",
    author: "",
    category: "",
    condition: "ACTIVE",
    availability: "AVAILABLE",
  });

  useEffect(() => {
    if(selectedBook){
        setBook(selectedBook);
    }else{
        setBook({title: "", author: "", category: "", condition: "ACTIVE", availability: "AVAILABLE"});
    }
  }, [selectedBook]);

  const handleChange = (e) => {
    setBook({...book, [e.target.name]: e.target.value});
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(book);
  };

  return (
    <form onSubmit = {handleSubmit} className='space-y-2 bg-gray-700 p-4 rounded-md text-gray-200'>
        <div className='flex gap-2'>
            <input name = "title" value = {book.title} onChange = {handleChange} placeholder='Title' className='p-2 border w-full text-gray-200' required />
            <input name = "author" value = {book.author} onChange = {handleChange} placeholder='Author' className='p-2 border w-full text-gray-200' required />
        </div>
         <div className="flex gap-2">
        <input name="category" value={book.category} onChange={handleChange} placeholder="Category" className="p-2 border w-full text-gray-200" required />
        <select name="condition" value={book.condition} onChange={handleChange} className="p-2 border w-full">
          <option value="ACTIVE" className='bg-gray-700 '>Active</option>
          <option value="INACTIVE" className='bg-gray-700 '>Inactive</option>
        </select>
        <select name="availability" value={book.availability} onChange={handleChange} className="p-2 border w-full text-gray-200">
          <option value="AVAILABLE" className='bg-gray-700 '>Available</option>
          <option value="ISSUED" className='bg-gray-700 '>Issued</option>
        </select>
      </div>
      <div className="flex justify-between items-center">
        <button type="submit" className="bg-blue-400 text-white px-4 py-1 rounded">
          {selectedBook ? "Update Book" : "Add Book"}
        </button>
        {selectedBook && (
          <button type="button" onClick={clearSelection} className="text-red-500 text-sm">
            Cancel Edit
          </button>
        )}
      </div>
    </form>
  )
}

export default BookForm;