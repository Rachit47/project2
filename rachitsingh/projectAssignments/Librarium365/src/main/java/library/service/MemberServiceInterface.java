package library.service;

import java.sql.SQLException;
import java.util.List;

import library.model.Member;

public interface MemberServiceInterface {
	public void addMember(Member member) throws SQLException;
	
	public void removeMember(Member member) throws SQLException;
	public void updateMember(Member member) throws SQLException;
	
	public Member getMemberById(int memberId) throws SQLException;
	public List<Member> getAllMembers() throws SQLException;
	public List<Member> filterByMail(String providedMail) throws SQLException;
	
}
