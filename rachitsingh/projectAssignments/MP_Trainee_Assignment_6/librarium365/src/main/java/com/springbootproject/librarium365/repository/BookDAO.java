package com.springbootproject.librarium365.repository;

import com.springbootproject.librarium365.enums.AvailabilityStatus;
import com.springbootproject.librarium365.exceptions.BookNotFoundException;
import com.springbootproject.librarium365.model.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookDAO {

    boolean insertBook(Book book) throws SQLException;

    boolean updateBookDetails(Book book) throws BookNotFoundException;

    List<Book> getAllBooks();

    Book getBookByID(Integer bookID) throws SQLException;

    boolean deleteBookByID(Integer bookID);

    boolean insertBatchOfBooks(List<Book> books);

    void insertBookLog(Book book, Connection conn) throws SQLException;

    void updateBookAvailability(int bookID, AvailabilityStatus status, Connection conn) throws SQLException;
}
