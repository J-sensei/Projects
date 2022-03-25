import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Shop {
    private int id;
    private String phone;
    private String shopName;
    private boolean status; //true -> case, false -> normal
    private String statusString;
    private Manager manager;
    //Check in button
    private Button checkInBtn;

    public Shop(int id, String phone, String shopName, boolean status, String name){
        this.id = id;
        this.shopName = shopName;
        this.status = status;
        try{
            setPhone(phone);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        manager = new Manager(name);
        if(this.status == false)
            checkInBtn = new Button("Check In");
    }

    public boolean checkPhoneInput(String phone){
        boolean result = true;

        //test if every char is number
        for(int i = 0; i < phone.length(); ++i){
            if(!Character.isDigit(phone.charAt(i))){ //if contains non number char
                result = false;
            }
        }

        return result;
    }

    //static
    public static Shop loadDataFromId(int id){
        String[] result = null;
        Shop shop;
        try{
            result = FileCsv.searchCsvResult("ShopLog.csv", 0, Integer.toString(id));
        }catch(IOException e){
            e.printStackTrace();
        }

        shop = new Shop(Integer.parseInt(result[0]), result[1], result[2], Boolean.parseBoolean(result[4]),
                result[3]);
        
        return shop;
    }

    public void changeShopStatus(LocalDateTime dateTime){ //change to case, give dateTime for that specific customer
        final String filename = "ShopLog.csv";

        if(FileCsv.isFileFound(filename)){
            boolean status = true;
            setStatus(status);//set shop to case

            try{
                FileCsv.changeFileCsv(filename, this, id); //save in csv
            }catch(IOException e){
                e.printStackTrace();
            }

            //check and change customer status
            RecordHandle r = new RecordHandle();
            ArrayList<Integer> customerIdList = r.getVisitedCustomerId(this, dateTime);//case customer list

            for(int id : customerIdList){//loop customer list
                Customer closeCustomer = Customer.loadDataFromId(id);
                if(closeCustomer.getStatus() == 1)//if this customer is normal
                    closeCustomer.setStatus(3); // set to close
                try{
                    FileCsv.changeFileCsv("CustomerLog.csv", closeCustomer, id); //save into csv file
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

        }else{
            System.out.println("ShopLog.csv is not found");
        }
    }

    //setter
    public void setPhone(String phone) throws IllegalArgumentException{
        if(checkPhoneInput(phone))
            this.phone = phone;
        else
            throw new IllegalArgumentException("Phone must be number");
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public void setButtonAction(Customer customer){
        checkInBtn.setOnAction(e -> {
            RecordHandle record = new RecordHandle(customer, this);
            record.checkIn();
        });
    }

    //getter
    public static ArrayList<Boolean> getAllShopStatus(){
        final String filename = "ShopLog.csv";
        ArrayList<Boolean> result = new ArrayList<>();

        int row = 0;
        try{
            row = FileCsv.getRowNumber(filename);
        }catch(IOException e){
            e.printStackTrace();
        }
        for(int i = 0; i < row; ++i){
            Shop tempShop = loadDataFromId(i + 1);
            result.add(tempShop.getStatus());
        }

        return result;
    }
    public static ObservableList<Shop> getShop(){
        final String filename = "ShopLog.csv";
        ObservableList<Shop> shopRecord = FXCollections.observableArrayList();
        int row = 0;
        try{
            row = FileCsv.getRowNumber(filename);
        }catch(IOException e){
            e.printStackTrace();
        }

        for(int i = 0; i < row; ++i){
            Shop tempShop = loadDataFromId(i + 1);
            shopRecord.add(tempShop);
        }

        return shopRecord;
    }
    //get table shop specific for customer to checkIn
    public static ObservableList<Shop> getAllShopTable(Customer customer){
        final String filename = "ShopLog.csv";
        ObservableList<Shop> shopRecord = FXCollections.observableArrayList();
        int row = 0;
        try{
            row = FileCsv.getRowNumber(filename);
        }catch(IOException e){
            e.printStackTrace();
        }

        for(int i = 0; i < row; ++i){
            String[] tempValue = null;
            try{
                tempValue = FileCsv.searchCsvResult(filename, 0, Integer.toString(i + 1));
            }catch(IOException e){
                e.printStackTrace();
            }
            Shop tempShop = new Shop(Integer.parseInt(tempValue[0]), 
                                    tempValue[1], tempValue[2],
                                     Boolean.parseBoolean(tempValue[4]), tempValue[3]);
            if(tempShop.getStatus() == false)
                tempShop.setButtonAction(customer);
            shopRecord.add(tempShop);
        }

        return shopRecord;
    }

    public int getId(){return id;}
    public String getPhone(){return phone;}
    public String getManager(){return manager.getName();}
    public String getShopName(){return shopName;}
    public Button getCheckInBtn(){return checkInBtn;}
    public boolean getStatus(){return status;}
    public String getStatusString(){
        if(status)
            statusString = "Case";
        else
            statusString = "Normal";
        return statusString;
    }

    @Override
    public String toString(){
        return id + "," + phone + "," + shopName + "," + manager.getName() + "," + 
                status;
    }
}
