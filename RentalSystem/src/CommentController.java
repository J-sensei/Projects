// Liew Jiann Shen (1191100556)
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CommentController implements Initializable{

    private Property property; // Current property

    @FXML
    private TextField comment; // Comment data

    @FXML
    private Label id;

    public CommentController(Property property) {
        this.property = property;
    }

    // Edit Comment of the property
    @FXML // Edit the comment and close the current window
    public void submit(ActionEvent event) {
        property.editComment(comment.getText());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override // initialize the comment to the text field
    public void initialize(URL location, ResourceBundle resources) {
        
        if(!property.getComment().equals("-")) {
            comment.setText(property.getComment());
        }

        id.setText(Integer.toString(property.getId()));
        
    }
    
}
