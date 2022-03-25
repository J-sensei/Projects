// Soon Jie Kang (1201301760)

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginRegisterController implements Initializable {

    private String currentSelection; // Current selected user type

    ObservableList<String> list; // List for the combo box(User Type Selection)

    @FXML
    private ComboBox<String> userSelectType; // Combo box(User Type Selection)

    @FXML
    private TextField username, password, confirmPassword, name, phone; // Textfrields for login and register page

    @FXML
    private Label message; // Show message to the user

    @FXML // Redirect to register page
    public void goToRegister(ActionEvent event) throws IOException{

        Main main = new Main();
        main.changeScene("Registration.fxml");

    }

    @FXML // Redirect to login page
    public void goToLogin(ActionEvent event) throws IOException{

        Main main = new Main();
        main.changeScene("Login.fxml");

    }

    @FXML // Update current selection when combo box is use
    public void getSelection(ActionEvent event) {

        switch(userSelectType.getValue()) {
            case "Property Agent":
                currentSelection = "1";
                break;
            case "Property Owner":
                currentSelection = "2";
                break;
            case "Property Tenant":
                currentSelection = "3";
                break;
            case "Admin":
                currentSelection = "4";
        }
    }

    @FXML // Register user
    public void registerButton(ActionEvent event) throws IOException {

        if(!(password.getText().equals(confirmPassword.getText()))) { // Password and confirm password are not same
            message.setText("Please confirm your password");
        }else if(username.getText().isEmpty() || password.getText().isEmpty() || confirmPassword.getText().isEmpty() || 
                name.getText().isEmpty() || phone.getText().isEmpty()) { // One of the field is empty
            message.setText("Please complete the form");
        }else if((Utils.searchCsvFile("csv/Admin.csv", 0, username.getText()) != null) ||
                (Utils.searchCsvFile("csv/Agent.csv", 0, username.getText()) != null) ||
                (Utils.searchCsvFile("csv/Owner.csv", 0, username.getText()) != null) ||
                (Utils.searchCsvFile("csv/Tenant.csv", 0, username.getText()) != null) ||
                (Utils.searchCsvFile("csv/PendingUser.csv", 0, username.getText()) != null)) {
            message.setText("Username already exist");
        }else if((Utils.searchCsvFile("csv/PendingUser.csv", 3, phone.getText()) != null) ||
                (Utils.searchCsvFile("csv/Agent.csv", 3, phone.getText()) != null) ||
                (Utils.searchCsvFile("csv/Owner.csv", 3, phone.getText()) != null) ||
                (Utils.searchCsvFile("csv/Tenant.csv", 3, phone.getText()) != null)) {
            message.setText("This phone number has been used");
        }else { // Check username
            if(Utils.searchCsvFile("csv/PendingUser.csv", 0, 4, username.getText(), currentSelection) == null) { //No same username in PendingUser.csv
                User newUser;
                switch(currentSelection) { // Register depends on the user selection
                    case "1":
                            newUser = new Agent(username.getText(), password.getText(), name.getText(), phone.getText());
                            newUser.register(); 
                        break;
                    case "2":
                            newUser = new Owner(username.getText(), password.getText(), name.getText(), phone.getText());
                            newUser.register(); 
                        break;
                    case "3":
                            newUser = new Tenant(username.getText(), password.getText(), name.getText(), phone.getText());
                            newUser.register(); 
                        break;
                }
                // Update pending user list
                PendingUser.loadPendingList();

                // Display pop up message after registration
                Alert message = new Alert(AlertType.INFORMATION);
                message.setHeaderText("Register Success");
                message.setContentText("Thank You for joining us!\nPlease wait for admin approve your account.");
                message.showAndWait();   
                //Refresh the page
                Main main = new Main();
                main.changeScene("Registration.fxml");

            }else {
                message.setText("Username already exist");
            }
        }
    }

    @FXML // Login user
    public void loginButton(ActionEvent event) throws IOException {

        if(username.getText().isEmpty() || password.getText().isEmpty()) {
            message.setText("Please complete the form");
        }else {
            Main main = new Main();
            switch(currentSelection) {
                case "1":
                    Agent agent = new Agent(username.getText(), password.getText());
                    if(agent.login() != null) {
                        User.setCurrentUser(agent.login());
                        main.changeScene("AgentOwnerPage.fxml");
                    }else {
                        message.setText("Invalid username and password");
                    }     
                break;
                case "2":
                    Owner owner = new Owner(username.getText(), password.getText());
                    if(owner.login() != null) {
                        User.setCurrentUser(owner.login());
                        main.changeScene("AgentOwnerPage.fxml");
                    }else {
                        message.setText("Invalid username and password");
                    }       
                    break;
                case "3":
                    Tenant tenant = new Tenant(username.getText(), password.getText());
                    if(tenant.login() != null) {
                        User.setCurrentUser(tenant.login());
                        main.changeScene("TenantPage.fxml");
                    }else {
                        message.setText("Invalid username and password");
                    }           
                    break;
                case "4":
                    Admin admin = new Admin(username.getText(), password.getText());
                    if(admin.login() != null) {
                        User.setCurrentUser(admin.login());
                        main.changeScene("AdminPage.fxml");
                    }else {
                        message.setText("Invalid username and password");
                    }
                    break;
            }
        }
    }

    @Override // initialize the drop down menu values base on the login or register page
    public void initialize(URL location, ResourceBundle resources) {

        // To find out this is login or register page as register page does not have admin option
        if(location.toString().substring(location.toString().lastIndexOf('/') + 1).equals("Login.fxml")) {
            list = FXCollections.observableArrayList("Admin", "Property Agent", "Property Owner", "Property Tenant");
            currentSelection = "4";
        }else {
            list = FXCollections.observableArrayList("Property Agent", "Property Owner", "Property Tenant");
            currentSelection = "1";
        }
        
        // Initialize conbo box values
        userSelectType.setItems(list);
        userSelectType.getSelectionModel().selectFirst();
        
    }
}
