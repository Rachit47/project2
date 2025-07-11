package library.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import library.enums.AvailabilityStatus;
import library.exceptionhandler.BookNotFoundException;
import library.model.Book;

public interface BookDAOInterface {

    boolean insertBook(Book book) throws SQLException;

    boolean updateBookDetails(Book book) throws BookNotFoundException;

    List<Book> getAllBooks();

    Book getBookByID(Integer bookID) throws SQLException;

    boolean deleteBookByID(Integer bookID);

    boolean insertBatchOfBooks(List<Book> books);

    void insertBookLog(Book book, Connection conn) throws SQLException;

    void updateBookAvailability(int bookID, AvailabilityStatus status, Connection conn) throws SQLException;
}
