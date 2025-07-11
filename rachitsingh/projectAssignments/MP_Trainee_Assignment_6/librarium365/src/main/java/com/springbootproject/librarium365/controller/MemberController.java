package com.springbootproject.librarium365.controller;

import com.springbootproject.librarium365.model.Member;
import com.springbootproject.librarium365.service.MemberService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping
	public List<Member> getAllMembers() throws SQLException {
		return memberService.getAllMembers();
	}

	@PostMapping
	public String addMember(@Valid @RequestBody Member member) {
		try {
			memberService.addMember(member);
			return "Added";
		} catch (Exception e) {
			return "Failed";
		}
	}

	@GetMapping("/{id}")
	public Member getMemberById(@PathVariable int id) throws SQLException {
		return memberService.getMemberById(id);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateMember(@RequestBody Member member) {
	    try {
	        memberService.updateMember(member);
	        return ResponseEntity.ok("Member updated");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Update failed");
	    }
	}


	@DeleteMapping("/{id}")
	public String deleteMember(@PathVariable int id) {
		try {
			Member member = memberService.getMemberById(id);
			if (member != null) {
				memberService.removeMember(member);
				return "Deleted";
			}
		} catch (Exception ignored) {
		}
		return "Failed";
	}
}
