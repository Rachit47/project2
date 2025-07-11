package PustakaLokam.library.controller.report;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import PustakaLokam.library.model.IssueRecord;
import PustakaLokam.library.service.ReportService;

import java.util.List;

public class OverdueBooksReportController {
    @FXML
    private TableView<IssueRecord> overdueBooksTable;

    private final ReportService reportService = new ReportService();

    @FXML
    public void initialize() {
        List<IssueRecord> overdueBooks = reportService.getOverdueBooks();
        overdueBooksTable.getItems().addAll(overdueBooks);
    }
}
