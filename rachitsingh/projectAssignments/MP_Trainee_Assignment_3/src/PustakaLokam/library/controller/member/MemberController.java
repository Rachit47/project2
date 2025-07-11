package PustakaLokam.library.controller.member;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import PustakaLokam.library.model.Member;
import PustakaLokam.library.service.MemberService;
import PustakaLokam.library.utilities.Validation;
import PustakaLokam.library.exceptionhandler.SearchDataNotFoundException;


import java.io.IOException;
import java.lang.Long;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class MemberController {

    @FXML private TextField nameField, emailField, mobileField,searchBar;
    @FXML private ChoiceBox<String> genderBox;
    @FXML private TextArea addressField;     
    @FXML private TableView<Member> memberTable;
    @FXML private TableColumn<Member, String> nameCol, emailCol, mobileCol;
    
    private final MemberService memberService = new MemberService();
    private final Validation validator = new Validation();
    private final ObservableList<Member> memberList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        emailCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getEmail()));
        mobileCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cell.getValue().getMobile())));
        genderBox.setItems(FXCollections.observableArrayList("Male", "Female"));
        loadMembers();
    }

    protected void loadMembers() {
        try {
            memberList.setAll(memberService.getAllMembers());
            memberTable.setItems(memberList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load members from database !");
        }
    }
    public List<Member> filterByMail(String providedMail) throws SQLException {
    	List<Member> members = memberService.getAllMembers();
    	Predicate<Member> predicateMember = 
    			Member -> Member.getEmail().equals(providedMail);
    	return members.stream()
    	.filter(predicateMember).
    	collect(Collectors.toList());
    }

    @FXML
    private void handleAddMember() {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String mobileText = mobileField.getText();
            String genderStr = genderBox.getValue();
            String address = addressField.getText();

            if (name.isEmpty() || email.isEmpty() || mobileText.isEmpty() || genderStr == null || address.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            long mobile = Long.parseLong(mobileText);
            char gender = genderStr.charAt(0);

            Member m = new Member(0, name, email, mobile, gender, address);
            validator.validate(m);
            memberService.addMember(m);

            loadMembers();
            clearFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Add Error", "Mobile number must be numeric.");
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Add Error", ex.getMessage());
        }
    }

    @FXML
    private void handleDeleteMember() {
        Member selected = memberTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                memberService.removeMember(selected);
                memberList.remove(selected);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Delete Error", e.getMessage());
            }
        }
    }
    @FXML
    private void handleUpdateMember() {
        Member selected = memberTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PustakaLokam/library/ui/member/UpdateMemberUI.fxml"));
                Parent root = loader.load();

                UpdateMemberController controller = loader.getController();
                controller.setMemberData(selected, this); 

                Stage stage = new Stage();
                stage.setTitle("Update Member");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a member to update.");
        }
    }
    @FXML
    private void handleSearchBar() {
    	try {
    		String providedEmail = searchBar.getText();
        	memberList.setAll(filterByMail(providedEmail));
            memberTable.setItems(memberList);
            if(memberList.isEmpty()) {
            	throw new SearchDataNotFoundException(providedEmail);
            }
    	}catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",e.getMessage());
    	}
    	
    }
    @FXML
    private void handleClear() {
    	loadMembers();
    	searchBar.clear();
    }


    private void clearFields() {
        nameField.clear(); emailField.clear(); mobileField.clear(); genderBox.setValue(null); addressField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}