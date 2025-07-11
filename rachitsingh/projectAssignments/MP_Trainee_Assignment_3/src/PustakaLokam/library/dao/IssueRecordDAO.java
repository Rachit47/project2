package PustakaLokam.library.dao;

import PustakaLokam.library.model.IssueRecord;
import PustakaLokam.library.utilities.DBConnectivityUtility;
import PustakaLokam.library.exceptionhandler.IssueNotFoundException;
import PustakaLokam.library.enums.IssueStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IssueRecordDAO {

    public void insertIssue(IssueRecord record, Connection conn) throws SQLException {
        String issueSQL = "INSERT INTO issue_records (BookID, MemberID, Status, IssueDate) VALUES (?, ?, ?, ?)";
        String logSQL   = "INSERT INTO issue_records_log (BookID, MemberID, Action, ActionDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement issueStmt = conn.prepareStatement(issueSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement logStmt   = conn.prepareStatement(logSQL)) {

            issueStmt.setInt(1, record.getBookID());
            issueStmt.setInt(2, record.getMemberID());
            issueStmt.setString(3, record.getStatus().name());
            issueStmt.setDate(4, Date.valueOf(record.getIssueDate()));
            issueStmt.executeUpdate();

            logStmt.setInt(1, record.getBookID());
            logStmt.setInt(2, record.getMemberID());
            logStmt.setString(3, "ISSUED");
            logStmt.setDate(4, Date.valueOf(record.getIssueDate()));
            logStmt.executeUpdate();
        }
    }

    public void markReturnedBook(int issueID, Connection conn) throws SQLException, IssueNotFoundException {
        IssueRecord record = findByID(issueID, conn);
        if (record.getStatus() != IssueStatus.I) {
            throw new IssueNotFoundException("Issue " + issueID + " is not currently active.");
        }

        String updateSQL = "UPDATE issue_records SET Status = ? WHERE IssueID = ?";
        String logSQL = "INSERT INTO issue_records_log (BookID, MemberID, Action, ActionDate) VALUES (?, ?, ?, ?)";
        LocalDate now = LocalDate.now();

        try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
             PreparedStatement logStmt = conn.prepareStatement(logSQL)) {

            updateStmt.setString(1, IssueStatus.R.name());
            updateStmt.setInt(2, issueID);
            updateStmt.executeUpdate();

            logStmt.setInt(1, record.getBookID());
            logStmt.setInt(2, record.getMemberID());
            logStmt.setString(3, "RETURNED");
            logStmt.setDate(4, java.sql.Date.valueOf(now));
            logStmt.executeUpdate();
        }
    }

    public IssueRecord findByID(int issueID, Connection conn) throws SQLException, IssueNotFoundException {
        String sql = "SELECT IssueID, BookID, MemberID, Status, IssueDate FROM issue_records WHERE IssueID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, issueID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new IssueNotFoundException("No issue found with ID " + issueID);
                }
                return extractIssueRecord(rs);
            }
        }
    }

    public List<IssueRecord> findAllIssues() {
        List<IssueRecord> records = new ArrayList<>();
        String sql = "SELECT IssueID, BookID, MemberID, Status, IssueDate FROM issue_records";

        try (Connection conn = DBConnectivityUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                records.add(extractIssueRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return records;
    }

    public List<IssueRecord> findOverdueIssues() {
        List<IssueRecord> overdue = new ArrayList<>();
        LocalDate cutoff = LocalDate.now().minusDays(30);

        for (IssueRecord rec : findAllIssues()) {
            if (rec.getStatus() == IssueStatus.I && rec.getIssueDate().isBefore(cutoff)) {
                overdue.add(rec);
            }
        }
        return overdue;
    }

    private IssueRecord extractIssueRecord(ResultSet rs) throws SQLException {
        IssueRecord rec = new IssueRecord();
        rec.setIssueID(rs.getInt("IssueID"));
        rec.setBookID(rs.getInt("BookID"));
        rec.setMemberID(rs.getInt("MemberID"));
        rec.setStatus(IssueStatus.valueOf(rs.getString("Status")));
        rec.setIssueDate(rs.getDate("IssueDate").toLocalDate());
        return rec;
    }
}
