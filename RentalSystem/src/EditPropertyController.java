// Adam Koh Jia Le (1191101858)

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditPropertyController implements Initializable{

    private Property property; // current property

    private String tempAddress; // to check if no changes to the address

    // Property details
    @FXML
    private Button kick;

    @FXML
    private Label id;

    @FXML
    private Label message;

    @FXML
    private Label status;

    @FXML
    private Label tenant;

    @FXML
    private TextField address;

    @FXML
    private TextField postcode;

    @FXML
    private TextField size;

    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField room;

    @FXML
    private TextField bathRoom;

    @FXML
    private TextField price;

    @FXML
    private CheckBox pool, airCon, wifi, waterHeater;

    // Constructor to get the property values
    public EditPropertyController(Property property) {
        this.property = property;
    }

    // set the default check box value
    private void setCheckBox(String value, CheckBox box) {
        if(value.equals("Yes")) box.setSelected(true);
    }

    // get the check box values
    private String getCheckBoxValue(CheckBox box) {
        if(box.isSelected()) return "Yes";
        else return "No";
    }

    //Kick current tenant who is renting this property
    @FXML
    public void kick(ActionEvent event) throws IOException {
        if(!property.getStatus().equals("hidden"))
            property.setStatus("active");
        property.setTenant("null");

        Utils.writeCSvFile("csv/Property.csv", Property.getPropertyList());

        // Refresh scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProperty.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

        // Refresh table
        if(User.getCurrentUser() instanceof Admin) {
            ManagePropertyController controller = (ManagePropertyController)Main.getCurrentController();
            controller.getTable().refresh();
        }else {
            MyPropertyController controller = (MyPropertyController)Main.getCurrentController();
            controller.getTable().refresh();
        }
    }

    // Update this property
    @FXML
    public void save(ActionEvent event) throws IOException {

        // Check Inputs
        if(address.getText().isEmpty() || postcode.getText().isEmpty() || size.getText().isEmpty() || type.getValue().isEmpty() || 
            room.getText().isEmpty() || bathRoom.getText().isEmpty() ||  price.getText().isEmpty()) {
            message.setText("Please complete all info");
        }else if(!address.getText().equals(tempAddress) && (Utils.searchCsvFile("csv/PropertyPending.csv", 1, address.getText(), ";") != null ||
                    Utils.searchCsvFile("csv/Property.csv", 2, address.getText(), ";") != null)) {
            message.setText("This address is already exist");
        }else {
            // Apply Changes
            property.setAddress(address.getText());
            property.setPostcode(Integer.parseInt(postcode.getText()));
            property.setSize(Integer.parseInt(size.getText()));
            property.setType(type.getValue());
            property.setNoRoom(Integer.parseInt(room.getText()));
            property.setNoBathRoom(Integer.parseInt(bathRoom.getText()));
            property.setPrice(Float.parseFloat(price.getText()));

            property.setSwimmingPool(getCheckBoxValue(pool));
            property.setWifi(getCheckBoxValue(wifi));
            property.setAirCon(getCheckBoxValue(airCon));
            property.setWaterheater(getCheckBoxValue(waterHeater));

            // Save Changes
            if(property.getId() > 0) {
                Utils.writeCSvFile("csv/Property.csv", Property.getPropertyList());
            }else {
                Utils.writeCsvPendingPropertyFile("csv/PropertyPending.csv", Property.getPendingPropertyList());
            }

            //Refresh table
            if(User.getCurrentUser() instanceof Admin) {
                ManagePropertyController controller = (ManagePropertyController)(Main.getCurrentController());
                controller.getTable().refresh();
            }else {
                MyPropertyController controller = (MyPropertyController)(Main.getCurrentController());
                controller.getTable().refresh();
            }

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    // Intialize all the default values to the edit property page
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        tempAddress = property.getAddress();
        type.setItems(FXCollections.observableArrayList("Condominium", "Single Story", "Double Storey", "TownHouse", "Bunglow"));

        //Agent / Owner cannot edit the property details after approved, they can only edit price
        if((User.getCurrentUser() instanceof Agent || User.getCurrentUser() instanceof Owner) && property.getId() > 0) {
            address.setDisable(true);
            postcode.setDisable(true);
            size.setDisable(true);
            type.setDisable(true);
            room.setDisable(true);
            bathRoom.setDisable(true);

            pool.setDisable(true);
            wifi.setDisable(true);
            airCon.setDisable(true);
            waterHeater.setDisable(true);      
        }

        //Init id
        if(property.getId() > 0) {
            id.setText(Integer.toString(property.getId()));
        }else {
            id.setText("Pending Property");
        }

        // Init Porperty values
        address.setText(property.getAddress());
        postcode.setText(Integer.toString(property.getPostcode()));
        size.setText(Integer.toString(property.getSize()));
        type.getSelectionModel().select(property.getType());
        room.setText(Integer.toString(property.getNoRoom()));
        bathRoom.setText(Integer.toString(property.getNoBathRoom()));
        price.setText(String.format("%.2f", property.getPrice()));
        status.setText(property.getStatus());
        tenant.setText(property.getTenant() == null ? "-" : property.getTenant());

        if(property.getTenant() != null) kick.setDisable(false);

        setCheckBox(property.getSwimmingPool(), pool);
        setCheckBox(property.getWifi(), wifi);
        setCheckBox(property.getAirCon(), airCon);
        setCheckBox(property.getWaterHeater(), waterHeater);
        
    }
    
}
