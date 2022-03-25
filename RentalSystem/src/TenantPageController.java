// Teoh Yee Chien (1201300833)

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TenantPageController implements Initializable {
    
    private ObservableList<Property> currentProperties; // list of property that tenant currently renting
    private ObservableList<Property> pendingProperties; // list of prperty that tenant have pending request

    @FXML
    private Label name;

    // Tenant renting property
    @FXML
    private TableView<Property> property;

    @FXML
    private TableColumn<Property, Integer> id;

    @FXML
    private TableColumn<Property, String> address;

    @FXML
    private TableColumn<Property, Integer> postcode;

    @FXML
    private TableColumn<Property, Button> detail;

    // Tenant Pending Property
    @FXML
    private TableView<Property> pendingProperty;

    @FXML
    private TableColumn<Property, Integer> pendingId;

    @FXML
    private TableColumn<Property, String> pendingAddress;

    @FXML
    private TableColumn<Property, Integer> pendingPostcode;

    @FXML
    private TableColumn<Property, Button> pendingDetail;

    @FXML //logout, go back to login page and set current user to null
    public void logout(ActionEvent event) throws IOException {
        User.logout();
    }

    @FXML // Redirect to the edit profile page
    public void editProfile(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("EditProfile.fxml"); 
    }

    @FXML // Redirect ot the rent property page
    public void rentProperty(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("RentProperty.fxml"); 
    }

    @FXML // Redirect to the view report page
    public void viewReport(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewReport.fxml"));
        Scene scene = new Scene(loader.load());
        ViewReportController controller = loader.getController();
        controller.setTableData("potentialTenant");
        Main.getStage().setScene(scene);
    }

    // Load the list of prperty that tenant currently renting
    private void loadCurrentProperty() {
        currentProperties = FXCollections.observableArrayList();

        for(int i = 0; i < Property.getPropertyList().size(); i++) {

            if(Property.getPropertyList().get(i).getTenant() != null) {
                if(Property.getPropertyList().get(i).getTenant().equals(User.getCurrentUser().getUsername())) {
                    currentProperties.add(Property.getPropertyList().get(i));
                }
            }
        }

        property.setItems(currentProperties);
    }

    // Load the list of prperty that tenant have pending request on it
    private void loadPendingProperty() {
        pendingProperties = FXCollections.observableArrayList();

        for(int i = 0; i < Property.getPropertyList().size(); i++) {

            if(Property.getPropertyList().get(i).getStatus().equals("active")) {

                for(int j = 0; j < Property.getPropertyList().get(i).getPendingTenant().size(); j++) {
                    if(Property.getPropertyList().get(i).getPendingTenant().get(j).getUsername()
                            .equals(((Tenant)User.getCurrentUser()).getUsername()) && Property.getPropertyList().get(i).getPendingTenant().get(j).getStatus().equals("pending")) {
                        pendingProperties.add(Property.getPropertyList().get(i));
                    }
                }
            }
        }

        pendingProperty.setItems(pendingProperties);
    }

    @Override // initialze all the default values of tenant homepage
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(((Tenant)User.getCurrentUser()).getFullName());

        id.setCellValueFactory(new PropertyValueFactory<Property, Integer>("id"));
        address.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        postcode.setCellValueFactory(new PropertyValueFactory<Property, Integer>("postcode"));
        detail.setCellValueFactory(new PropertyValueFactory<Property, Button>("detail"));

        pendingId.setCellValueFactory(new PropertyValueFactory<Property, Integer>("id"));
        pendingAddress.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        pendingPostcode.setCellValueFactory(new PropertyValueFactory<Property, Integer>("postcode"));
        pendingDetail.setCellValueFactory(new PropertyValueFactory<Property, Button>("detail"));

        loadCurrentProperty();
        loadPendingProperty();
    }

}
