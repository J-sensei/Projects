// Liew Jiann Shen (1191100556)

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CommentPropertyController implements Initializable{

    // Table Properties
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
    private TableColumn<Property, Button> comment;

    @FXML // Redirect back to admin main menu
    public void goToMenu(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("AdminPage.fxml");
    }

    @Override // Initialize all the property to the table
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Property> temp = FXCollections.observableArrayList(Property.getPropertyList());
        
        id.setCellValueFactory(new PropertyValueFactory<Property, Integer>("id"));
        username.setCellValueFactory(new PropertyValueFactory<Property, String>("username"));
        address.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        postcode.setCellValueFactory(new PropertyValueFactory<Property, Integer>("postcode"));
        type.setCellValueFactory(new PropertyValueFactory<Property, String>("type"));
        size.setCellValueFactory(new PropertyValueFactory<Property, Integer>("size"));
        detail.setCellValueFactory(new PropertyValueFactory<Property, Button>("detail"));
        comment.setCellValueFactory(new PropertyValueFactory<Property, Button>("commentBtn"));

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
}
