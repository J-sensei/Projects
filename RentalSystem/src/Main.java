// Adam Koh Jia Le (1191101858)
// Liew Jiann Shen (1191100556)
// Soon Jie Kang (1201301760)
// Teoh Yee Chien (1201300833)

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
 
public class Main extends Application {

    private static Stage stage;

    private static Object currentController; // Store the current controller, to refresh the table

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        // Load list of data
        Admin.loadAdmin();
        Tenant.loadTenant();
        Owner.loadOwner();
        Agent.loadAgent();
        
        PendingUser.loadPendingList();
        PendingTenant.loadPendingTenantList();
        Property.loadProperty();
        Property.loadPendingProperty();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Cyberjaya Rental System");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image("file:assets/icon.png"));
            primaryStage.show();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Change the scene by load the fxml file
    public void changeScene(String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();

        currentController = loader.getController(); // set the current controller
        stage.getScene().setRoot(root);

    }

    public static Stage getStage() { return stage; }

    public static Object getCurrentController() { return currentController; }
}