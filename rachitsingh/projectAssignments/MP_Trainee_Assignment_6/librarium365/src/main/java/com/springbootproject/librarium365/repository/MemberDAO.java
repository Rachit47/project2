package com.springbootproject.librarium365.repository;

import com.springbootproject.librarium365.model.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDAO {

    void addMember(Member member) throws SQLException;

    List<Member> getAllMembers() throws SQLException;

    void removeMember(Member member) throws SQLException;

    void updateMember(Member member) throws SQLException;

    List<Member> getMemberByID(List<Integer> memberIDs) throws SQLException;
}
