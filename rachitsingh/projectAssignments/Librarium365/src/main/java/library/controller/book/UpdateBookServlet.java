package library.controller.book;

import library.enums.AvailabilityStatus;
import library.enums.BookCondition;
import library.model.Book;
import library.service.BookService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/updateBook")
public class UpdateBookServlet extends HttpServlet {

    private static final long serialVersionUID = -6377018956669383023L;
	private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = bookService.fetchAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/ui_pages/book/updateBook.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            Book selectedBook = bookService.getBookByID(bookId);
            List<Book> books = bookService.fetchAllBooks();

            request.setAttribute("books", books);
            request.setAttribute("selectedBook", selectedBook);
        } else if ("update".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String category = request.getParameter("category");

            char statusChar = request.getParameter("status").charAt(0);
            AvailabilityStatus status = statusChar == 'A' ? AvailabilityStatus.AVAILABLE : AvailabilityStatus.ISSUED;

            char conditionChar = request.getParameter("condition").charAt(0);
            BookCondition condition = conditionChar == 'A' ? BookCondition.ACTIVE : BookCondition.INACTIVE;

            Book updatedBook = new Book(bookId, title, author, category, condition, status);
            bookService.updateBookDetails(updatedBook);

            request.setAttribute("updateSuccess", true);
        }

        request.setAttribute("books", bookService.fetchAllBooks());

        request.getRequestDispatcher("/ui_pages/book/manageBooks.jsp").forward(request, response);
    }
}
