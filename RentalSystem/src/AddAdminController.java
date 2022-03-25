// Liew Jiann Shen (1191100556)

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AddAdminController {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField confirmPassword;

    @FXML
    private Label message;

    // Add Admin
    // add button to add the new admin to the system
    public void add(ActionEvent event) throws IOException {

        if(username.getText().isEmpty() || password.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
            message.setText("Please complete the form");
            return;
        }

        if(!password.getText().equals(confirmPassword.getText())) {
            message.setText("Passwords are not same");
        }else if((Utils.searchCsvFile("csv/Admin.csv", 0, username.getText()) != null) ||
                (Utils.searchCsvFile("csv/Agent.csv", 0, username.getText()) != null) ||
                (Utils.searchCsvFile("csv/Owner.csv", 0, username.getText()) != null) ||
                (Utils.searchCsvFile("csv/Tenant.csv", 0, username.getText()) != null)) {
            message.setText("Username already exist");
        }else {
            Admin admin = new Admin(username.getText(), password.getText());
            admin.register();

            //Update Admin list
            Admin.loadAdmin();
            // Display pop up message after registration
            Alert message = new Alert(AlertType.INFORMATION);
            message.setHeaderText("Register Success");
            message.setContentText("An Admin is added to the system.");
            message.showAndWait();   
            //Refresh the page
            Main main = new Main();
            main.changeScene("AddAdmin.fxml");
        }   
    }

    // redirect back to admin main menu
    public void goToMenu(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("AdminPage.fxml");
    }

}

