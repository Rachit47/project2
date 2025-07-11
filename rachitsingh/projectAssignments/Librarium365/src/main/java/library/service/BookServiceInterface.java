package library.service;

import java.util.List;

import library.exceptionhandler.BookNotFoundException;
import library.exceptionhandler.DatabaseOperationException;
import library.exceptionhandler.InvalidBookDataException;
import library.model.Book;

public interface BookServiceInterface {
	boolean insertNewBooks(List<Book> booksToBeInserted) throws InvalidBookDataException;

    boolean updateBookDetails(Book book) throws BookNotFoundException, DatabaseOperationException;

    Book getBookByID(int bookID) throws BookNotFoundException, DatabaseOperationException;

    List<Book> fetchAllBooks();

    boolean deleteBook(int bookID);
}
