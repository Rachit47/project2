package library.controller.book;

import library.service.BookService;
import library.model.Book;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {

    private static final long serialVersionUID = -2678159059431679447L;
	private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookIdParam = request.getParameter("bookId");

        if (bookIdParam != null && !bookIdParam.isEmpty()) {
            int bookId = Integer.parseInt(bookIdParam);

            try {
                bookService.deleteBook(bookId);
                request.setAttribute("deleteSuccess", true);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("deleteSuccess", false);
            }
        }

        List<Book> books = bookService.fetchAllBooks();
        request.setAttribute("books", books);
        response.sendRedirect(request.getContextPath() + "/manageBooks");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = bookService.fetchAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/ui_pages/book/confirmDeleteBook.jsp").forward(request, response);
    }
}
