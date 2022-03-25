// Adam Koh Jia Le (1191101858)

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyPropertyController implements Initializable {

    private ObservableList<Property> temp; // temporary list to put into the table

    @FXML 
    private ComboBox<String> propertyType; // Contains Pending property, approved property, and property that have pending tenant

    // Table Properties
    @FXML
    private TableView<Property> property;

    @FXML
    private TableColumn<Property, Integer> id;

    @FXML
    private TableColumn<Property, String> address;

    @FXML
    private TableColumn<Property, Integer> postcode;

    @FXML
    private TableColumn<Property, String> type;

    @FXML
    private TableColumn<Property, Float> price;

    @FXML
    private TableColumn<Property, String> status;

    @FXML
    private TableColumn<Property, Button> detail;

    @FXML
    private TableColumn<Property, Button> edit;

    @FXML
    private TableColumn<Property, Button> drop;

    @FXML
    private TableColumn<Property, Button> approve;

    @FXML
    private TableColumn<Property, Button> delete;

    @FXML
    private TableColumn<Property, Integer> pendingTenant;

    @FXML
    private TableColumn<Property, CheckBox> hidden;

    @FXML // Redirect back to the agent or owner main menu
    public void goToMenu(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("AgentOwnerPage.fxml");
    }

    @FXML // Change the table column and values based on the property type selection
    public void selectPropertyType(ActionEvent event) {

        id.setVisible(true);
        pendingTenant.setVisible(false);
        approve.setVisible(false);
        status.setVisible(true);

        delete.setVisible(false);
        drop.setVisible(true);
        edit.setVisible(true);
        hidden.setVisible(false);

        switch(propertyType.getValue()) {
            case "Approved Property":
                setProperty();
                hidden.setVisible(true);
                break;
            case "Pending Property":
                id.setVisible(false);
                drop.setVisible(false);
                delete.setVisible(true);
                setPendingProperty();
                break;
            case "Approve Pending Tenant":
                pendingTenant.setVisible(true);
                status.setVisible(false);
                delete.setVisible(false);
                drop.setVisible(false);
                edit.setVisible(false);
                approve.setVisible(true);
                setApproveProperty();
                break;
        }
    }

    // Tabel values for approved property
    private void setApproveProperty() {

        temp = FXCollections.observableArrayList();

        // Get the property that match to this user
        for(int i = 0; i < Property.getPropertyList().size(); i++) {
            if(Property.getPropertyList().get(i).getUsername().equals(User.getCurrentUser().getUsername()) && 
                !Property.getPropertyList().get(i).getStatus().equals("inactive") &&
                Property.getPropertyList().get(i).getNoPendingTenant() > 0)
                temp.add(Property.getPropertyList().get(i));
        }
        
        property.setItems(temp);

    }

    // Table values for pending property
    private void setPendingProperty() {

        temp = FXCollections.observableArrayList();

        // Get the pending property that match to this user
        for(int i = 0; i < Property.getPendingPropertyList().size(); i++) {
            if(Property.getPendingPropertyList().get(i).getUsername().equals(User.getCurrentUser().getUsername()))
                temp.add(Property.getPendingPropertyList().get(i));
        }
        
        property.setItems(temp);

    }

    // Table values for the property that have pending tenant in it
    private void setProperty() {

        temp = FXCollections.observableArrayList();

        // Get the property that match to this user
        for(int i = 0; i < Property.getPropertyList().size(); i++) {
            if(Property.getPropertyList().get(i).getUsername().equals(User.getCurrentUser().getUsername()))
                temp.add(Property.getPropertyList().get(i));
        }
        
        property.setItems(temp);

    }
    
    @Override // initialize the default table values
    public void initialize(URL location, ResourceBundle resources) {
        
        //Columns visibility settings
        pendingTenant.setVisible(false);
        approve.setVisible(false);
        delete.setVisible(false);

        //Table columns
        id.setCellValueFactory(new PropertyValueFactory<Property, Integer>("id"));
        address.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        postcode.setCellValueFactory(new PropertyValueFactory<Property, Integer>("postcode"));
        type.setCellValueFactory(new PropertyValueFactory<Property, String>("type"));
        status.setCellValueFactory(new PropertyValueFactory<Property, String>("status"));
        pendingTenant.setCellValueFactory(new PropertyValueFactory<Property, Integer>("noPendingTenant"));

        price.setCellValueFactory(new PropertyValueFactory<Property, Float>("price"));
        price.setCellFactory(c -> new TableCell<Property, Float>() {
            @Override
            protected void updateItem(Float value, boolean empty) {
                super.updateItem(value, empty);
                if(empty) {
                    setText(null);
                }else {
                    setText(String.format("%.2f", value.floatValue())); 
                }
            }
        });

        approve.setCellValueFactory(new PropertyValueFactory<Property, Button>("openPendingTenant"));
        detail.setCellValueFactory(new PropertyValueFactory<Property, Button>("detail"));
        edit.setCellValueFactory(new PropertyValueFactory<Property, Button>("edit"));
        drop.setCellValueFactory(new PropertyValueFactory<Property, Button>("drop"));
        delete.setCellValueFactory(new PropertyValueFactory<Property, Button>("delete"));
        hidden.setCellValueFactory(new PropertyValueFactory<Property, CheckBox>("hidden"));

        setProperty();
        
        propertyType.setItems(FXCollections.observableArrayList("Approved Property", "Pending Property", "Approve Pending Tenant"));
        propertyType.getSelectionModel().selectFirst();
        
    }

    // getter
    public TableView<Property> getTable() { return property; }
    public ObservableList<Property> getObervableList() { return temp; }
    
}
