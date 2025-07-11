import IssueBookForm from './IssueBookForm';
import { issueBook, returnBook } from '../../services/issueBookService';

const ManageIssueBook = () => {
  const handleIssue = async (data) => {
    await issueBook(data);
    alert("Book Issued successfully!");
  };

  const handleReturn = async (id) => {
    await returnBook(id);
    alert("Book Returned successfully!");
  };

  return (
    <div className="max-w-2xl mx-auto mt-8 text-white">
      <IssueBookForm onIssue={handleIssue} onReturn={handleReturn} />
    </div>
  );
};

export default ManageIssueBook;