package com.springbootproject.librarium365.controller;

import com.springbootproject.librarium365.exceptions.BookNotFoundException;
import com.springbootproject.librarium365.exceptions.IssueNotFoundException;
import com.springbootproject.librarium365.exceptions.IssueOperationException;
import com.springbootproject.librarium365.exceptions.MemberNotFoundException;
import com.springbootproject.librarium365.model.IssueBook;
import com.springbootproject.librarium365.service.impl.IssueBookServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
public class IssueBookController {

    private final IssueBookServiceImpl issueBookService;

    @Autowired
    public IssueBookController(IssueBookServiceImpl issueBookService) {
        this.issueBookService = issueBookService;
    }

    @PostMapping("/issue")
    public String issueBook(@Valid @RequestBody IssueBook issueBook) {
        try {
            issueBookService.issueBook(issueBook.getBookID(), issueBook.getMemberID());
            return "Issued";
        } catch (BookNotFoundException | MemberNotFoundException | IssueOperationException e) {
            return "Failed";
        }
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam int issueID) {
        try {
            issueBookService.returnBook(issueID);
            return "Returned";
        } catch (IssueNotFoundException | IssueOperationException e) {
            return "Failed";
        }
    }
}
