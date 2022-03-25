// Adam Koh Jia Le (1191101858)

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class AgentOwnerPageController implements Initializable {
    
    @FXML
    private Label name; // Label to display user full name on the main menu

    @FXML
    private Label pendingProperty, pendingTenant;

    @FXML // Redirect to add property page
    public void addProperty() throws IOException {
        Main main = new Main();
        main.changeScene("AddProperty.fxml");
    }

    @FXML // Redirect to edit profile page
    public void editProfile() throws IOException {
        Main main = new Main();
        main.changeScene("EditProfile.fxml");
    }

    @FXML // redirect to my property page
    public void myProperty() throws IOException {
        Main main = new Main();
        main.changeScene("MyProperty.fxml");
    }

    @FXML // redirect to view report page
    public void viewReport(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewReport.fxml"));
        Scene scene = new Scene(loader.load());
        ViewReportController controller = loader.getController();
        controller.setTableData(User.getCurrentUser().getUsername());
        Main.getStage().setScene(scene);
    }

    @FXML // Logout, go back to login page and set current user to null
    public void logout(ActionEvent event)  throws IOException {
        User.logout();
    }

    @Override // initialize agent and owner page properties
    public void initialize(URL location, ResourceBundle resources) {
        
        if(User.getCurrentUser() instanceof Agent)
            name.setText(((Agent)User.getCurrentUser()).getFullName());
        else
            name.setText(((Owner)User.getCurrentUser()).getFullName());

        // Count total approved property 
        int count = 0;
        for(int i = 0; i < Property.getPendingPropertyList().size(); i++) {
            if(Property.getPendingPropertyList().get(i).getUsername().equals(User.getCurrentUser().getUsername()))
                count++;
        }
        pendingProperty.setText(Integer.toString(count));

        // Count total pending property
        count = 0;
        for(int i = 0; i < Property.getPropertyList().size(); i++) {
            if(Property.getPropertyList().get(i).getUsername().equals(User.getCurrentUser().getUsername()) &&
                Property.getPropertyList().get(i).getPendingTenant().size() > 0)
                count++;
        }
        pendingTenant.setText(Integer.toString(count));
    }

}
