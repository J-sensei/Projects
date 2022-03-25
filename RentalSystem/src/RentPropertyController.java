// Teoh Yee Chien (1201300833)

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RentPropertyController implements Initializable{

    // Property table 
    @FXML
    private TableView<Property> property;
    
    @FXML
    private TableColumn<Property, Integer> id;

    @FXML
    private TableColumn<Property, String> username;

    @FXML
    private TableColumn<Property, String> address;

    @FXML
    private TableColumn<Property, Integer> postcode;

    @FXML
    private TableColumn<Property, String> type;

    @FXML
    private TableColumn<Property, Integer> size;

    @FXML
    private TableColumn<Property, Float> price;

    @FXML
    private TableColumn<Property, Button> detail;

    @FXML
    private TableColumn<Property, Button> apply;

    @FXML // Redirect back to the tenant main menu page
    public void goToMenu(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("TenantPage.fxml");
    }

    @Override // Initialize all the property that are ready to be rent to the table
    public void initialize(URL location, ResourceBundle resources) {
        
        ObservableList<Property> temp = FXCollections.observableArrayList();

        for(int i = 0; i < Property.getPropertyList().size(); i++) {
            boolean match = false;
            if(Property.getPropertyList().get(i).getStatus().equals("active")) {

                for(int j = 0; j < Property.getPropertyList().get(i).getPendingTenant().size(); j++) {
                    if(Property.getPropertyList().get(i).getPendingTenant().get(j).getUsername()
                            .equals(((Tenant)User.getCurrentUser()).getUsername()) && Property.getPropertyList().get(i).getPendingTenant().get(j).getStatus().equals("pending")) {
                        match = true;
                        break;
                    }
                }

            if(match == false)
                temp.add(Property.getPropertyList().get(i));
            }
        }
        
        id.setCellValueFactory(new PropertyValueFactory<Property, Integer>("id"));
        username.setCellValueFactory(new PropertyValueFactory<Property, String>("username"));
        address.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        postcode.setCellValueFactory(new PropertyValueFactory<Property, Integer>("postcode"));
        type.setCellValueFactory(new PropertyValueFactory<Property, String>("type"));
        size.setCellValueFactory(new PropertyValueFactory<Property, Integer>("size"));
        detail.setCellValueFactory(new PropertyValueFactory<Property, Button>("detail"));
        apply.setCellValueFactory(new PropertyValueFactory<Property, Button>("apply"));

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

        property.setItems(temp);
    }

    // Pop up to shows that the pending request the sent
    @FXML
    public static void confirmRentalApplication() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Rent Property");
        alert.setHeaderText(null);
        alert.setContentText("Application successfully submitted.");
        alert.showAndWait();
    }

    // Pop up to shows that the this tenant already have the rental request on the property
    @FXML
    public static void rentalApplicationExisted() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Rent Property");
        alert.setHeaderText(null);
        alert.setContentText("You have already submitted rental application of this property.\nKindly wait for the approval.");
        alert.showAndWait();
    }

    public TableView<Property> getTable() { return property; }
}
