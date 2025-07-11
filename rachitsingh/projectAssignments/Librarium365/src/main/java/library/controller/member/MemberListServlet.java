package library.controller.member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import library.model.Member;
import library.service.MemberService;

@WebServlet("/members")
public class MemberListServlet extends HttpServlet{
	
	private static final long serialVersionUID = 3721596784853220052L;
	private MemberService memberService;
	
	public void init() throws ServletException {
		memberService = new MemberService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Member> memberList = null;
		try {
			memberList = memberService.getAllMembers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("members", memberList);
		
		request.getRequestDispatcher("/ui_pages/member/listAllMembers.jsp").forward(request, response);
	}
}
