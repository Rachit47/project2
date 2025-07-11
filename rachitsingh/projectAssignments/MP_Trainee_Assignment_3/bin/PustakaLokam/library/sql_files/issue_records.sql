USE library_db;
CREATE TABLE issue_records (
    IssueID INT PRIMARY KEY AUTO_INCREMENT,
    BookID INT NOT NULL,
    MemberID INT NOT NULL,
    Status ENUM('I', 'R') NOT NULL, -- I = Issued, R = Returned
    IssueDate DATE NOT NULL,
    FOREIGN KEY (BookID) REFERENCES books(BookID),
    FOREIGN KEY (MemberID) REFERENCES members(MemberID)
);
CREATE TABLE issue_records_log (
    LogID INT PRIMARY KEY AUTO_INCREMENT,
    BookID INT NOT NULL,
    MemberID INT NOT NULL,
    Action ENUM('ISSUED', 'RETURNED') NOT NULL,
    ActionDate DATE NOT NULL,
    FOREIGN KEY (BookID) REFERENCES books(BookID),
    FOREIGN KEY (MemberID) REFERENCES members(MemberID)
);
