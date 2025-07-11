import React, { useEffect, useState } from 'react';
import {
  fetchOverdueBooks,
  fetchBookCountByCategory,
  fetchMembersWithActiveIssues,
} from '../../services/reportsService';
const ManageReports = () => {
  const [overdueBooks, setOverdueBooks] = useState([]);
  const [bookCountByCategory, setBookCountByCategory] = useState([]);
  const [activeIssueMembers, setActiveIssueMembers] = useState([]);

  useEffect (()=>{
    loadReports();
    const loadData = async () => {
      const data = await fetchBookCountByCategory();
      console.log("bookCountByCategory:", data);
      setBookCountByCategory(data);
    }
   loadData();
  }, [])

  const loadReports = async() => {
    try{
        const [overdue, bookCounts, activeMembers] = await Promise.all([
            fetchOverdueBooks(),
            fetchBookCountByCategory(),
            fetchMembersWithActiveIssues(),
        ]);

        setOverdueBooks(overdue);
        setBookCountByCategory(bookCounts);
        setActiveIssueMembers(activeMembers);
    } catch (error){
        console.log("Error fetching report data: ", error);
    }
  };

  return (
    <div className="max-w-6xl mx-auto mt-8 bg-gray-800 text-gray-200">
      <h2 className="text-2xl font-bold mb-4 text-center">📊 Librarium365 - Reports Dashboard</h2>
      <section className="mb-6">
        <h3 className="text-xl font-semibold mb-2">📚 Overdue Books</h3>
        {overdueBooks.length == 0 ? (<p>No overdue books.</p>): (
            <table className="w-full table-auto bg-gray-700 rounded">
            <thead>
              <tr className="bg-gray-900">
                <th className="px-3 py-2">Issue ID</th>
                <th className="px-3 py-2">Book ID</th>
                <th className="px-3 py-2">Member ID</th>
                <th className="px-3 py-2">Issue Date</th>
              </tr>
            </thead>
            <tbody>
              {overdueBooks.map((book) => (
                <tr key={book.issueID} className="border-t border-gray-600">
                  <td className="px-3 py-2">{book.issueID}</td>
                  <td className="px-3 py-2">{book.bookID}</td>
                  <td className="px-3 py-2">{book.memberID}</td>
                  <td className="px-3 py-2">{book.issueDate}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
      <section className="mb-6">
        <h3 className="text-xl font-semibold mb-2">📦 Book Count by Category</h3>
        {
            bookCountByCategory.length == 0 ? (<p>No category-wise data is available as of now.</p>) :
            (
                <ul className='list-disc ml-5 space-y-1'>
                    {bookCountByCategory.map((item, index) => (
                      <li key={index}>
                        <span>{item.category}</span>: {item.count} books
                    </li>
                    ))}
                </ul>
            )}
      </section>
      <section className="mb-6">
        <h3 className="text-xl font-semibold mb-2">👥 Members with Active Book Issues</h3>
        {activeIssueMembers.length == 0 ? (<p>No members have active issues as of now.</p>):
        (
            <table className="w-full table-auto bg-gray-700 rounded">
            <thead>
              <tr className="bg-gray-900">
                <th className="px-3 py-2">Member ID</th>
                <th className="px-3 py-2">Name</th>
                <th className="px-3 py-2">Email</th>
                <th className="px-3 py-2">Mobile</th>
              </tr>
            </thead>
            <tbody>
                {
                    activeIssueMembers.map((member) => (
                        <tr key={member.memberID} className="border-t border-gray-600">
                            <td className="px-3 py-2">{member.memberID}</td>
                            <td className="px-3 py-2">{member.name}</td>
                            <td className="px-3 py-2">{member.email}</td>
                            <td className="px-3 py-2">{member.mobile}</td>
                        </tr>
                    ))
                }
            </tbody>
            </table>
        )}
      </section>
    </div>
  )
}

export default ManageReports