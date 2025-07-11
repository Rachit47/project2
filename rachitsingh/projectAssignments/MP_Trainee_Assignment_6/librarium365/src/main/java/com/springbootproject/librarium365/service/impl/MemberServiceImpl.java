package com.springbootproject.librarium365.service.impl;

import com.springbootproject.librarium365.model.Member;
import com.springbootproject.librarium365.repository.MemberDAO;
import com.springbootproject.librarium365.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

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
        Predicate<Member> emailFilter = member -> member.getEmail().equals(providedMail);
        return members.stream()
                .filter(emailFilter)
                .collect(Collectors.toList());
    }
}
