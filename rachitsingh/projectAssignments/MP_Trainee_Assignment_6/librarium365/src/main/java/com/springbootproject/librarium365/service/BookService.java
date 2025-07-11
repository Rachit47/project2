package com.springbootproject.librarium365.service;

import com.springbootproject.librarium365.exceptions.BookNotFoundException;
import com.springbootproject.librarium365.exceptions.DatabaseOperationException;
import com.springbootproject.librarium365.exceptions.InvalidBookDataException;
import com.springbootproject.librarium365.model.Book;

import java.util.List;

public interface BookService {

    boolean insertNewBooks(List<Book> booksToBeInserted) throws InvalidBookDataException;

    boolean updateBookDetails(Book book) throws BookNotFoundException, DatabaseOperationException;

    Book getBookByID(int bookID) throws BookNotFoundException, DatabaseOperationException;

    List<Book> fetchAllBooks();

    boolean deleteBook(int bookID);
}
