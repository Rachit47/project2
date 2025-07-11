package com.springbootproject.librarium365.service;

import com.springbootproject.librarium365.model.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberService {

    void addMember(Member member) throws SQLException;

    void removeMember(Member member) throws SQLException;

    void updateMember(Member member) throws SQLException;

    Member getMemberById(int memberId) throws SQLException;

    List<Member> getAllMembers() throws SQLException;

    List<Member> filterByMail(String providedMail) throws SQLException;
}