package library.controller.member;

import library.model.Member;
import library.service.MemberService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/DeleteMemberServlet")
public class DeleteMemberServlet extends HttpServlet {

    private MemberService memberService;

    @Override
    public void init() throws ServletException {
        memberService = new MemberService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("memberId");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int memberId = Integer.parseInt(idParam);
                Member member = memberService.getMemberById(memberId);

                if (member != null) {
                    memberService.removeMember(member);
                    request.setAttribute("deleteSuccess", true);
                } else {
                    request.setAttribute("deleteSuccess", false);
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("deleteSuccess", false);
            }
        }


        try {
            List<Member> updatedList = memberService.getAllMembers();
            request.setAttribute("members", updatedList);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("members", null);
        }

        response.sendRedirect(request.getContextPath() + "/manageMembers");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Member> members = memberService.getAllMembers();
            request.setAttribute("members", members);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("members", null);
        }

        request.getRequestDispatcher("/ui_pages/member/confirmDeleteMember.jsp").forward(request, response);
    }
}
