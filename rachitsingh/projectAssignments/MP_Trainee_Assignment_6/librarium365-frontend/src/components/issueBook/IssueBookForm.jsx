import React, { useState } from 'react'
const IssueBookForm = ({onIssue, onReturn}) => {
  const [issueData, setIssueData] = useState({
    bookId: "",
    memberId: "",
  });

  const [returnId, setReturnId] = useState('');
  
  const handleIssueChange = (e) => {
    const {name, value} = e.target;
    setIssueData((prev) => ({...prev, [name]: value}));
  }
  const handleReturnChange = (e) =>{
    setReturnId(e.target.value);
  }

  const handleIssueSubmit = (e) =>{
    e.preventDefault();
    if(!issueData.bookId || !issueData.memberId){
      return;
    }

    onIssue(issueData);
    setIssueData({
      bookId: '',
      memberId: '',
    });
  };

  const handleReturnSubmit = (e) =>{
    e.preventDefault();

    if(!returnId) return;

    onReturn(returnId);
    setReturnId('');
  }

  return (
     <div className="bg-gray-800 p-4 rounded-md shadow-md">
      <h3 className="text-lg font-semibold mb-2">📘Book Issue and Return Portal</h3>
      <form onSubmit={handleIssueSubmit} className="space-y-3">
  <input
    type="number"
    name="bookId"
    value={issueData.bookId}
    onChange={handleIssueChange}
    placeholder="Book ID"
    className="w-full p-2 rounded bg-gray-700 text-white"
  />
  <input
    type="number"
    name="memberId"
    value={issueData.memberId}
    onChange={handleIssueChange}
    placeholder="Member ID"
    className="w-full p-2 rounded bg-gray-700 text-white"
  />
  <button
    type="submit"
    className="bg-green-600 hover:bg-green-700 px-4 py-2 rounded text-white"
  >
    Issue Book
  </button>
</form>


      <h3 className="text-lg font-semibold mt-6 mb-2">Return a Book</h3>
      <form onSubmit={handleReturnSubmit} className="space-y-3">
        <input
          type="number"
          name="returnId"
          value={returnId}
          onChange={handleReturnChange}
          placeholder="Issue ID"
          className="w-full p-2 rounded bg-gray-700 text-white"
        />
        <button
          type="submit"
          className="bg-yellow-600 hover:bg-yellow-700 px-4 py-2 rounded text-white"
        >
          Return Book
        </button>
      </form>
    </div>
  );
}
export default IssueBookForm;
