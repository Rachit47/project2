package library.controller.report;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.exceptionhandler.BookNotFoundException;
import library.exceptionhandler.IssueNotFoundException;
import library.exceptionhandler.IssueOperationException;
import library.exceptionhandler.MemberNotFoundException;
import library.model.IssueBook;
import library.service.IssueBookService;

@WebServlet("/IssueBookServlet")
public class IssueBookServlet extends HttpServlet {

	private static final long serialVersionUID = 2968687989156854749L;
	private IssueBookService issueService;

	@Override
	public void init() {
		issueService = new IssueBookService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/ui_pages/report/issueBook.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("issue".equals(action)) {
			String bookIdString = request.getParameter("bookId");
			String memberIdString = request.getParameter("memberId");
			Integer bookIdInt = null;
			if (!bookIdString.isBlank()) {
				bookIdInt = Integer.parseInt(bookIdString);
			}
			Integer memberIdInt = null;
			if (!memberIdString.isBlank()) {
				memberIdInt = Integer.parseInt(memberIdString);
			}
			IssueBook returnedRecord = null;
			try {
				returnedRecord = issueService.issueBook(bookIdInt, memberIdInt);
			} catch (BookNotFoundException | MemberNotFoundException | IssueOperationException e) {
				e.printStackTrace();
			}
			if (returnedRecord == null) {
				request.setAttribute("issueError", "Book already issued");
			} else {
				request.setAttribute("issueSuccess", "Book issued successfully.");
			}
		}

		if ("return".equals(action)) {
			String issueIdStr = request.getParameter("issueId");
			try {
				Integer issueIdInt = Integer.parseInt(issueIdStr);
				issueService.returnBook(issueIdInt);
				request.setAttribute("returnSuccess", "Book returned successfully.");
			} catch (NumberFormatException e) {
				request.setAttribute("returnError", "Invalid issue ID.");
			} catch (IssueNotFoundException | IssueOperationException e) {
				request.setAttribute("returnError", "Error returning book: " + e.getMessage());
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("/ui_pages/report/issueBook.jsp").forward(request, response);
	}
}
