package library.controller.book;
import library.model.Book;
import library.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class BookListServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private BookService bookService;
	
	public void init() throws ServletException {
		bookService = new BookService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> bookList = bookService.fetchAllBooks();
		request.setAttribute("books", bookList);
		
		request.getRequestDispatcher("/ui_pages/book/listAllBooks.jsp").forward(request, response);
	}
}
