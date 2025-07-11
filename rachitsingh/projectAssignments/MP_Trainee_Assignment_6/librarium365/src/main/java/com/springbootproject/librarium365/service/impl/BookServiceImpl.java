package com.springbootproject.librarium365.service.impl;

import com.springbootproject.librarium365.exceptions.BookNotFoundException;
import com.springbootproject.librarium365.exceptions.DatabaseOperationException;
import com.springbootproject.librarium365.exceptions.InvalidBookDataException;
import com.springbootproject.librarium365.model.Book;
import com.springbootproject.librarium365.repository.BookDAO;
import com.springbootproject.librarium365.service.BookService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	@Autowired
    private BookDAO bookDAO;

    @Override
    public boolean insertNewBooks(List<Book> booksToBeInserted) {
        if (booksToBeInserted == null || booksToBeInserted.isEmpty()) {
            throw new InvalidBookDataException("No books are there to put in the library.");
        }

        for (var book : booksToBeInserted) {
            if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
                throw new InvalidBookDataException("Book title not provided.");
            }
            if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
                throw new InvalidBookDataException("Book Author not provided.");
            }
            if (book.getCategory() == null || book.getCategory().trim().isEmpty()) {
                throw new InvalidBookDataException("Book Category not provided.");
            }
        }

        return bookDAO.insertBatchOfBooks(booksToBeInserted);
    }

    @Override
    public boolean updateBookDetails(Book book) throws BookNotFoundException, DatabaseOperationException {
        return bookDAO.updateBookDetails(book);
    }

    @Override
    public Book getBookByID(int bookID) throws BookNotFoundException, DatabaseOperationException {
        try {
            Book book = bookDAO.getBookByID(bookID);
            if (book == null) {
                throw new BookNotFoundException("No book found with ID: " + bookID);
            }
            return book;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch book with ID: " + bookID, e);
        }
    }

    @Override
    public List<Book> fetchAllBooks() {
        return bookDAO.getAllBooks();
    }

    @Override
    public boolean deleteBook(int bookID) {
        return bookDAO.deleteBookByID(bookID);
    }
}