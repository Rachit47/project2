package com.springbootproject.librarium365.repository.impl;

import com.springbootproject.librarium365.enums.IssueStatus;
import com.springbootproject.librarium365.exceptions.IssueNotFoundException;
import com.springbootproject.librarium365.model.IssueBook;
import com.springbootproject.librarium365.repository.IssueBookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IssueBookDAOImpl implements IssueBookDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<IssueBook> issueRowMapper = (rs, rowNum) -> {
        IssueBook rec = new IssueBook();
        rec.setIssueID(rs.getInt("IssueID"));
        rec.setBookID(rs.getInt("BookID"));
        rec.setMemberID(rs.getInt("MemberID"));
        rec.setStatus(IssueStatus.fromCode(rs.getString("Status")));
        rec.setIssueDate(rs.getDate("IssueDate").toLocalDate());
        return rec;
    };

    @Override
    public void insertIssue(IssueBook record, Connection conn) throws SQLException {
        String issueSQL = "INSERT INTO issue_records (BookID, MemberID, Status, IssueDate) VALUES (?, ?, ?, ?)";
        String logSQL = "INSERT INTO issue_records_log (BookID, MemberID, Action, ActionDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement issueStmt = conn.prepareStatement(issueSQL);
             PreparedStatement logStmt = conn.prepareStatement(logSQL)) {

            issueStmt.setInt(1, record.getBookID());
            issueStmt.setInt(2, record.getMemberID());
            issueStmt.setString(3, record.getStatus().getCode());
            issueStmt.setDate(4, Date.valueOf(record.getIssueDate()));
            issueStmt.executeUpdate();

            logStmt.setInt(1, record.getBookID());
            logStmt.setInt(2, record.getMemberID());
            logStmt.setString(3, record.getStatus().getCode());
            logStmt.setDate(4, Date.valueOf(record.getIssueDate()));
            logStmt.executeUpdate();
        }
    }

    @Override
    public void markReturnedBook(int issueID, Connection conn) throws SQLException, IssueNotFoundException {
        IssueBook record = findByID(issueID, conn);
        if (record.getStatus() != IssueStatus.ISSUED) {
            throw new IssueNotFoundException("Issue " + issueID + " is not currently active.");
        }

        insertIssueLog(record, IssueStatus.RETURNED, conn);

        String updateSQL = "UPDATE issue_records SET Status = ? WHERE IssueID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setString(1, IssueStatus.RETURNED.getCode());
            stmt.setInt(2, issueID);
            stmt.executeUpdate();
        }
    }

    @Override
    public IssueBook findByID(int issueID, Connection conn) throws SQLException, IssueNotFoundException {
        String sql = "SELECT IssueID, BookID, MemberID, Status, IssueDate FROM issue_records WHERE IssueID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, issueID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new IssueNotFoundException("No issue found with ID " + issueID);
                }
                return extractIssueBook(rs);
            }
        }
    }

    @Override
    public List<IssueBook> findAllIssues() {
        String sql = "SELECT IssueID, BookID, MemberID, Status, IssueDate FROM issue_records";
        return jdbcTemplate.query(sql, issueRowMapper);
    }

    @Override
    public List<IssueBook> findOverdueIssues() {
        List<IssueBook> allIssues = findAllIssues();
        LocalDate cutoff = LocalDate.now().minusDays(30);

        List<IssueBook> overdue = new ArrayList<>();
        for (IssueBook issue : allIssues) {
            if (issue.getStatus() == IssueStatus.ISSUED && issue.getIssueDate().isBefore(cutoff)) {
                overdue.add(issue);
            }
        }
        return overdue;
    }

    private IssueBook extractIssueBook(ResultSet rs) throws SQLException {
        IssueBook rec = new IssueBook();
        rec.setIssueID(rs.getInt("IssueID"));
        rec.setBookID(rs.getInt("BookID"));
        rec.setMemberID(rs.getInt("MemberID"));
        rec.setStatus(IssueStatus.fromCode(rs.getString("Status")));
        rec.setIssueDate(rs.getDate("IssueDate").toLocalDate());
        return rec;
    }

    private void insertIssueLog(IssueBook oldRecord, IssueStatus status, Connection conn) throws SQLException {
        String sql = "INSERT INTO issue_records_log (BookID, MemberID, Action, ActionDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, oldRecord.getBookID());
            stmt.setInt(2, oldRecord.getMemberID());
            stmt.setString(3, status.getCode());
            stmt.setDate(4, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
        }
    }
}
