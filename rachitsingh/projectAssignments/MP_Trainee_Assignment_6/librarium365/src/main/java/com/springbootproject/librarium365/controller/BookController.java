package com.springbootproject.librarium365.controller;

import com.springbootproject.librarium365.model.Book;
import com.springbootproject.librarium365.service.BookService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.fetchAllBooks();
	}

	@PostMapping
	public String addBook(@Valid @RequestBody Book book) {
		try {
			bookService.insertNewBooks(List.of(book));
			return "Book added";
		} catch (Exception e) {
			return "Failed";
		}
	}

	@PostMapping("/batch")
	public String addBooksBatch(@RequestBody List<Book> books) {
		try {
			bookService.insertNewBooks(books);
			return "Batch added";
		} catch (Exception e) {
			return "Failed";
		}
	}

	@GetMapping("/{id}")
	public Book getBook(@PathVariable int id) {
		return bookService.getBookByID(id);
	}

	@PutMapping("/{id}")
	public String updateBook(@PathVariable int id, @RequestBody Book book) {
		try {
			book.setBookID(id);
			bookService.updateBookDetails(book);
			return "Updated";
		} catch (Exception e) {
			return "Failed";
		}
	}

	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable int id) {
		try {
			bookService.deleteBook(id);
			return "Deleted";
		} catch (Exception e) {
			return "Failed";
		}
	}
}
