package library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import library.model.Member;
import library.utilities.DBConnectivityUtility;

public class MemberDAO implements MemberDAOInterface{
	
	@Override
    public void addMember(Member member) throws SQLException {
        String sql = "INSERT INTO members (Name, Email, Mobile, Gender, Address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectivityUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setLong(3, member.getMobile());
            stmt.setString(4, String.valueOf(member.getGender()));
            stmt.setString(5, member.getAddress());
            stmt.executeUpdate();
        }
    }
	
	@Override
    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection conn = DBConnectivityUtility.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(new Member(
                    rs.getInt("MemberId"),
                    rs.getString("Name"),
                    rs.getString("Email"),
                    rs.getLong("Mobile"),
                    rs.getString("Gender").charAt(0),
                    rs.getString("Address")
                ));
            }
        }
        return members;
    }
    
	@Override
   public void removeMember(Member member) throws SQLException{
	   String sql = "DELETE FROM members WHERE memberid = ?";
	   try (Connection conn = DBConnectivityUtility.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)) {
		   stmt.setInt(1, member.getMemberId());
		   stmt.executeUpdate();
	   	 }
	   }
	
	@Override
   public void updateMember(Member member) throws SQLException{
	   String sql ="UPDATE members SET Name = ?, Email = ?, Mobile = ?, Gender = ?, Address = ? WHERE MemberId = ?";
	   try (Connection conn = DBConnectivityUtility.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
		   stmt.setString(1, member.getName());
           stmt.setString(2, member.getEmail());
           stmt.setLong(3, member.getMobile());
           stmt.setString(4, String.valueOf(member.getGender()));
           stmt.setString(5, member.getAddress());
           stmt.setInt(6, member.getMemberId());
           stmt.executeUpdate();
	   }   
   }
   
	@Override
   public List<Member> getMemberByID(List<Integer> memberIDs) throws SQLException {
	   String placeholders = memberIDs.stream()
			   .map(id -> "?")
			   .collect(Collectors.joining(", "));
	    String sql = "SELECT memberId, name, email, mobile, gender, address FROM members WHERE memberId IN (" + placeholders + ")";

	    List<Member> members = new ArrayList<>();
	    
	    try (Connection conn = DBConnectivityUtility.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	for(int i=0; i < memberIDs.size(); i++) {
		    	stmt.setInt(i+1, memberIDs.get(i));
		    }

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                Member member = new Member();
	                member.setMemberId(rs.getInt("memberId"));
	                member.setName(rs.getString("name"));
	                member.setEmail(rs.getString("email"));
	                member.setMobile(rs.getLong("mobile"));
	                member.setGender(rs.getString("gender").charAt(0));
	                member.setAddress(rs.getString("address"));
	                members.add(member);
	            }
	        }
	    }

	    return members;
	}
  }