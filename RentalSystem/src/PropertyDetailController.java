// Adam Koh Jia Le (1191101858)

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class PropertyDetailController implements Initializable {

    private Property property; // current property class

    // Property details
    @FXML
    private Label id;

    @FXML
    private Label username;

    @FXML
    private Label address;

    @FXML
    private Label postcode;

    @FXML
    private Label type;

    @FXML
    private Label size;

    @FXML
    private Label room;

    @FXML
    private Label price;

    @FXML
    private Label rentalRate;

    @FXML
    private Label bathRoom;

    @FXML
    private Label pool;

    @FXML
    private Label airCon;

    @FXML
    private Label wifi;

    @FXML
    private Label waterHeater;

    @FXML
    private ImageView image;

    @FXML
    private Label status;

    @FXML
    private Label tenant;

    @FXML
    private Label comment;

    public PropertyDetailController(Property property) {
        this.property = property;
    }

    @Override // Initialize the property details to the labels
    public void initialize(URL location, ResourceBundle resources) {
        
        if(property.getId() < 0) { // Its pending property
            id.setText("Pending Property");
            status.setText("Pending");
        }else {
            id.setText(Integer.toString(property.getId()));
            status.setText(property.getStatus());
        }

        if(property.getTenant() == null)
            tenant.setText("-");
        else
            tenant.setText(property.getTenant()); 

        if(property.getComment() == null)
            comment.setText("-");
        else
            comment.setText(property.getComment());  

        username.setText(property.getUsername());
        address.setText(property.getAddress());;
        postcode.setText(Integer.toString(property.getPostcode()));
        type.setText(property.getType());;
        size.setText(Integer.toString(property.getSize()));
        room.setText(Integer.toString(property.getNoRoom()));
        bathRoom.setText(Integer.toString(property.getNoBathRoom()));
        price.setText(String.format("%.2f", property.getPrice()));
        pool.setText(property.getSwimmingPool());;
        airCon.setText(property.getAirCon());;
        wifi.setText(property.getWifi());;
        waterHeater.setText(property.getWaterHeater());;

        rentalRate.setText(String.format("%.2f", property.getRentalRate()));
        
        image.setImage(property.getImage());
        
    }

}

