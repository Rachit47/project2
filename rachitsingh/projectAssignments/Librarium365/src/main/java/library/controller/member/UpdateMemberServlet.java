package library.controller.member;

import library.model.Member;
import library.service.MemberService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/UpdateMemberServlet")
public class UpdateMemberServlet extends HttpServlet {

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

        request.getRequestDispatcher("/ui_pages/member/updateMember.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int memberId = Integer.parseInt(request.getParameter("memberId"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            long mobile = Long.parseLong(request.getParameter("mobile"));
            char gender = request.getParameter("gender").charAt(0);
            String address = request.getParameter("address");

            Member member = new Member(memberId, name, email, mobile, gender, address);
            memberService.updateMember(member);

            request.setAttribute("updateSuccess", true);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("updateSuccess", false);
        }

        try {
            List<Member> members = memberService.getAllMembers();
            request.setAttribute("members", members);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("members", null);
        }

        response.sendRedirect(request.getContextPath() + "/manageMembers");
    }
}
