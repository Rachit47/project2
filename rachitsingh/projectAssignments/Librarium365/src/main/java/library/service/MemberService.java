package library.service;
import java.util.Collections;
import library.dao.MemberDAO;
import library.model.Member;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MemberService implements MemberServiceInterface{
    private final MemberDAO memberDAO = new MemberDAO();
    
    @Override
    public void addMember(Member member) throws SQLException {
        memberDAO.addMember(member);
    }
    
    @Override
    public void removeMember(Member member) throws SQLException {
        memberDAO.removeMember(member);
    }
    
    @Override
    public void updateMember(Member member) throws SQLException {
        memberDAO.updateMember(member);
    }
    
    @Override
    public Member getMemberById(int memberId) throws SQLException {
        List<Integer> idList = Collections.singletonList(memberId);  
        List<Member> members = memberDAO.getMemberByID(idList);      
        return members.isEmpty() ? null : members.get(0);
    }
    
    @Override
    public List<Member> getAllMembers() throws SQLException {
        return memberDAO.getAllMembers();
    }
    
    @Override
    public List<Member> filterByMail(String providedMail) throws SQLException {
    	List<Member> members = getAllMembers();
    	Predicate<Member> predicateMember = 
    			Member -> Member.getEmail().equals(providedMail);
    	return members.stream()
    	.filter(predicateMember).
    	collect(Collectors.toList());
    }
}
