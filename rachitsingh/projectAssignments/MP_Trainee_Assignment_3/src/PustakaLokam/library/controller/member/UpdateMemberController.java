package PustakaLokam.library.controller.member;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import PustakaLokam.library.model.Member;
import PustakaLokam.library.service.MemberService;
import PustakaLokam.library.utilities.Validation;

public class UpdateMemberController {

	@FXML private TextField nameField;
	@FXML private TextField emailField;
	@FXML private TextField mobileField;
	@FXML private ChoiceBox<String> genderBox;
	@FXML private TextArea addressField;

    private Member member;
    private final MemberService memberService = new MemberService();
    private final Validation validator = new Validation();
    private MemberController parentController;

    public void setMemberData(Member member, MemberController parent) {
        this.member = member;
        this.parentController = parent;

        nameField.setText(member.getName());
        emailField.setText(member.getEmail());
        mobileField.setText("" + member.getMobile());
        genderBox.getItems().setAll("M", "F");
        genderBox.setValue("" + member.getGender());
        addressField.setText(member.getAddress());
    }

    @FXML
    private void handleSave() {
        try {
            member.setName(nameField.getText());
            member.setEmail(emailField.getText());
            member.setMobile(Long.parseLong(mobileField.getText()));
            member.setGender(genderBox.getValue().charAt(0));
            member.setAddress(addressField.getText());

            validator.validate(member); 

            memberService.updateMember(member);
            parentController.loadMembers();
            closeWindow();
        } catch (Exception e) {
            showAlert("Update Failed", e.getMessage());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
