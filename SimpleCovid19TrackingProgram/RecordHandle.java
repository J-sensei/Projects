import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class RecordHandle extends Record{

    private final String filename = "RecordLog.csv";

    public RecordHandle(){}

    public RecordHandle(Customer customer, Shop shop){
        super(customer, shop);
    }
    //add 30 random records
    public void addRandomRecord(){
        int n = 30; //30 times

        ArrayList<Integer> customerStatus = Customer.getAllCustomerStatus();
        ArrayList<Boolean> shopStatus = Shop.getAllShopStatus();
        if(!customerStatus.contains(1)){// if there is no normal case customer
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText("All customer is either close or case");
            message.showAndWait();    
        }else if(!shopStatus.contains(false)){ //all shop is in case
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText("All shop is case");
            message.showAndWait();   
        }else{
            Random random = new Random();
            for(int i = 0; i < n; ++i){
                int randomCustomerId = 0;
                int randomShopId = 0;
                int randomMinute = 0;
                LocalDateTime randomDateTime = LocalDateTime.now();
                do{
                    int totalRow = 0;
                    try{
                        totalRow = FileCsv.getRowNumber("CustomerLog.csv");
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    randomCustomerId = random.nextInt(totalRow) + 1;
                }while(Customer.loadDataFromId(randomCustomerId).getStatus() != 1);
                do{
                    int totalRow = 0;
                    try{
                        totalRow = FileCsv.getRowNumber("ShopLog.csv");
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    randomShopId = random.nextInt(totalRow) + 1;
                }while(Shop.loadDataFromId(randomShopId).getStatus() != false);
                randomMinute = random.nextInt(1441); // 0 -> 1440 minutes
                randomDateTime = randomDateTime.minusMinutes(randomMinute);
                insertSpecificRecord(Customer.loadDataFromId(randomCustomerId),
                                    Shop.loadDataFromId(randomShopId), randomDateTime);
            }
            Alert message = new Alert(AlertType.INFORMATION);
            message.setHeaderText("Success");
            message.setContentText("Random Check in successfully");
            message.showAndWait();  
        }
    }
    //check in shop
    public void checkIn(){
        if(getCustomer().getStatus() == 1){ //status is normal
            insertRecord();
        }else{
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText("Your status is not normal!");
            message.showAndWait();  
        }
    }
    private void insertRecord(){
        FileCsv.checkFileExist(filename);
        Record record;
        int id;
        try{
            id = FileCsv.getRowNumber(filename) + 1;
            record = new Record(id, getCustomer(), getShop(), LocalDateTime.now());
            FileCsv.writeFileCsv(filename, record);
        }catch(IOException e){
            e.printStackTrace();
        }
        Alert message = new Alert(AlertType.INFORMATION);
        message.setHeaderText("Success");
        message.setContentText("Check In successfully");
        message.showAndWait();  
    }
    private void insertSpecificRecord(Customer customer, Shop shop, LocalDateTime dateTime){
        FileCsv.checkFileExist(filename);  
        Record record;
        int id;
        try{
            id = FileCsv.getRowNumber(filename) + 1;
            record = new Record(id, customer, shop, dateTime);
            FileCsv.writeFileCsv(filename, record);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private Record getRecordDate(int id){
        Record r;
        String[] data = null;
        try{
            data = FileCsv.searchCsvResult(filename, 0, Integer.toString(id));
        }catch(IOException e){
            e.printStackTrace();
        }
        LocalDateTime dateTime = LocalDateTime.parse(data[3], getFormat());
        r = new Record(Integer.parseInt(data[0]), Customer.loadDataFromId(Integer.parseInt(data[1])),
                     Shop.loadDataFromId(Integer.parseInt(data[2])), dateTime);
        return r;
    }

    public ObservableList<Record> getRecordTable(){
        ObservableList<Record> result = FXCollections.observableArrayList();    
        
        if(FileCsv.isFileFound(filename)){
            int row = 0;;
            try{
                row = FileCsv.getRowNumber(filename);
            }catch(IOException e){
                e.printStackTrace();
            }
            for(int i = 0; i < row; ++i){
                Record temp = getRecordDate(i + 1);
                result.add(temp);
            }
        }else{
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText(filename + "is not found");
            message.showAndWait();  
        } 

        return result;
    }

    public ObservableList<Record> getCustomerRecord(Customer customer){
        ObservableList<Record> customerRecord = FXCollections.observableArrayList();
        if(FileCsv.isFileFound(filename)){
            int row = 0;;
            try{
                row = FileCsv.getRowNumber(filename);
            }catch(IOException e){
                e.printStackTrace();
            }
            for(int i = 0; i < row; ++i){
                Record temp = getRecordDate(i + 1);
                if(temp.getCustomer().getId() == customer.getId())
                    customerRecord.add(temp);
            }
        }else{
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText(filename + "is not found");
            message.showAndWait();  
        }

        return customerRecord;
    }

    //get all customer id that visited this shop in time range
    public ArrayList<Integer> getVisitedCustomerId(Shop shop, LocalDateTime dateTime){
        ArrayList<Integer> result = new ArrayList<Integer>();

        if(FileCsv.isFileFound(filename)){
            int row = 0;;
            try{
                row = FileCsv.getRowNumber(filename);
            }catch(IOException e){
                e.printStackTrace();
            }
            for(int i = 0; i < row; ++i){
                Record temp = getRecordDate(i + 1);
                if(temp.getShop().getId() == shop.getId()){ //check if this customer is visited this shop
                    LocalDateTime visitedTime = temp.getDateTime();

                    if(visitedTime.isAfter(dateTime.minusMinutes(60 + 1)) &&  //if this customer is inside time range
                        visitedTime.isBefore(dateTime.plusMinutes(60 + 1))){
                            result.add(temp.getCustomer().getId());
                    }
                    
                }
            }  
        }else{
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText(filename + "is not found");
            message.showAndWait();          
        }

        return removeDuplicates(result);
    }

    public ArrayList<LocalDateTime> getVisitedTime(Customer customer, Shop shop){ //get date time of the customer visited a shop
        ArrayList<LocalDateTime> result = new ArrayList<LocalDateTime>();
        if(FileCsv.isFileFound(filename)){
            int row = 0;;
            try{
                row = FileCsv.getRowNumber(filename);
            }catch(IOException e){
                e.printStackTrace();
            }
            for(int i = 0; i < row; ++i){
                Record temp = getRecordDate(i + 1);
                if(temp.getCustomer().getId() == customer.getId()){
                    if(temp.getShop().getId() == shop.getId()){
                        result.add(temp.getDateTime());
                    }
                }
            }  
        }else{
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText(filename + "is not found");
            message.showAndWait();        
        }
        /*
        for(LocalDateTime x : result){
            System.out.println("Date Time:" + x);
        }*/
        
        return result;
    }

    //get specific shop id for a customer
    public ArrayList<Integer> getVisitedShopId(Customer customer){
        ArrayList<Integer> result = new ArrayList<Integer>();

        if(FileCsv.isFileFound(filename)){
            int row = 0;;
            try{
                row = FileCsv.getRowNumber(filename);
            }catch(IOException e){
                e.printStackTrace();
            }
            for(int i = 0; i < row; ++i){
                Record temp = getRecordDate(i + 1);
                if(temp.getCustomer().getId() == customer.getId())
                    result.add(temp.getShop().getId());
            }  
        }else{
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("ERROR");
            message.setContentText(filename + "is not found");
            message.showAndWait();         
        }
        //return result;
        return removeDuplicates(result);
    }

    private ArrayList<Integer> removeDuplicates(ArrayList<Integer> arr){
        ArrayList<Integer> newArr = new ArrayList<Integer>();
        for(int value : arr){
            if(!newArr.contains(value))
                newArr.add(value);
        }
        return newArr;
    }
}