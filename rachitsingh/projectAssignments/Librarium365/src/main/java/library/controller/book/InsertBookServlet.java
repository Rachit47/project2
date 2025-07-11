package library.controller.book;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import library.enums.AvailabilityStatus;
import library.enums.BookCondition;
import library.model.Book;
import library.service.BookService;

@WebServlet("/InsertBookServlet")
public class InsertBookServlet extends HttpServlet {
	private static final long serialVersionUID = 6351656558437994629L;
	private BookService bookService;

	@Override
	public void init() {
		bookService = new BookService();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		List<Book> batchedBooks = (List<Book>) session.getAttribute("batchedBooks");
		if (batchedBooks == null) {
			batchedBooks = new ArrayList<>();
		}

		if ("add".equals(action)) {
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String category = request.getParameter("category");

			char availabilityChar = request.getParameter("status").charAt(0);
			AvailabilityStatus status = availabilityChar == 'A' ? AvailabilityStatus.AVAILABLE
					: AvailabilityStatus.ISSUED;

			char conditionChar = request.getParameter("condition").charAt(0);
			BookCondition condition = conditionChar == 'A' ? BookCondition.ACTIVE : BookCondition.INACTIVE;

			Book book = new Book(title, author, category, condition, status);
			batchedBooks.add(book);

			session.setAttribute("batchedBooks", batchedBooks);
		} else if ("submit".equals(action)) {
			if (!batchedBooks.isEmpty()) {
				try {
					bookService.insertNewBooks(batchedBooks);
					session.removeAttribute("batchedBooks");
					request.setAttribute("submissionSuccess", true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("books", bookService.fetchAllBooks());
		request.getRequestDispatcher("/ui_pages/book/manageBooks.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("books", bookService.fetchAllBooks());
		request.getRequestDispatcher("/ui_pages/book/manageBooks.jsp").forward(request, response);
	}
}
