package PustakaLokam.library.controller.report;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import PustakaLokam.library.service.ReportService;

import java.util.Map;

public class BooksPerCategoryReportController {
    @FXML
    private TableView<Map.Entry<String, Long>> booksPerCategoryTable;

    @FXML
    private TableColumn<Map.Entry<String, Long>, String> categoryColumn;

    @FXML
    private TableColumn<Map.Entry<String, Long>, Long> countColumn;

    private final ReportService reportService = new ReportService();

    @FXML
    public void initialize() {
        categoryColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getKey()));
        countColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getValue()).asObject());

        booksPerCategoryTable.getItems().addAll(reportService.getBookCountByCategory().entrySet());
    }
}
