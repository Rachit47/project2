package library.service;

import library.model.Book;
import library.dao.BookDAO;
import library.exceptionhandler.BookNotFoundException;
import library.exceptionhandler.DatabaseOperationException;
import library.exceptionhandler.InvalidBookDataException;

import java.sql.SQLException;
import java.util.List;

public class BookService implements BookServiceInterface{
    private BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }
    
    @Override
    public boolean insertNewBooks(List<Book> booksToBeInserted) {
        if (booksToBeInserted == null || booksToBeInserted.isEmpty()) {
            throw new InvalidBookDataException("No books are there to put in the library.");
        }

        for (var book: booksToBeInserted) {
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
    public boolean updateBookDetails(Book book)throws BookNotFoundException, DatabaseOperationException  {
    	return bookDAO.updateBookDetails(book);
    }
    
    @Override
    public Book getBookByID(int bookID) {
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