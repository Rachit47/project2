package com.springbootproject.librarium365.repository.impl;

import com.springbootproject.librarium365.model.Member;
import com.springbootproject.librarium365.repository.MemberDAO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
            rs.getInt("MemberId"),
            rs.getString("Name"),
            rs.getString("Email"),
            rs.getLong("Mobile"),
            rs.getString("Gender").charAt(0),
            rs.getString("Address")
    );

    @PostConstruct
    public void testConnection() {
        jdbcTemplate.query("SELECT 1", rs -> {
            System.out.println("MemberDAO DB connected!");
        });
    }

    @Override
    public void addMember(Member member) {
        String sql = "INSERT INTO members (Name, Email, Mobile, Gender, Address) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                member.getName(),
                member.getEmail(),
                member.getMobile(),
                String.valueOf(member.getGender()),
                member.getAddress()
        );
    }

    @Override
    public List<Member> getAllMembers() {
        String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public void removeMember(Member member) {
        String sql = "DELETE FROM members WHERE MemberId = ?";
        jdbcTemplate.update(sql, member.getMemberId());
    }

    @Override
    public void updateMember(Member member) {
        String sql = "UPDATE members SET Name = ?, Email = ?, Mobile = ?, Gender = ?, Address = ? WHERE MemberId = ?";
        jdbcTemplate.update(sql,
                member.getName(),
                member.getEmail(),
                member.getMobile(),
                String.valueOf(member.getGender()),
                member.getAddress(),
                member.getMemberId()
        );
    }

    @Override
    public List<Member> getMemberByID(List<Integer> memberIDs) {
        if (memberIDs == null || memberIDs.isEmpty()) {
            return Collections.emptyList();
        }

        String placeholders = memberIDs.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));
        String sql = "SELECT * FROM members WHERE MemberId IN (" + placeholders + ")";

        Object[] args = memberIDs.toArray();

        return jdbcTemplate.query(sql, args, memberRowMapper);
    }
}
