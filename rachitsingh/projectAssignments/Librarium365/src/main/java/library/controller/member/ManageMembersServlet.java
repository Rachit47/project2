package library.controller.member;

import library.model.Member;
import library.service.MemberService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/manageMembers")
public class ManageMembersServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MemberService memberService;

    @Override
    public void init() throws ServletException {
        memberService = new MemberService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Member> members = memberService.getAllMembers();
            request.setAttribute("members", members);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("members", null);
        }

        request.getRequestDispatcher("/ui_pages/member/manageMembers.jsp").forward(request, response);
    }
}
