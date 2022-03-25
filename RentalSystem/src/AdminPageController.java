// Liew Jiann Shen (1191100556)

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class AdminPageController implements Initializable {
    
    @FXML
    private Label name;

    @FXML
    private Label pendingUser;

    @FXML
    private Label pendingProperty;

    @FXML
    private Label user;

    @FXML
    private Label property;

    @FXML
    private Label admin, agent, owner, tenant;


    @FXML //redirect to review pending user page
    public void goToReview(ActionEvent event) throws IOException {

        Main main = new Main();
        main.changeScene("PendingUser.fxml");

    }

    @FXML //redirect to approve property page
    public void goToApproveProperty(ActionEvent event) throws IOException {

        Main main = new Main();
        main.changeScene("ApproveProperty.fxml");

    }

    @FXML // redirect to edit profile page
    public void editProfile(ActionEvent event) throws IOException {

        Main main = new Main();
        main.changeScene("EditProfile.fxml");

    }

    @FXML //redirect to add admin page
    public void goToAddAdmin(ActionEvent event) throws IOException {

        Main main = new Main();
        main.changeScene("AddAdmin.fxml");

    }

    @FXML //redirect to add admin page
    public void goToReport(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewReport.fxml"));
        Scene scene = new Scene(loader.load());
        ViewReportController controller = loader.getController();
        controller.setTableData("admin");
        Main.getStage().setScene(scene);

    }


    @FXML //redirect to add admin page
    public void goCommentProperty(ActionEvent event) throws IOException {

        Main main = new Main();
        main.changeScene("CommentProperty.fxml");

    }

    @FXML // redirect to manage property page
    public void goToManageProperty(ActionEvent event) throws IOException {

        Main main = new Main();
        main.changeScene("ManageProperty.fxml");

    }

    @FXML //logout, go back to login page and set current user to null
    public void logout(ActionEvent event)  throws IOException {
        User.logout();
    }

    @Override // initialize home page properties
    public void initialize(URL location, ResourceBundle resources) {
        
        name.setText(User.getCurrentUser().getUsername());

        // Data
        pendingUser.setText(Integer.toString(PendingUser.getPendingList().size()));
        pendingProperty.setText(Integer.toString(Property.getPendingPropertyList().size()));

        user.setText(Integer.toString(Admin.getAdminList().size() + Agent.getAgentList().size() + Owner.getOwnerList().size() + Tenant.getTenantList().size()));
        admin.setText(Integer.toString(Admin.getAdminList().size()));
        agent.setText(Integer.toString(Agent.getAgentList().size()));
        owner.setText(Integer.toString(Owner.getOwnerList().size()));
        tenant.setText(Integer.toString(Tenant.getTenantList().size()));

        property.setText(Integer.toString(Property.getPropertyList().size()));
        
    }

}
