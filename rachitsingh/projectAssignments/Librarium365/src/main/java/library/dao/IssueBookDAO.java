package library.dao;

import library.model.IssueBook;
import library.utilities.DBConnectivityUtility;
import library.exceptionhandler.IssueNotFoundException;
import library.enums.IssueStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IssueBookDAO implements IssueBookDAOInterface{

	@Override
	public void insertIssue(IssueBook record, Connection conn) throws SQLException {
		String issueSQL = "INSERT INTO issue_records (BookID, MemberID, Status, IssueDate) VALUES (?, ?, ?, ?)";
		String logSQL = "INSERT INTO issue_records_log (BookID, MemberID, Action, ActionDate) VALUES (?, ?, ?, ?)";

		try (PreparedStatement issueStmt = conn.prepareStatement(issueSQL, Statement.RETURN_GENERATED_KEYS);
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

	    try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
	        updateStmt.setString(1, IssueStatus.RETURNED.getCode());
	        updateStmt.setInt(2, issueID);
	        updateStmt.executeUpdate();
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
		List<IssueBook> records = new ArrayList<>();
		String sql = "SELECT IssueID, BookID, MemberID, Status, IssueDate FROM issue_records";

		try (Connection conn = DBConnectivityUtility.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				records.add(extractIssueBook(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return records;
	}
	
	@Override
	public List<IssueBook> findOverdueIssues() {
		List<IssueBook> overdue = new ArrayList<>();
		LocalDate cutoff = LocalDate.now().minusDays(30);

		for (IssueBook rec : findAllIssues()) {
			if (rec.getStatus() == IssueStatus.ISSUED && rec.getIssueDate().isBefore(cutoff)) {
				overdue.add(rec);
			}
		}
		return overdue;
	}
	
	
	private IssueBook extractIssueBook(ResultSet rs) throws SQLException {
		IssueBook rec = new IssueBook();
		rec.setIssueID(rs.getInt("IssueID"));
		rec.setBookID(rs.getInt("BookID"));
		rec.setMemberID(rs.getInt("MemberID"));

		String statusCode = rs.getString("Status");
		rec.setStatus(IssueStatus.fromCode(statusCode));

		rec.setIssueDate(rs.getDate("IssueDate").toLocalDate());
		return rec;
	}
	
	
	private void insertIssueLog(IssueBook oldRecord, IssueStatus actionStatus, Connection conn) throws SQLException {
	    String logSQL = "INSERT INTO issue_records_log (BookID, MemberID, Action, ActionDate) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement logStmt = conn.prepareStatement(logSQL)) {
	        logStmt.setInt(1, oldRecord.getBookID());
	        logStmt.setInt(2, oldRecord.getMemberID());
	        logStmt.setString(3, actionStatus.getCode());
	        logStmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
	        logStmt.executeUpdate();
	    }
	}

}