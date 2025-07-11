package com.springbootproject.librarium365.repository.impl;

import com.springbootproject.librarium365.enums.AvailabilityStatus;
import com.springbootproject.librarium365.enums.BookCondition;
import com.springbootproject.librarium365.exceptions.BookNotFoundException;
import com.springbootproject.librarium365.exceptions.DatabaseOperationException;
import com.springbootproject.librarium365.model.Book;
import com.springbootproject.librarium365.repository.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static class BookRowMapper implements RowMapper<Book> {
		@Override
		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			Book book = new Book();
			book.setBookID(rs.getInt("bookID"));
			book.setTitle(rs.getString("Title"));
			book.setAuthor(rs.getString("Author"));
			book.setCategory(rs.getString("Category"));
			String status = rs.getString("Status");
			book.setCondition("A".equalsIgnoreCase(status) ? BookCondition.ACTIVE : BookCondition.INACTIVE);
			char availability = rs.getString("Availability").charAt(0);
			book.setAvailability(availability == 'A' ? AvailabilityStatus.AVAILABLE : AvailabilityStatus.ISSUED);
			return book;
		}
	}

	@Override
	public boolean insertBook(Book book) {
		String sql = "INSERT INTO books (Title, Author, Category, Status, Availability) VALUES (?, ?, ?, ?, ?)";
		int result = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getCategory(),
				book.getCondition().getCode(), book.getAvailability() == AvailabilityStatus.AVAILABLE ? "A" : "I");
		return result > 0;
	}

	@Override
	public boolean updateBookDetails(Book book) throws BookNotFoundException {
		String sql = "UPDATE books SET Title = ?, Author = ?, Category = ?, Status = ?, Availability = ? WHERE bookID = ?";

		Book oldBook = getBookByID(book.getBookID());
		if (oldBook == null) {
			throw new BookNotFoundException("Book with ID " + book.getBookID() + " not found.");
		}

		try {
			// Log inside the same transaction
			insertBookLog(oldBook, null);
		} catch (Exception e) {
			System.err.println("⚠️ Logging failed for Book ID " + book.getBookID());
		}

		try {
			int result = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getCategory(),
					book.getCondition().getCode(), book.getAvailability() == AvailabilityStatus.AVAILABLE ? "A" : "I",
					book.getBookID());
			return result > 0;
		} catch (Exception e) {
			throw new DatabaseOperationException("Failed to update book with ID " + book.getBookID(), e);
		}
	}

	@Override
	public List<Book> getAllBooks() {
		String sql = "SELECT * FROM books";
		return jdbcTemplate.query(sql, new BookRowMapper());
	}

	@Override
	public Book getBookByID(Integer bookID) {
		String sql = "SELECT * FROM books WHERE bookID = ?";
		List<Book> books = jdbcTemplate.query(sql, new BookRowMapper(), bookID);
		return books.isEmpty() ? null : books.get(0);
	}

	@Override
	public boolean deleteBookByID(Integer bookID) {
		String sql = "DELETE FROM books WHERE bookID = ?";
		return jdbcTemplate.update(sql, bookID) > 0;
	}

	@Override
	public boolean insertBatchOfBooks(List<Book> books) {
		String sql = "INSERT INTO books (Title, Author, Category, Status, Availability) VALUES (?, ?, ?, ?, ?)";

		int[] result = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Book book = books.get(i);
				ps.setString(1, book.getTitle());
				ps.setString(2, book.getAuthor());
				ps.setString(3, book.getCategory());
				ps.setString(4, book.getCondition().getCode());
				ps.setString(5, book.getAvailability() == AvailabilityStatus.AVAILABLE ? "A" : "I");
			}

			@Override
			public int getBatchSize() {
				return books.size();
			}
		});

		for (int r : result) {
			if (r == 0)
				return false;
		}
		return true;
	}

	@Override
	public void insertBookLog(Book book, Connection conn) {
		String sql = "INSERT INTO books_log (bookID, Title, Author, Category, Status, Availability, Timestamp) "
				+ "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
		jdbcTemplate.update(sql, book.getBookID(), book.getTitle(), book.getAuthor(), book.getCategory(),
				book.getCondition().getCode(), book.getAvailability() == AvailabilityStatus.AVAILABLE ? "A" : "I");
	}

	@Override
	public void updateBookAvailability(int bookID, AvailabilityStatus status, Connection conn) {
		String sql = "UPDATE books SET Availability = ? WHERE bookID = ?";
		int rows = jdbcTemplate.update(sql, status == AvailabilityStatus.AVAILABLE ? "A" : "I", bookID);
		if (rows == 0) {
			throw new RuntimeException("No book found with ID " + bookID + " to update availability.");
		}
	}
}
