package com.springbootproject.librarium365.controller;

import com.springbootproject.librarium365.model.IssueBook;
import com.springbootproject.librarium365.model.Member;
import com.springbootproject.librarium365.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	private final ReportService reportService;

	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping("/overdue-books")
	public List<IssueBook> getOverdueBooks() {
		return reportService.getOverdueBooks();
	}

	@GetMapping("/book-count-by-category")
	public List<Map<String, Object>> getBookCountByCategory() {
		Map<String, Long> rawData = reportService.getBookCountByCategory();
		List<Map<String, Object>> resultData = new ArrayList<>();
		for (Map.Entry<String, Long> entry : rawData.entrySet()) {
			Map<String, Object> map = new HashMap<>();
			map.put("category", entry.getKey());
			map.put("count", entry.getValue());
			resultData.add(map);
		}
		return resultData;
	}

	@GetMapping("/members-with-active-issues")
	public List<Member> getMembersWithActiveIssues() {
		return reportService.getMembersWithActiveIssues();
	}
}
