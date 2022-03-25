// Soon Jie Kang (1201301760)

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewReportController implements Initializable{
    @FXML private TableView<Property> tableView;
    @FXML private TableColumn<Property, Integer> idColumn;
    @FXML private TableColumn<Property, String> ownerColumn;
    @FXML private TableColumn<Property, Float> sizeColumn;
    @FXML private TableColumn<Property, Float> priceColumn;
    @FXML private TableColumn<Property, Float> rateColumn;
    @FXML private TableColumn<Property, String> addressColumn;
    @FXML private TableColumn<Property, String> postcodeColumn;
    @FXML private TableColumn<Property, String> typeColumn;
    @FXML private TableColumn<Property, String> statusColumn;
    @FXML private TableColumn<Property, Integer> noofRoomColumn;
    @FXML private TableColumn<Property, Integer> noofBathroomColumn;
    @FXML private TableColumn<Property, String> swimmingpoolColumn;
    @FXML private TableColumn<Property, String> airconColumn;
    @FXML private TableColumn<Property, String> wifiColumn;
    @FXML private TableColumn<Property, String> waterheaterColumn;
    @FXML private TableColumn<Property, String> commentColumn;

    @FXML private ComboBox<String> optionComboBox;
    @FXML private ComboBox<String> searchComboBox;
    @FXML private Button mainMenuButton;
    @FXML private Button viewAllButton;
    @FXML private TextField searchText;
    @FXML private CheckBox checkActive;
    @FXML private CheckBox checkInactive;
    @FXML private CheckBox checkHidden;
    @FXML private CheckBox checkSwimYes;
    @FXML private CheckBox checkSwimNo;
    @FXML private CheckBox checkAirconYes;
    @FXML private CheckBox checkAirconNo;
    @FXML private CheckBox checkWifiYes;
    @FXML private CheckBox checkWifiNo;
    @FXML private CheckBox checkWaterYes;
    @FXML private CheckBox checkWaterNo;
    @FXML private CheckBox checkCommentYes;
    @FXML private CheckBox checkCommentNo;
    
    private LinkedList<Property> defaultList= Property.getPropertyList();
    
    ObservableList<Property> propertyList = FXCollections.observableArrayList(defaultList);
    
    FilteredList<Property> filteredList = new FilteredList<>(propertyList, p -> true);
    SortedList<Property> sortableData = new SortedList<>(filteredList);
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set the combo box value for option and search
        optionComboBox.getItems().addAll("Type of Property","According to Rental Rates","According to Status","According to Project");
        searchComboBox.getItems().addAll("Owner/Agent","Address","Postcode","Type");
        //setting up the table data
        idColumn.setCellValueFactory(new PropertyValueFactory<Property, Integer>("id"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("username"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Property, Float>("size"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Property, Float>("price"));
        priceColumn.setCellFactory(c -> new TableCell<Property, Float>() {
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

        rateColumn.setCellValueFactory(new PropertyValueFactory<Property, Float>("rentalRate"));
        rateColumn.setCellFactory(c -> new TableCell<Property, Float>() {
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

        addressColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        postcodeColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("postcode"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("type"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("status"));
        noofRoomColumn.setCellValueFactory(new PropertyValueFactory<Property, Integer>("noRoom"));
        noofBathroomColumn.setCellValueFactory(new PropertyValueFactory<Property, Integer>("noBathRoom"));
        swimmingpoolColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("swimmingPool"));
        airconColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("airCon"));
        wifiColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("wifi"));
        waterheaterColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("waterHeater"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("comment"));
        
        //setting up the filter list for search function
        tableView.setItems(sortableData);
        
        //List of all filter predicate
        Predicate<Property> activeAll = (property ->{
            if(checkActive.isSelected() && 
                    checkInactive.isSelected() && 
                    checkHidden.isSelected() &&
                    checkSwimYes.isSelected() && 
                    checkSwimNo.isSelected() && 
                    checkAirconYes.isSelected() && 
                    checkAirconNo.isSelected() &&
                    checkWifiYes.isSelected() && 
                    checkWifiNo.isSelected() && 
                    checkWaterYes.isSelected() &&
                    checkWaterNo.isSelected()
                    ){
                    return true;
                }
            
            return false;
        });

        Predicate<Property> isActive = (property ->{
            if(!checkActive.isSelected() && property.getStatus().toLowerCase().contains("hidden")){
                return true;
            }

            if(!checkActive.isSelected() && !(property.getStatus().toLowerCase().contains("inactive"))){
                return false;
            }
            
            return true;
        });

        Predicate<Property> isInactive = (property ->{

            if(!checkInactive.isSelected() && property.getStatus().toLowerCase().contains("inactive")){
                return false;
            }
            
            return true;
        });

        Predicate<Property> isHidden = (property ->{

            if(!checkHidden.isSelected() && property.getStatus().toLowerCase().contains("hidden")){
                return false;
            }
            return true;
        });


        Predicate<Property> isSwimmingPoolYes = (property ->{
            if( !checkSwimYes.isSelected() && property.getSwimmingPool().toLowerCase().contains("yes")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> isSwimmingPoolNo = (property ->{
            if( !checkSwimNo.isSelected() && property.getSwimmingPool().toLowerCase().contains("no")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> isAirconYes = (property ->{
            if( !checkAirconYes.isSelected() && property.getAirCon().toLowerCase().contains("yes")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> isAirconNo = (property ->{
            if( !checkAirconNo.isSelected() && property.getAirCon().toLowerCase().contains("no")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> isWifiYes = (property ->{
            if( !checkWifiYes.isSelected() && property.getWifi().toLowerCase().contains("yes")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> isWifiNo = (property ->{
            if( !checkWifiNo.isSelected() && property.getWifi().toLowerCase().contains("no")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> isWaterHeaterYes = (property ->{
            if( !checkWaterYes.isSelected() && property.getWaterHeater().toLowerCase().contains("yes")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> isWaterHeaterNo = (property ->{
            if( !checkWaterNo.isSelected() && property.getWaterHeater().toLowerCase().contains("no")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> isCommentYes = (property ->{
            if( !checkCommentYes.isSelected() && !property.getComment().toLowerCase().contains("-")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> isCommentNo = (property ->{
            if( !checkCommentNo.isSelected() && property.getComment().toLowerCase().contains("-")){
                return false;
            }
        
            return true;
        });

        Predicate<Property> filter = (property -> {
            if( searchText.getText()== null || searchText.getText().trim().isEmpty()){
                return true;
            }

           

            switch(searchComboBox.getValue()){
                case"Owner/Agent":
                    if(property.getUsername().toLowerCase().contains(searchText.getText())){
                        return true;
                    }

                case"Address":
                    if(property.getAddress().toLowerCase().contains(searchText.getText())){
                        return true;
                    }

                case"Postcode":
                    if(Integer.toString(property.getPostcode()).contains(searchText.getText())){
                        return true;
                    }

                case"Type":
                    if(property.getType().toLowerCase().contains(searchText.getText())){
                        return true;
                    }
                
            }
            return false;

        });

        //listener for the checkbox function
        ChangeListener<Boolean> listener=((observable, oldValue, newValue) ->{
            filteredList.setPredicate( (isActive).and(isInactive).and(isHidden).and(isSwimmingPoolYes).and(isSwimmingPoolNo).and(isAirconYes).and(isAirconNo).and(isWifiYes).and(isWifiNo).and(isWaterHeaterYes).and(isWaterHeaterNo).and(isCommentYes).and(isCommentNo).and(filter) );
        });

        //listener for the search textfield
        ChangeListener<String> listener2=((observable, oldValue, newValue) ->{
            filteredList.setPredicate( (isActive).and(isInactive).and(isHidden).and(isSwimmingPoolYes).and(isSwimmingPoolNo).and(isAirconYes).and(isAirconNo).and(isWifiYes).and(isWifiNo).and(isWaterHeaterYes).and(isWaterHeaterNo).and(isCommentYes).and(isCommentNo).and(filter) );
        });

        
        checkActive.selectedProperty().addListener(listener);
        checkInactive.selectedProperty().addListener(listener);
        checkHidden.selectedProperty().addListener(listener);
        checkSwimYes.selectedProperty().addListener(listener);
        checkSwimNo.selectedProperty().addListener(listener);
        checkAirconYes.selectedProperty().addListener(listener);
        checkAirconNo.selectedProperty().addListener(listener);
        checkWifiYes.selectedProperty().addListener(listener);
        checkWifiNo.selectedProperty().addListener(listener);
        checkWaterYes.selectedProperty().addListener(listener);
        checkWaterNo.selectedProperty().addListener(listener);
        checkCommentYes.selectedProperty().addListener(listener);
        checkCommentNo.selectedProperty().addListener(listener);
        searchText.textProperty().addListener(listener2);
        
        viewAllButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e) {
                checkActive.setSelected(true);
                checkInactive.setSelected(true);
                checkHidden.setSelected(true);;
                checkSwimYes.setSelected(true);
                checkSwimNo.setSelected(true);
                checkAirconYes.setSelected(true);
                checkAirconNo.setSelected(true);
                checkWifiYes.setSelected(true);
                checkWifiNo.setSelected(true);
                checkWaterYes.setSelected(true);
                checkWaterNo.setSelected(true);
                searchText.setText(null);
                filteredList.setPredicate(activeAll);
            }
        });
    }

    //setting up data according to the user
    public void setTableData(String user) {
        LinkedList<Property> selectedList = new LinkedList<>();
        switch(user){
            case"admin":{
                break;
            }

            case"potentialTenant":{
            for(int i=0 ; i< defaultList.size() ; i++){
                if(defaultList.get(i).getStatus().toLowerCase().contains("active") || defaultList.get(i).getStatus().toLowerCase().contains("inactive")){
                    selectedList.add(defaultList.get(i));
                }
            }
            propertyList.setAll(FXCollections.observableArrayList(selectedList));
            checkHidden.setVisible(false);
            break;
            }
            
            default:{
                for(int i=0 ; i< defaultList.size() ; i++){
                    if(defaultList.get(i).getUsername().toLowerCase().contains(user)){
                        selectedList.add(defaultList.get(i));
                    }
                }
                propertyList.setAll(FXCollections.observableArrayList(selectedList));
            }
        }
        
    }


    //sorting function for the sort function
    @FXML
    public void onSortByComparator(){
        sortableData.comparatorProperty().unbind();
        switch(optionComboBox.getValue()){
            case("According to Project"):{
                sortableData.setComparator(new addressComparator());
                break;
            }

            case("Type of Property"):{
                sortableData.setComparator(new typeComparator());
                break;
            }

            case("According to Status"):{
                sortableData.setComparator(new activeComparator());
                break;
            }

            case("According to Rental Rates"):{
                sortableData.setComparator(new rentalComparator());
                break;
            }
        }
        
    }
    
    @FXML
    public void mainMenu() throws IOException{

        if(User.getCurrentUser() instanceof Admin) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
            Scene scene = new Scene(loader.load(), 900, 500);
            Main.getStage().setScene(scene);
        }else if(User.getCurrentUser() instanceof Tenant) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TenantPage.fxml"));
            Scene scene = new Scene(loader.load(), 900, 500);
            Main.getStage().setScene(scene);
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AgentOwnerPage.fxml"));
            Scene scene = new Scene(loader.load(), 900, 500);
            Main.getStage().setScene(scene);
        }
    }
        
}
