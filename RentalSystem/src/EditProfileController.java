// Teoh Yee Chien (1201300833)

import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class EditProfileController {
    boolean toggleEnabledPassword = false;
    boolean toggleEnabledFullName = false;
    boolean toggleEnabledPhone = false;

    Main main = new Main();

    // USERNAME is not user-changeable.
    // Meant for showing at USERNAME text field.
    @FXML
    Label usernameLabel;

    // Particulars in the text boxes.
    @FXML
    private TextField username, password, confirmPassword, fullName, phone;

    @FXML
    private Label phoneLabel, nameLabel;

    @FXML
    private Button saveButton;

    @FXML
    private ToggleButton phoneEdit, nameEdit;

    //
    String name = "";
    String phoneField = "";

    // Load up USERNAME for the USERNAME text box.
    @FXML
    public void initialize() {
        if (User.getCurrentUser() instanceof Agent) {
            name = ((Agent)User.getCurrentUser()).getFullName();
            phoneField = ((Agent)User.getCurrentUser()).getPhone();
        }else if(User.getCurrentUser() instanceof Owner) {
            name = ((Owner)User.getCurrentUser()).getFullName();
            phoneField = ((Owner)User.getCurrentUser()).getPhone();
        }else if(User.getCurrentUser() instanceof Tenant) {
            name = ((Tenant)User.getCurrentUser()).getFullName();
            phoneField = ((Tenant)User.getCurrentUser()).getPhone();
        }else {
            fullName.setVisible(false);
            phone.setVisible(false);
            nameEdit.setVisible(false);
            phoneEdit.setVisible(false);
            nameLabel.setVisible(false);
            phoneLabel.setVisible(false);
        }

        usernameLabel.setText(User.getCurrentUser().getUsername()); // Pre-load USERNAME.
        password.setText(User.getCurrentUser().getPassword()); // Pre-load PASSWORD.
        confirmPassword.setText(User.getCurrentUser().getPassword()); // Pre-load PASSWORD.
        fullName.setText(name); // Pre-load FULL NAME.
        phone.setText(phoneField); // Pre-load PHONE.
    }

    // Incomplete
    @FXML
    public void toggleEditButtonPassword(ActionEvent event) throws IOException {
        if (toggleEnabledPassword == false) {
            password.setDisable(false);
            confirmPassword.setDisable(false);
            password.setText("");
            confirmPassword.setText("");
            toggleEnabledPassword = true;
        } else {
            password.setDisable(true);
            confirmPassword.setDisable(true);
            password.setText(User.getCurrentUser().getPassword());
            confirmPassword.setText(User.getCurrentUser().getPassword());
            toggleEnabledPassword = false;
        }
        toggleSaveButton();
    }

    @FXML
    public void toggleEditButtonFullName(ActionEvent event) throws IOException {
        if (toggleEnabledFullName == false) {
            fullName.setDisable(false);
            fullName.setText("");
            toggleEnabledFullName = true;
        } else {
            fullName.setDisable(true);
            fullName.setText(name);
            toggleEnabledFullName = false;
        }
        toggleSaveButton();
    }

    @FXML // toggle on and off for edit button phone
    public void toggleEditButtonPhone(ActionEvent event) throws IOException {
        if (toggleEnabledPhone == false) {
            phone.setDisable(false);
            phone.setText("");
            toggleEnabledPhone = true;
        } else {
            phone.setDisable(true);
            phone.setText(phoneField);
            toggleEnabledPhone = false;
        }
        toggleSaveButton();
    }

    @FXML // toggle save button
    public void toggleSaveButton() {
        if (toggleEnabledFullName == true ||
            toggleEnabledPassword == true ||
            toggleEnabledPhone == true
        ) {
            saveButton.setDisable(false);
        } else {
            saveButton.setDisable(true);
        }
    }

    // The real deal: Updating the profile.
    @FXML
    public void updateProfile(ActionEvent event) throws IOException {
        if((password.getText().isEmpty() || confirmPassword.getText().isEmpty() || fullName.getText().isEmpty() || phone.getText().isEmpty())
            && !(User.getCurrentUser() instanceof Admin)) {
            updateProfileBlankField();
        }else if((password.getText().isEmpty() || confirmPassword.getText().isEmpty()) && User.getCurrentUser() instanceof Admin) {
            updateProfileBlankField();
        }else {
            // Ensure both password fields are matching.
            if(!(password.getText().equals(confirmPassword.getText()))) {
                updateProfilePasswordMismatch();
            } else {
                // Determining which CSV file to update.
                if (User.getCurrentUser() instanceof Agent) {
                    Agent agent = new Agent(usernameLabel.getText(), password.getText(), fullName.getText(), phone.getText());
                    agent.update(); // Calling the profile updating function.
                    updateProfileSuccessPrompt(); // Pop-up box indicating successful change.
                    //main.changeScene("AgentOwnerPage.fxml"); // Back to Owner's page.
                }else if(User.getCurrentUser() instanceof Owner) {
                    Owner owner = new Owner(usernameLabel.getText(), password.getText(), fullName.getText(), phone.getText());
                    owner.update(); // Calling the profile updating function.
                    updateProfileSuccessPrompt(); // Pop-up box indicating successful change.
                    //main.changeScene("AgentOwnerPage.fxml"); // Back to Owner's page.
                }else if(User.getCurrentUser() instanceof Tenant) {
                    Tenant tenant = new Tenant(usernameLabel.getText(), password.getText(), fullName.getText(), phone.getText());
                    tenant.update(); // Calling the profile updating function.
                    updateProfileSuccessPrompt(); // Pop-up box indicating successful change.
                    //main.changeScene("TenantPage.fxml"); // Back to Owner's page.
                }else {
                    Admin admin = new Admin(usernameLabel.getText(), password.getText());
                    admin.update();
                    updateProfileSuccessPrompt(); // Pop-up box indicating successful change.
                    //main.changeScene("AdminPage.fxml"); // Back to Owner's page.
                }
                main.changeScene("EditProfile.fxml");
            }
        }
    }

    // Function of redirecting back to home page.
    @FXML
    public void backToMain(ActionEvent event) throws IOException {
        Main main = new Main();
        if (User.getCurrentUser() instanceof Agent) {
            main.changeScene("AgentOwnerPage.fxml");
        }else if(User.getCurrentUser() instanceof Owner) {
            main.changeScene("AgentOwnerPage.fxml");
        }else if(User.getCurrentUser() instanceof Tenant) {
            main.changeScene("TenantPage.fxml");
        }else {
            main.changeScene("AdminPage.fxml");
        }
    }

    // Pop-up box indicating successful change.
    @FXML
    public void updateProfileSuccessPrompt() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Edit Profile");
        alert.setHeaderText(null);
        alert.setContentText("Profile sucessfully updated.");
        alert.showAndWait();
    }

    // Pop-up box indicating successful change.
    @FXML
    public void updateProfileBlankField() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Edit Profile");
        alert.setHeaderText(null);
        alert.setContentText("Please complete the details.");
        alert.showAndWait();
    }

    @FXML // Pop-up box for the mismatch of password and confirm password
    public void updateProfilePasswordMismatch() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Edit Profile");
        alert.setHeaderText(null);
        alert.setContentText("Password mismatch. Please try again.");
        alert.showAndWait();
    }

   
}

