package PustakaLokam.library.service;

import PustakaLokam.library.dao.MemberDAO;
import PustakaLokam.library.model.Member;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MemberService {
    private final MemberDAO memberDAO = new MemberDAO();

    public void addMember(Member member) throws SQLException {
        memberDAO.addMember(member);
    }

    public void removeMember(Member member) throws SQLException {
        memberDAO.removeMember(member);
    }

    public void updateMember(Member member) throws SQLException {
        memberDAO.updateMember(member);
    }

    public List<Member> getAllMembers() throws SQLException {
        return memberDAO.getAllMembers();
    }
    
    public List<Member> filterByMail(String providedMail) throws SQLException {
    	List<Member> members = getAllMembers();
    	Predicate<Member> predicateMember = 
    			Member -> Member.getEmail().equals(providedMail);
    	return members.stream()
    	.filter(predicateMember).
    	collect(Collectors.toList());
    }
}