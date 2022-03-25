import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ShopApp extends Application{

    private Shop shop;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FileCsv.checkFileExist("CustomerLog.csv");
        FileCsv.checkFileExist("ShopLog.csv");
        FileCsv.checkFileExist("RecordLog.csv");
        menuPage(primaryStage);

        primaryStage.show();
    }
    
    public void menuPage(Stage stage){
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));  //padding
        layout.setVgap(10); //vertical gap
        layout.setHgap(10); //horizontal gap

        Label titleLabel = new Label("Shop Menu");
        GridPane.setConstraints(titleLabel, 0, 0);

        Button registerBtn = new Button("Register");
        Button loginBtn = new Button("Login");
        Button exitBtn = new Button("Exit");

        registerBtn.setOnAction(e -> registerPage(stage));
        loginBtn.setOnAction(e -> loginPage(stage));
        exitBtn.setOnAction(e -> stage.close());

        //set position
        GridPane.setConstraints(registerBtn, 0, 1);
        GridPane.setConstraints(loginBtn, 0, 2);
        GridPane.setConstraints(exitBtn, 0, 3);

        layout.getChildren().addAll(titleLabel, registerBtn, loginBtn, exitBtn);
        layout.setAlignment(Pos.CENTER);

        Scene menuScene = new Scene(layout, 300, 300);

        stage.setTitle("Customer");
        stage.setScene(menuScene);
    }

    public void mainMenu(Stage stage){
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(8);
        layout.setHgap(10);

        Label shopId = new Label("ID: " + shop.getId());
        Label shopName = new Label("Name: " + shop.getShopName());
        Label shopManager = new Label("Manager: " + shop.getManager());
        Label shopStatus = new Label("Status: " + shop.getStatusString());

        Button menuBtn = new Button("Menu");

        menuBtn.setOnAction(e -> menuPage(stage));

        GridPane.setConstraints(shopId, 0,0);
        GridPane.setConstraints(shopName, 0,1);
        GridPane.setConstraints(shopManager, 0,2);
        GridPane.setConstraints(shopStatus, 0,3);
        GridPane.setConstraints(menuBtn, 0,4);

        layout.getChildren().addAll(shopId, shopName, shopManager, shopStatus, menuBtn);
        layout.setAlignment(Pos.CENTER);

        Scene loginScene = new Scene(layout, 300, 300);
        stage.setTitle("Login Customer");
        stage.setScene(loginScene);
    }

    public void loginPage(Stage stage){
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(8);
        layout.setHgap(10);

        Label phoneLabel = new Label("Phone: ");

        GridPane.setConstraints(phoneLabel, 0, 0);

        TextField phone = new TextField();
        phone.setPromptText("eg. 0322228888");

        GridPane.setConstraints(phone, 1, 0);

        Button menuBtn = new Button("Menu");
        Button loginBtn = new Button("Login");

        menuBtn.setOnAction(e -> menuPage(stage));
        loginBtn.setOnAction(e -> {
            if(loginShop(phone)){
                mainMenu(stage);
            }});

        GridPane.setConstraints(menuBtn, 0,3);
        GridPane.setConstraints(loginBtn, 1,3);

        layout.getChildren().addAll(phoneLabel, phone, menuBtn, loginBtn);
        layout.setAlignment(Pos.CENTER);

        Scene loginScene = new Scene(layout, 300, 300);
        stage.setTitle("Login Shop");
        stage.setScene(loginScene);
    }
    public void registerPage(Stage stage){
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(8);
        layout.setHgap(10);
        
        Label phoneLabel = new Label("Phone: ");
        Label shopLabel = new Label("Shop Name: ");
        Label managerLabel = new Label("Manager: ");
  
        GridPane.setConstraints(phoneLabel, 0, 0);
        GridPane.setConstraints(shopLabel, 0, 1);
        GridPane.setConstraints(managerLabel, 0, 2);
  
        TextField phone = new TextField();
        phone.setPromptText("eg. 0388889999");
        TextField shop = new TextField();
        shop.setPromptText("eg. Tesco");
        TextField manager = new TextField();
        manager.setPromptText("Manager Name");
  
        GridPane.setConstraints(phone, 1, 0);
        GridPane.setConstraints(shop, 1, 1);
        GridPane.setConstraints(manager, 1, 2);
  
        Button menuBtn = new Button("Menu");
        Button submitBtn = new Button("Register");
  
        menuBtn.setOnAction(e -> menuPage(stage));
        submitBtn.setOnAction(e -> {
          if(registerShop(phone, shop, manager)){
              phone.clear();
              shop.clear();
              manager.clear();
          }
          });
  
        GridPane.setConstraints(menuBtn, 0,4);
        GridPane.setConstraints(submitBtn, 1,4);
    
        layout.getChildren().addAll(phoneLabel, shopLabel, managerLabel, phone, shop, manager, menuBtn, submitBtn);
        layout.setAlignment(Pos.CENTER);
  
        Scene shopRegisterScene = new Scene(layout, 300, 300);
        stage.setTitle("Register Shop");
        stage.setScene(shopRegisterScene);
    }

    public boolean loginShop(TextField phone){
        if(phone.getText().trim().isEmpty()){
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText("Please complete all the fields");
            message.showAndWait();  
            return false;     
        }else{
            boolean isSuccess = false;
            String shopPhone = phone.getText();
            Login login = new Login();

            if(login.checkShopExist(shopPhone)){
                shop = login.loginShop(shopPhone);
                isSuccess = true;
                Alert message = new Alert(AlertType.INFORMATION);
                message.setHeaderText("Successs");
                message.setContentText("Login successfully");
                message.showAndWait();    
            }else{
                Alert message = new Alert(AlertType.ERROR);
                message.setHeaderText("ERROR");
                message.setContentText("Shop not found");
                message.showAndWait();    
            }

            return isSuccess;
        }
    }

    public boolean registerShop(TextField phone, TextField shop, TextField manager){
        if(phone.getText().trim().isEmpty() || shop.getText().trim().isEmpty() || manager.getText().trim().isEmpty()){
          Alert message = new Alert(AlertType.ERROR);
          message.setHeaderText("ERROR");
          message.setContentText("Please complete all the fields");
          message.showAndWait();  
          return false;
        }else{
          Register registerShop = new Register();
          registerShop.insertDataShop(phone.getText(), shop.getText(), false, manager.getText());
          return true;
        }
    }
}
