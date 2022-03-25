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
 
public class AdminApp extends Application {
  public static void main(String[] args) {
      launch(args);
  }
  @Override
  public void start(Stage primaryStage) {
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

      Label titleLabel = new Label("Admin Menu");
      GridPane.setConstraints(titleLabel, 0, 0);
 
      Button exitBtn = new Button("Exit");
      Button masterViewBtn = new Button("Master Visit History");
      Button viewCustomerBtn = new Button("View Customer List");
      Button viewShopBtn = new Button("View Shop List");
 
      masterViewBtn.setOnAction(e -> masterViewPage(stage));
      viewCustomerBtn.setOnAction(e -> viewCustomerPage(stage));
      viewShopBtn.setOnAction(e -> viewShopPage(stage));
      exitBtn.setOnAction(e -> stage.close());

      //set position
 
      GridPane.setConstraints(masterViewBtn, 0, 2);
      GridPane.setConstraints(viewCustomerBtn, 0, 3);
      GridPane.setConstraints(viewShopBtn, 0, 4);
      GridPane.setConstraints(exitBtn, 0, 5);

      layout.getChildren().addAll(titleLabel, masterViewBtn, viewCustomerBtn, viewShopBtn, exitBtn);
      layout.setAlignment(Pos.CENTER);

      Scene menuScene = new Scene(layout, 300, 300);

      stage.setTitle("Admin");
      stage.setScene(menuScene);
  }

  public void viewShopPage(Stage stage){
      //Name column
      TableColumn<Shop, Integer> shopNo = new TableColumn<>("No"); 
      shopNo.setMinWidth(50);
      shopNo.setCellValueFactory(new PropertyValueFactory<>("id"));

      TableColumn<Shop, String> shopName = new TableColumn<>("Name"); 
      shopName.setMinWidth(100);
      shopName.setCellValueFactory(new PropertyValueFactory<>("shopName"));

      TableColumn<Shop, String> shopPhone = new TableColumn<>("Phone"); 
      shopPhone.setMinWidth(100);
      shopPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

      TableColumn<Shop, String> shopManager = new TableColumn<>("Manager"); 
      shopManager.setMinWidth(100);
      shopManager.setCellValueFactory(new PropertyValueFactory<>("manager"));
    
      TableColumn<Shop, String> shopStatus = new TableColumn<>("Status"); 
      shopStatus.setMinWidth(100);
      shopStatus.setCellValueFactory(new PropertyValueFactory<>("statusString"));

      TableView<Shop> shopTable = new TableView<>();
      ObservableList<Shop> shopData = Shop.getShop();

      shopTable.setItems(shopData);
      shopTable.getColumns().addAll(Arrays.asList(shopNo, shopName, shopPhone, shopManager, shopStatus));

      Button menuBtn = new Button("Main Menu");
      menuBtn.setOnAction(e -> menuPage(stage));
      Button resetBtn = new Button("Reset shop to preload data");
      resetBtn.setOnAction(e -> {
          FileCsv.resetShopRecord();
          Alert message = new Alert(AlertType.INFORMATION);
          message.setHeaderText("Success");
          message.setContentText("Shop record is reset");
          message.showAndWait();  
          viewShopPage(stage);
      });
      Button refreshBtn = new Button("Refresh");
      refreshBtn.setOnAction(e -> viewShopPage(stage));

      GridPane layout = new GridPane();
      layout.setPadding(new Insets(10, 10, 10, 10));  //padding
      layout.setVgap(10); //vertical gap
      layout.setHgap(10); //horizontal gap   

      GridPane.setConstraints(menuBtn, 0, 0);
      GridPane.setConstraints(resetBtn, 0, 1);
      GridPane.setConstraints(refreshBtn, 1, 0);
      GridPane.setConstraints(shopTable, 0, 2);

      layout.getChildren().addAll(menuBtn, resetBtn, refreshBtn, shopTable);

      Scene checkInScene = new Scene(layout);

      stage.setScene(checkInScene);
  }

  public void viewCustomerPage(Stage stage){
      //Name column
      TableColumn<Customer, Integer> customerNo = new TableColumn<>("No");
      customerNo.setMinWidth(50);
      customerNo.setCellValueFactory(new PropertyValueFactory<>("id"));  

      TableColumn<Customer, String> customerName = new TableColumn<>("Name");
      customerName.setMinWidth(100);
      customerName.setCellValueFactory(new PropertyValueFactory<>("name")); 
      
      TableColumn<Customer, String> customerPhone = new TableColumn<>("Phone");
      customerPhone.setMinWidth(100);
      customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone")); 

      TableColumn<Customer, String> customerStatus = new TableColumn<>("Status");
      customerStatus.setMinWidth(50);
      customerStatus.setCellValueFactory(new PropertyValueFactory<>("statusString")); 
  
      TableView<Customer> customerTable = new TableView<>();
      ObservableList<Customer> customerData = Customer.getAllCustomerTable();

      customerTable.setItems(customerData);
      customerTable.getColumns().addAll(Arrays.asList(customerNo, customerName, customerPhone, customerStatus));

      Button menuBtn = new Button("Main Menu");
      menuBtn.setOnAction(e -> menuPage(stage));
      Button resetBtn = new Button("Reset customer to preload data");
      resetBtn.setOnAction(e -> {
          FileCsv.resetCustomerRecord();
          Alert message = new Alert(AlertType.INFORMATION);
          message.setHeaderText("Success");
          message.setContentText("Customer record is reset");
          message.showAndWait();  
          viewCustomerPage(stage);
      });
      Button refreshBtn = new Button("Refresh");
      refreshBtn.setOnAction(e -> viewCustomerPage(stage));
      
      GridPane layout = new GridPane();
      layout.setPadding(new Insets(10, 10, 10, 10));  //padding
      layout.setVgap(10); //vertical gap
      layout.setHgap(10); //horizontal gap   

      GridPane.setConstraints(menuBtn, 0, 0);
      GridPane.setConstraints(resetBtn, 0, 1);
      GridPane.setConstraints(refreshBtn, 1, 0);
      GridPane.setConstraints(customerTable, 0, 2);

      layout.getChildren().addAll(menuBtn, resetBtn, refreshBtn, customerTable);

      Scene viewCustomerScene = new Scene(layout);

      stage.setScene(viewCustomerScene);
  }

  public void masterViewPage(Stage stage){
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

      TableColumn<Record, String> recordCustomer = new TableColumn<>("Customer");
      recordCustomer.setMinWidth(100);
      recordCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));

      TableColumn<Record, String> recordShop = new TableColumn<>("Shop");
      recordShop.setMinWidth(100);
      recordShop.setCellValueFactory(new PropertyValueFactory<>("shopName"));

      TableColumn<Record, String> recordStatus = new TableColumn<>("Customer Status");
      recordStatus.setMinWidth(100);
      recordStatus.setCellValueFactory(new PropertyValueFactory<>("customerStatus"));

      TableColumn<Record, Button> recordFlagBtn = new TableColumn<>("Change Status");
      recordFlagBtn.setMinWidth(100);
      recordFlagBtn.setCellValueFactory(new PropertyValueFactory<>("flagBtn"));

      RecordHandle r = new RecordHandle();

      TableView<Record> recordTable = new TableView<>();
      ObservableList<Record> recordData = r.getRecordTable();

      recordTable.setItems(recordData);
      recordTable.getColumns().addAll(Arrays.asList(recordNo, recordDate, recordTime, recordCustomer, recordShop, recordStatus, recordFlagBtn));

      Button menuBtn = new Button("Main Menu");
      menuBtn.setOnAction(e -> menuPage(stage));
      Button randomAddBtn = new Button("Add Random 30 visits");
      randomAddBtn.setOnAction(e -> {
          r.addRandomRecord();
          masterViewPage(stage);
      });
      Button deleteBtn = new Button("Delete All Records");
      deleteBtn.setOnAction(e -> {
          FileCsv.deleteAllFileContent("RecordLog.csv");
          masterViewPage(stage);
          Alert message = new Alert(AlertType.INFORMATION);
          message.setHeaderText("Success");
          message.setContentText("All record is deleted");
          message.showAndWait();  
      });
      Button refreshBtn = new Button("Refresh");
      refreshBtn.setOnAction(e -> masterViewPage(stage));
  
      GridPane layout = new GridPane();
      layout.setPadding(new Insets(10, 10, 10, 10));  //padding
      layout.setVgap(10); //vertical gap
      layout.setHgap(10); //horizontal gap   

      GridPane.setConstraints(menuBtn, 0, 0);
      GridPane.setConstraints(randomAddBtn, 0, 1);
      GridPane.setConstraints(deleteBtn,1, 1);
      GridPane.setConstraints(refreshBtn, 1, 0);
      GridPane.setConstraints(recordTable, 0, 2);

      layout.getChildren().addAll(menuBtn, randomAddBtn, deleteBtn, refreshBtn, recordTable);

      Scene masterViewScene = new Scene(layout);

      stage.setScene(masterViewScene);
  } 
}
