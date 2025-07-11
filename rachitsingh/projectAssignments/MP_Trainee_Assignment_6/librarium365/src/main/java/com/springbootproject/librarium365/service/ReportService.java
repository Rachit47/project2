package com.springbootproject.librarium365.service;

import com.springbootproject.librarium365.model.IssueBook;
import com.springbootproject.librarium365.model.Member;

import java.util.List;
import java.util.Map;

public interface ReportService {
    List<IssueBook> getOverdueBooks();
    Map<String, Long> getBookCountByCategory();
    List<Member> getMembersWithActiveIssues();
}

