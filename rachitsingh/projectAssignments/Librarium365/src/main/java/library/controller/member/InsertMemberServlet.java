package library.controller.member;

import library.model.Member;
import library.service.MemberService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/InsertMemberServlet")
public class InsertMemberServlet extends HttpServlet {

	private MemberService memberService;

	@Override
	public void init() throws ServletException {
		memberService = new MemberService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		long mobile = Long.parseLong(request.getParameter("mobile"));
		char gender = request.getParameter("gender").charAt(0);
		String address = request.getParameter("address");

		Member member = new Member();
		member.setName(name);
		member.setEmail(email);
		member.setMobile(mobile);
		member.setGender(gender);
		member.setAddress(address);

		try {
			memberService.addMember(member);
			request.setAttribute("submissionSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("submissionSuccess", false);
		}

		response.sendRedirect(request.getContextPath() + "/manageMembers");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/ui_pages/member/addMember.jsp").forward(request, response);
	}
}
