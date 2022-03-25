// Adam Koh Jia Le (1191101858)

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AddPropertyController implements Initializable {

    private File image;

    // Facilities check box
    @FXML   private CheckBox poolBox;
    @FXML   private CheckBox airconBox;
    @FXML   private CheckBox wifiBox;
    @FXML   private CheckBox waterHeaterBox;

    // Property details
    @FXML   private TextField address;
    @FXML   private TextField postcode;
    @FXML   private ComboBox<String> houseType;
    @FXML   private TextField roomNo;
    @FXML   private TextField bathNo;
    @FXML   private TextField size;
    @FXML   private TextField price;

    // Error message label
    @FXML   private Label message;
    // Display file name when a file is selected
    @FXML  private Label filename;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        houseType.setItems(FXCollections.observableArrayList("Condominium", "Single Story", "Double Storey", "TownHouse", "Bungalow"));
    }

    // Image selector
    // Button to prompt the video for user to select their images
    @FXML
    public void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg", "*png"));

        image = fileChooser.showOpenDialog(null);
        if(image != null) {
            filename.setText(image.getName());
        }
    }

    // Get the extension name of a fileame
    private String getExtension(String filename) {
        String result = "";

        int c1 = 4;
        int c2 = filename.length() - 1;
        while(c1 != 0) {
            result += filename.charAt(c2);
            c1--;
            c2--;
        }

        return new StringBuilder(result).reverse().toString();
    }

    // Generate random string to name a new image
    private String generateImageName() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        final int targetStringLength = 11;
        Random random = new Random();
    
        String generatedString = random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
    
        return generatedString;
    }
    
    // Add Property Button
    // A button to add the new property to the pending list
    public void add(ActionEvent event) throws IOException {
        check();
    }
   
   // True -> Yes, False -> No, return the string base on the checkbox value 
    private String selectedBox(CheckBox faciliti) {
        String select = "";

        if(faciliti.isSelected())
            select = "Yes";
        else
            select = "No";
        
        return select;
    }

    // Go Back to Agent or Owner Main menu
    public void cancel(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("AgentOwnerPage.fxml");
     }

    // Checking and Insert new property
    // Perform input validation and insert the new value to the system
    private void check() throws IOException {
        if(houseType.getValue() == null) { 
            message.setText("Please input all info");
            return;
        }
        
        if(address.getText().isEmpty() || postcode.getText().isEmpty() || houseType.getValue().isEmpty() || 
            roomNo.getText().isEmpty() || bathNo.getText().isEmpty() || price.getText().isEmpty() || size.getText().isEmpty()) {
            message.setText("Please complete all info");
        }else if(Utils.searchCsvFile("csv/PropertyPending.csv", 1, address.getText(), ";") != null ||
                 Utils.searchCsvFile("csv/Property.csv", 2, address.getText(), ";") != null) {
            message.setText("This address is already exist");
        }else {

            String swimmingPool = selectedBox(poolBox);
            String airCon = selectedBox(airconBox);
            String wifi = selectedBox(wifiBox);
            String waterHeater = selectedBox(waterHeaterBox);

            String filename = "assets/default.jpg";
            if(image != null) {
                filename = "assets/Property/" + generateImageName() + getExtension(image.getName());
                File destFile = new File(filename);
                //Copy file
                try {
                    Files.copy(image.toPath(), destFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Property property = new Property(User.getCurrentUser().getUsername(), address.getText(), Integer.parseInt(postcode.getText()), houseType.getValue(), 
            Integer.parseInt(size.getText()), Float.parseFloat(price.getText()), Integer.parseInt(roomNo.getText()), Integer.parseInt(bathNo.getText()),
            swimmingPool, airCon, wifi, waterHeater, filename);
            Property.getPendingPropertyList().add(property);
            Utils.writeCsvFile("csv/PropertyPending.csv", property.getPendingProperty());
            //Property.loadPendingProperty(); //reload pending property list
            // Display pop up message after proproty added to the file
            Alert message = new Alert(AlertType.INFORMATION);
            message.setHeaderText("Property Added");
            message.setContentText("Your property has been added to the pending list\nPlease wait for admin approve your property.");
            message.showAndWait();   
            //Refresh the page
            Main main = new Main();
            main.changeScene("AddProperty.fxml");
        }
    }
    
}
