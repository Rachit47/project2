package library.controller.report;

import library.model.IssueBook;
import library.model.Member;
import library.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/reportMain")
public class ReportMainServlet extends HttpServlet {

    private ReportService reportService;

    @Override
    public void init() {
        reportService = new ReportService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<IssueBook> overdueBooks = reportService.getOverdueBooks();
        Map<String, Long> categoryCounts = reportService.getBookCountByCategory();
        List<Member> membersWithActiveIssues = reportService.getMembersWithActiveIssues();

        request.setAttribute("overdueBooks", overdueBooks);
        request.setAttribute("categoryCounts", categoryCounts);
        request.setAttribute("activeMembers", membersWithActiveIssues);

        request.getRequestDispatcher("/ui_pages/report/ReportMainPage.jsp").forward(request, response);
    }
}
