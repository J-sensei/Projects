// Teoh Yee Chien (1201300833)

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;

public class PendingUserController implements Initializable{

    private ObservableList<PendingUser> temp; // temporary pending user values to put into the table

    // Pending User table properties
    @FXML
    private TableView<PendingUser> users;

    @FXML
    private TableColumn<PendingUser, String> username;

    @FXML
    private TableColumn<PendingUser, String> name;

    @FXML
    private TableColumn<PendingUser, String> phone;

    @FXML
    private TableColumn<PendingUser, String> role;

    @FXML
    private TableColumn<PendingUser, Button> approve;

    @FXML
    private TableColumn<PendingUser, Button> delete;

    @FXML //redirect to Main Menu
    public void goToMenu(ActionEvent event) throws IOException{

        Main main = new Main();
        main.changeScene("AdminPage.fxml");

    }

    @Override // Initialize the values of pending user table
    public void initialize(URL location, ResourceBundle resources) {
        
        username.setCellValueFactory(new PropertyValueFactory<PendingUser, String>("username"));
        name.setCellValueFactory(new PropertyValueFactory<PendingUser, String>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<PendingUser, String>("phone"));
        role.setCellValueFactory(new PropertyValueFactory<PendingUser, String>("role"));
        approve.setCellValueFactory(new PropertyValueFactory<PendingUser, Button>("approve"));
        delete.setCellValueFactory(new PropertyValueFactory<PendingUser, Button>("delete"));

        temp = FXCollections.observableArrayList(PendingUser.getPendingList());
        users.setItems(temp);
        
    }

    public TableView<PendingUser> getTable() { return users; }
    
}
