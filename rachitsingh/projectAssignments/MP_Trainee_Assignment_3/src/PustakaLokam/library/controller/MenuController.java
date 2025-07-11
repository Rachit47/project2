package PustakaLokam.library.controller;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class MenuController {

    @FXML
    private void openMemberModule() {
        loadWindow("/PustakaLokam/library/ui/member/MemberUI.fxml", "Member Management");
    }

    @FXML
    private void openIssueModule() {
        loadWindow("/PustakaLokam/library/ui/issue_book.fxml", "Issue Book");
    }

    @FXML
    private void openDeleteBookWindow() {
        loadWindow("/PustakaLokam/library/ui/book/delete_book.fxml", "Delete Book");
    }

    @FXML
    private void onInsertBookClick(ActionEvent event) {
        loadWindow("/PustakaLokam/library/ui/book/insert_book.fxml", "Add a New Book to the Library");
    }

    @FXML
    private void onUpdateBookClick(ActionEvent event) {
        loadWindow("/PustakaLokam/library/ui/book/book_list.fxml", "Select a Book to Update");
    }

    @FXML
    private void onDisplayBooksClick(ActionEvent event) {
        loadWindow("/PustakaLokam/library/ui/book/display_books.fxml", "All Registered Books of the Library");
    }
    @FXML
    public void openOverdueBooksReport() {
        loadWindow("/PustakaLokam/library/ui/report/OverdueBooksReport.fxml", "Overdue Books Report");
    }
    @FXML
    public void openBooksPerCategoryReport() {
        loadWindow("/PustakaLokam/library/ui/report/BooksPerCategoryReport.fxml", "Books Per Category");
    }
    @FXML
    public void openMembersWithActiveIssuesReport() {
        loadWindow("/PustakaLokam/library/ui/report/MembersWithActiveIssuesReport.fxml", "Members With Active Issues");
    }

    private void loadWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading FXML at: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
