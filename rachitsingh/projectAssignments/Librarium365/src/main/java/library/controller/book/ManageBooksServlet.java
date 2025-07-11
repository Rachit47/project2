package library.controller.book;

import library.model.Book;
import library.service.BookService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/manageBooks")
public class ManageBooksServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> books = bookService.fetchAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/ui_pages/book/manageBooks.jsp").forward(request, response);
    }
}
