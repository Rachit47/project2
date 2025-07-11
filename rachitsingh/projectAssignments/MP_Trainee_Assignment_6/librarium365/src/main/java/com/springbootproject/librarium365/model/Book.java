package com.springbootproject.librarium365.model;

import com.springbootproject.librarium365.enums.AvailabilityStatus;
import com.springbootproject.librarium365.enums.BookCondition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Book {

	private Integer bookID;

	@NotBlank(message = "Title is required")
	private String title;
	
	@NotBlank(message = "Author is required")
	private String author;
	
	@NotBlank(message = "Category is required")
	private String category;
	
	@NotNull(message = "Condition is required")
	private BookCondition condition = BookCondition.ACTIVE;
	
	@NotNull(message = "Availability is required")
	private AvailabilityStatus availability = AvailabilityStatus.AVAILABLE;

	public Book() {
	}

	public Book(Integer bookID, String title, String author, String category, BookCondition condition,
			AvailabilityStatus availability) {
		this.bookID = bookID;
		this.title = title;
		this.author = author;
		this.category = category;
		this.condition = condition != null ? condition : BookCondition.ACTIVE;
		this.availability = availability != null ? availability : AvailabilityStatus.AVAILABLE;
	}

	public Book(String title, String author, String category, BookCondition condition,
			AvailabilityStatus availability) {
		this(null, title, author, category, condition, availability);
	}


	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title != null ? title.trim() : "";
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author != null ? author.trim() : "";
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category != null ? category.trim() : "";
	}

	public BookCondition getCondition() {
		return condition;
	}

	public void setCondition(BookCondition condition) {
		this.condition = condition != null ? condition : BookCondition.ACTIVE;
	}

	public AvailabilityStatus getAvailability() {
		return availability;
	}

	public void setAvailability(AvailabilityStatus availability) {
		this.availability = availability != null ? availability : AvailabilityStatus.AVAILABLE;
	}

	@Override
	public String toString() {
		return "Book{" + "bookID=" + bookID + ", title='" + title + '\'' + ", author='" + author + '\'' + ", category='"
				+ category + '\'' + ", condition=" + condition + ", availability=" + availability + '}';
	}
}
