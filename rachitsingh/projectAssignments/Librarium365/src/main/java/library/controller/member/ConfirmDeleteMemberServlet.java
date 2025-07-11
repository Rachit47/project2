package library.controller.member;

import library.model.Member;
import library.service.MemberService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/confirmDeleteMember")
public class ConfirmDeleteMemberServlet extends HttpServlet {

    private MemberService memberService;

    @Override
    public void init() throws ServletException {
        memberService = new MemberService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Member> members = null;
		try {
			members = memberService.getAllMembers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        request.setAttribute("members", members);

        request.getRequestDispatcher("/ui_pages/member/confirmDeleteMember.jsp").forward(request, response);
    }
}

