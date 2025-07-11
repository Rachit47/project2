package PustakaLokam.library.controller.report;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import PustakaLokam.library.model.Member;
import PustakaLokam.library.service.ReportService;

import java.util.List;

public class MembersWithActiveIssuesReportController {

    @FXML
    private TableView<Member> membersTable;

    @FXML
    private TableColumn<Member, Integer> memberIdColumn;

    @FXML
    private TableColumn<Member, String> nameColumn;

    @FXML
    private TableColumn<Member, String> emailColumn;

    private final ReportService reportService = new ReportService();

    @FXML
    public void initialize() {
    	memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        List<Member> activeMembers = reportService.getMembersWithActiveIssues();
        membersTable.getItems().setAll(activeMembers);
    }
}
