package PustakaLokam.library.controller;

import PustakaLokam.library.exceptionhandler.*;
import PustakaLokam.library.service.IssueRecordService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class IssueBookController {

    @FXML
    private TextField bookIdField;

    @FXML
    private TextField memberIdField;

    @FXML
    private Button issueButton;

    @FXML
    private TextField issueIdField;

    @FXML
    private Button returnButton;

    private final IssueRecordService issueService = new IssueRecordService();

    @FXML
    private void handleIssueBook() {
        String bookIdText = bookIdField.getText().trim();
        String memberIdText = memberIdField.getText().trim();

        if (bookIdText.isEmpty() || memberIdText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter both Book ID and Member ID.");
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdText);
            int memberId = Integer.parseInt(memberIdText);

            issueService.issueBook(bookId, memberId);

            showAlert(Alert.AlertType.INFORMATION, "Book has been issued successfully!");
            bookIdField.clear();
            memberIdField.clear();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Book ID and Member ID must be numeric.");
        } catch (BookNotFoundException | MemberNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        } catch (IssueOperationException e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            showAlert(Alert.AlertType.ERROR, "Failure occurred while issuing the book: " + errorMessage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void handleReturnBook() {
        String issueIdText = issueIdField.getText().trim();

        if (issueIdText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter the Issue ID to return.");
            return;
        }

        try {
            int issueId = Integer.parseInt(issueIdText);
            issueService.returnBook(issueId);
            showAlert(Alert.AlertType.INFORMATION, "Book has been returned successfully!");
            issueIdField.clear();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Issue ID must be numeric.");
        } catch (IssueNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        } catch (IssueOperationException e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            showAlert(Alert.AlertType.ERROR, "Failure occurred while returning the book: " + errorMessage);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Unexpected error occurred: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Book Transaction");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
