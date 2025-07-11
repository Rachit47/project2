package library.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import library.enums.AvailabilityStatus;
import library.enums.BookCondition;
import library.exceptionhandler.BookNotFoundException;
import library.exceptionhandler.DatabaseOperationException;
import library.model.Book;
import library.utilities.DBConnectivityUtility;

public class BookDAO implements BookDAOInterface{
	
	@Override
    public boolean insertBook(Book book) throws SQLException {
        String sqlQuery = "INSERT INTO books (Title, Author, Category, Status, Availability) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectivityUtility.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlQuery)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setString(4, book.getCondition().getCode());
            statement.setString(5, book.getAvailability() == AvailabilityStatus.AVAILABLE ? "A" : "I");

            return statement.executeUpdate() > 0;
        }
    }
	
	@Override
    public boolean updateBookDetails(Book book) throws BookNotFoundException {
        String updateQuery = "UPDATE books SET Title = ?, Author = ?, Category = ?, Status = ?, Availability = ? WHERE bookID = ?";

        try (Connection conn = DBConnectivityUtility.getConnection()) {

            Book oldBook = getBookByID(book.getBookID());
            if (oldBook == null) {
                throw new BookNotFoundException("Book with ID " + book.getBookID() + " not found for update.");
            }

            try {
                insertBookLog(oldBook, conn);
            } catch (SQLException logEx) {
                System.err.println("Warning: Failed to log previous book version for Book ID " + book.getBookID());
                logEx.printStackTrace();
            }

            try (PreparedStatement statement = conn.prepareStatement(updateQuery)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());
                statement.setString(3, book.getCategory());
                statement.setString(4, book.getCondition().getCode());
                statement.setString(5, book.getAvailability() == AvailabilityStatus.AVAILABLE ? "A" : "I");
                statement.setObject(6, book.getBookID(), java.sql.Types.INTEGER);

                int updatedRows = statement.executeUpdate();
                if (updatedRows == 0) {
                    throw new BookNotFoundException("Book with ID " + book.getBookID() + " not found for update.");
                }

                return true;
            }

        } catch (SQLException SQLE) {
            throw new DatabaseOperationException("Failed to update book with ID " + book.getBookID(), SQLE);
        }
    }

	@Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sqlQuery = "SELECT bookID, Title, Author, Category, Status, Availability FROM books";

        try (Connection conn = DBConnectivityUtility.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlQuery);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookID(rs.getObject("bookID", Integer.class));
                book.setAuthor(rs.getString("Author"));
                book.setTitle(rs.getString("Title"));
                book.setCategory(rs.getString("Category"));

                String status = rs.getString("Status");
                book.setCondition("A".equalsIgnoreCase(status) ? BookCondition.ACTIVE : BookCondition.INACTIVE);

                char availabilityChar = rs.getString("Availability").charAt(0);
                book.setAvailability(availabilityChar == 'A' ? AvailabilityStatus.AVAILABLE : AvailabilityStatus.ISSUED);

                books.add(book);
            }
        } catch (SQLException SQLE) {
            SQLE.printStackTrace();
        }
        return books;
    }
	
	@Override
    public Book getBookByID(Integer bookID) throws SQLException {
        String sqlQuery = "SELECT * FROM books WHERE bookID = ?";
        try (Connection conn = DBConnectivityUtility.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlQuery)) {

            statement.setObject(1, bookID, java.sql.Types.INTEGER);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();

                    book.setBookID(rs.getObject("bookID", Integer.class));
                    book.setTitle(rs.getString("Title"));
                    book.setAuthor(rs.getString("Author"));
                    book.setCategory(rs.getString("Category"));

                    String status = rs.getString("Status");
                    book.setCondition("A".equalsIgnoreCase(status) ? BookCondition.ACTIVE : BookCondition.INACTIVE);

                    char availabilityChar = rs.getString("Availability").charAt(0);
                    book.setAvailability(availabilityChar == 'A' ? AvailabilityStatus.AVAILABLE : AvailabilityStatus.ISSUED);

                    return book;
                }
            }
        }
        return null;
    }
	
	@Override
    public boolean deleteBookByID(Integer bookID) {
        String sql = "DELETE FROM books WHERE bookID = ?";
        try (Connection conn = DBConnectivityUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, bookID, java.sql.Types.INTEGER);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	@Override
    public boolean insertBatchOfBooks(List<Book> books) {
        String sqlQuery = "INSERT INTO books (Title, Author, Category, Status, Availability) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = DBConnectivityUtility.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
                for (Book book : books) {
                    statement.setString(1, book.getTitle());
                    statement.setString(2, book.getAuthor());
                    statement.setString(3, book.getCategory());
                    statement.setString(4, book.getCondition().getCode());
                    statement.setString(5, book.getAvailability() == AvailabilityStatus.AVAILABLE ? "A" : "I");
                    statement.addBatch();
                }

                int[] acknowledgements = statement.executeBatch();
                
                conn.commit();

                for (int ack : acknowledgements) {
                    if (ack == Statement.EXECUTE_FAILED) {
                        return false;
                    }
                }
                return true;
            }

        } catch (SQLException SQLE) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            SQLE.printStackTrace();
            return false;
        }
    }
	
	@Override
    public void insertBookLog(Book book, Connection conn) throws SQLException {
        String query = "INSERT INTO books_log (bookID, Title, Author, Category, Status, Availability, Timestamp) " +
                       "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setObject(1, book.getBookID(), java.sql.Types.INTEGER);
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getCategory());
            statement.setString(5, book.getCondition().getCode());
            statement.setString(6, book.getAvailability() == AvailabilityStatus.AVAILABLE ? "A" : "I");
            statement.executeUpdate();
        }
    }
	
	@Override
    public void updateBookAvailability(int bookID, AvailabilityStatus status, Connection conn) throws SQLException {
        String sql = "UPDATE books SET Availability = ? WHERE bookID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status == AvailabilityStatus.AVAILABLE ? "A" : "I");

            ps.setInt(2, bookID);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No book found with ID " + bookID + " to update availability.");
            }
        }
    }
}
