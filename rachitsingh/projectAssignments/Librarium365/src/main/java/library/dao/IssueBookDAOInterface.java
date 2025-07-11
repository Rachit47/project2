package library.dao;

import library.model.IssueBook;
import library.exceptionhandler.IssueNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IssueBookDAOInterface {

    void insertIssue(IssueBook record, Connection conn) throws SQLException;

    void markReturnedBook(int issueID, Connection conn) throws SQLException, IssueNotFoundException;

    IssueBook findByID(int issueID, Connection conn) throws SQLException, IssueNotFoundException;

    List<IssueBook> findAllIssues();

    List<IssueBook> findOverdueIssues();
}
