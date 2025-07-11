package library.dao;

import library.model.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDAOInterface {

    void addMember(Member member) throws SQLException;

    List<Member> getAllMembers() throws SQLException;

    void removeMember(Member member) throws SQLException;

    void updateMember(Member member) throws SQLException;

    List<Member> getMemberByID(List<Integer> memberIDs) throws SQLException;
}
