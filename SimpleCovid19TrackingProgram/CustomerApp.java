import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*; // Button, Label
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
 
public class CustomerApp extends Application {

    private Customer customer;
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

        Label titleLabel = new Label("Customer Menu");
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
        layout.setPadding(new Insets(10, 10, 10, 10));  //padding
        layout.setVgap(10); //vertical gap
        layout.setHgap(10); //horizontal gap   
        
        Label details = new Label("Welcome back, " + customer.getName() + "(+6" + customer.getPhone() + ")"); 
        Label status = new Label("Status: " + customer.getStatusString());
        Button menuBtn = new Button("Menu");
        menuBtn.setOnAction(e -> menuPage(stage));
        Button checkInBtn = new Button("Check-in Shop");
        checkInBtn.setOnAction(e -> {
            if(customer.getStatus() == 1)//if normal case csutomer can checkin
                checkInPage(stage);
            else{
                Alert message = new Alert(AlertType.ERROR);
                message.setHeaderText("ERROR");
                message.setContentText("Your status is not normal!");
                message.showAndWait();  
            }
        });
        Button viewBtn = new Button("View Record");
        viewBtn.setOnAction(e -> viewPage(stage));

        GridPane.setConstraints(details, 0, 0);
        GridPane.setConstraints(status, 0, 1);
        GridPane.setConstraints(checkInBtn, 0, 2);
        GridPane.setConstraints(viewBtn, 0, 3);
        GridPane.setConstraints(menuBtn, 0, 4);

        layout.getChildren().addAll(details, status, checkInBtn, menuBtn, viewBtn);
        layout.setAlignment(Pos.CENTER);

        Scene mainMenuScene = new Scene(layout, 500, 300);

        stage.setTitle("Main Menu");
        stage.setScene(mainMenuScene);
    }

    public void viewPage(Stage stage){
        //Name column
        TableColumn<Record, Integer> recordNo = new TableColumn<>("No");
        recordNo.setMinWidth(50);
        recordNo.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Record, String> recordDate = new TableColumn<>("Date");
        recordDate.setMinWidth(100);
        recordDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Record, String> recordTime = new TableColumn<>("Time");
        recordTime.setMinWidth(100);
        recordTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Record, String> recordShop = new TableColumn<>("Shop");
        recordShop.setMinWidth(100);
        recordShop.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        RecordHandle r = new RecordHandle();

        TableView<Record> recordTable = new TableView<>();
        ObservableList<Record> recordData = r.getCustomerRecord(customer);

        recordTable.setItems(recordData);
        recordTable.getColumns().addAll(Arrays.asList(recordNo, recordDate, recordTime, recordShop));

        Button menuBtn = new Button("Main Menu");
        menuBtn.setOnAction(e -> mainMenu(stage));
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> viewPage(stage));

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));  //padding
        layout.setVgap(10); //vertical gap
        layout.setHgap(10); //horizontal gap   

        GridPane.setConstraints(menuBtn, 0, 0);
        GridPane.setConstraints(refreshBtn, 1, 0);
        GridPane.setConstraints(recordTable, 0, 1);

        layout.getChildren().addAll(menuBtn, refreshBtn, recordTable);

        Scene checkInScene = new Scene(layout);

        stage.setScene(checkInScene);
    }

    public void checkInPage(Stage stage){
        //Name column
        TableColumn<Shop, Integer> shopNo = new TableColumn<>("No"); 
        shopNo.setMinWidth(50);
        shopNo.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Shop, String> shopName = new TableColumn<>("Shop"); 
        shopName.setMinWidth(100);
        shopName.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        TableColumn<Shop, Button> checkInBtn = new TableColumn<>("Check In");
        checkInBtn.setMinWidth(100);
        checkInBtn.setCellValueFactory(new PropertyValueFactory<>("checkInBtn"));

        TableView<Shop> shopTable = new TableView<>();
        ObservableList<Shop> shopData = Shop.getAllShopTable(customer);

        shopTable.setItems(shopData);
        shopTable.getColumns().addAll(Arrays.asList(shopNo, shopName, checkInBtn));

        Button menuBtn = new Button("Main Menu");
        menuBtn.setOnAction(e -> mainMenu(stage));
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> checkInPage(stage));

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));  //padding
        layout.setVgap(10); //vertical gap
        layout.setHgap(10); //horizontal gap   

        GridPane.setConstraints(menuBtn, 0, 0);
        GridPane.setConstraints(refreshBtn, 1, 0);
        GridPane.setConstraints(shopTable, 0, 1);

        layout.getChildren().addAll(menuBtn, refreshBtn, shopTable);

        Scene checkInScene = new Scene(layout);

        stage.setScene(checkInScene);
    }

    public void loginPage(Stage stage){
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(8);
        layout.setHgap(10);

        Label phoneLabel = new Label("Phone: ");
        Label passwordLabel = new Label("Password: ");

        GridPane.setConstraints(phoneLabel, 0, 0);
        GridPane.setConstraints(passwordLabel, 0, 1);

        TextField phone = new TextField();
        phone.setPromptText("eg. 0122345678");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        GridPane.setConstraints(phone, 1, 0);
        GridPane.setConstraints(password, 1, 1);

        Button menuBtn = new Button("Menu");
        Button loginBtn = new Button("Login");

        menuBtn.setOnAction(e -> menuPage(stage));
        loginBtn.setOnAction(e -> {
            if(loginCustomer(phone, password)){
                mainMenu(stage);
            }});

        GridPane.setConstraints(menuBtn, 0,3);
        GridPane.setConstraints(loginBtn, 1,3);

        layout.getChildren().addAll(phoneLabel, passwordLabel, phone, password, menuBtn, loginBtn);
        layout.setAlignment(Pos.CENTER);

        Scene loginScene = new Scene(layout, 300, 300);
        stage.setTitle("Login Customer");
        stage.setScene(loginScene);
    }

    public void registerPage(Stage stage){
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));  //padding
        layout.setVgap(8); //vertical gap
        layout.setHgap(10); //horizontal gap

        Label phoneLabel = new Label("Phone: ");
        Label nameLabel = new Label("Name: ");
        Label passwordLabel = new Label("Password: ");

        GridPane.setConstraints(phoneLabel, 0, 0); //top left, first row first col
        GridPane.setConstraints(nameLabel, 0, 1); //second row first col
        GridPane.setConstraints(passwordLabel, 0, 2);//third row

        TextField phone = new TextField();
        phone.setPromptText("eg. 0122345678");
        TextField name = new TextField();
        name.setPromptText("Name");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        

        GridPane.setConstraints(phone, 1, 0);
        GridPane.setConstraints(name, 1, 1);
        GridPane.setConstraints(password, 1, 2);

        Button menuBtn = new Button("Menu");
        Button registerBtn = new Button("Register");

        menuBtn.setOnAction(e -> menuPage(stage));
        registerBtn.setOnAction(e -> {
            if(registerCustomer(phone, name, password)){
                phone.clear();
                name.clear();
                password.clear();
            }});

        GridPane.setConstraints(menuBtn, 0,3);
        GridPane.setConstraints(registerBtn, 1,3);

        layout.getChildren().addAll(phoneLabel, nameLabel, passwordLabel,
                                    phone, name, password,
                                    menuBtn, registerBtn);
        layout.setAlignment(Pos.CENTER);

        Scene registerScene = new Scene(layout, 300, 300);

        stage.setTitle("Register Customer");
        stage.setScene(registerScene);
    }
    
    public boolean loginCustomer(TextField phone, PasswordField password){
        if(phone.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText("Please complete all the fields");
            message.showAndWait();  
            return false;         
        }else{
            boolean isSuccess = false;
            String cusPhone = phone.getText();
            String cusPassword = password.getText();
            Login login = new Login();

            if(login.checkCustomerPhone(cusPhone)){
                if(login.checkCustomerPassword(cusPhone, cusPassword)){
                    customer = login.loginCustomer(cusPhone, cusPassword);
                    isSuccess = true;
                    Alert message = new Alert(AlertType.INFORMATION);
                    message.setHeaderText("Success");
                    message.setContentText("Login Successfully");
                    message.showAndWait();   
                }else{
                    Alert message = new Alert(AlertType.ERROR);
                    message.setHeaderText("ERROR");
                    message.setContentText("Wrong Password, please retry");
                    message.showAndWait();                     
                }
            }else{
                Alert message = new Alert(AlertType.ERROR);
                message.setHeaderText("ERROR");
                message.setContentText("Customer not found in the list");
                message.showAndWait();        
            }

            return isSuccess;
        }
    }

    public boolean registerCustomer(TextField phone, TextField name, PasswordField password){
        if(phone.getText().trim().isEmpty() || name.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText("Please complete all the fields");
            message.showAndWait();
            return false;
        }else{
            Register registerCustomer = new Register();
            registerCustomer.insertDataCustomer(name.getText(), phone.getText(), 1, password.getText()); //by default case is normal
            return true;
        }
    }
}
