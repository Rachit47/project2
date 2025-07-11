package com.springbootproject.librarium365.repository;

import com.springbootproject.librarium365.exceptions.IssueNotFoundException;
import com.springbootproject.librarium365.model.IssueBook;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IssueBookDAO {

    void insertIssue(IssueBook record, Connection conn) throws SQLException;

    void markReturnedBook(int issueID, Connection conn) throws SQLException, IssueNotFoundException;

    IssueBook findByID(int issueID, Connection conn) throws SQLException, IssueNotFoundException;

    List<IssueBook> findAllIssues();

    List<IssueBook> findOverdueIssues();
}
