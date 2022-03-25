// Teoh Yee Chien (1201300833)

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PendingTenantController implements Initializable{

    private LinkedList<PendingTenant> pendingTenantList; // current pending tenant list of a property

    // Pending Tenant properties
    @FXML
    private TableView<PendingTenant> pendingTenant;

    @FXML
    private TableColumn<PendingTenant, String> username;

    @FXML
    private TableColumn<PendingTenant, String> fullName;

    @FXML
    private TableColumn<PendingTenant, String> phone;

    @FXML
    private TableColumn<PendingTenant, Button> approve;


    public PendingTenantController(LinkedList<PendingTenant> pendingTenantList) {

        this.pendingTenantList = pendingTenantList;

    }

    @Override // Initialize pending tenant table values
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<PendingTenant> temp = FXCollections.observableArrayList(pendingTenantList);
        
        username.setCellValueFactory(new PropertyValueFactory<PendingTenant, String>("username"));
        fullName.setCellValueFactory(new PropertyValueFactory<PendingTenant, String>("fullName"));
        phone.setCellValueFactory(new PropertyValueFactory<PendingTenant, String>("phone"));
        approve.setCellValueFactory(new PropertyValueFactory<PendingTenant, Button>("approve"));

        pendingTenant.setItems(temp);
        
    }
    
}
