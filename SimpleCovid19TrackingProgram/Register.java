import java.io.IOException;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

public class Register {
    private Customer customer;
    private Shop shop;
    private final String filenameCustomer = "CustomerLog.csv";
    private final String filenameShop = "ShopLog.csv";

    public Register(){
        FileCsv.checkFileExist(filenameCustomer);
        FileCsv.checkFileExist(filenameShop);
    }

    public Register(String name, String phone, int status, String password){
        insertDataCustomer(name, phone, status, password);
    }

    public Register(String phone, String shopName, boolean status, String name){
        insertDataShop(phone, shopName, status, name);
    }

    public void insertDataShop(String phone, String shopName, boolean status, String name){
        FileCsv.checkFileExist(filenameShop);
        int id;
        try{
            id = FileCsv.getRowNumber(filenameShop) + 1;
            shop = new Shop(id, phone, shopName, status, name);
            if(shop.checkPhoneInput(phone)){
                if(FileCsv.searchCsvDataExist(filenameShop, 1, phone) == false){
                    FileCsv.writeFileCsv(filenameShop, shop);
                    Alert message = new Alert(AlertType.INFORMATION);
                    message.setHeaderText("Success");
                    message.setContentText("Register Successfully");
                    message.showAndWait();
                }else{
                    Alert message = new Alert(AlertType.ERROR);
                    message.setHeaderText("ERROR");
                    message.setContentText("Phone number has been taken");
                    message.showAndWait();
                }
            }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void insertDataCustomer(String name, String phone, int status, String password){
        FileCsv.checkFileExist(filenameCustomer);
        int id;
        try{
            id = FileCsv.getRowNumber(filenameCustomer) + 1;
            customer = new Customer(id, name, phone, status, password);
            if(customer.checkInputValidity()){
                if(FileCsv.searchCsvDataExist(filenameCustomer, 1, phone) == false){ // 1-> second column = phone                    
                    FileCsv.writeFileCsv(filenameCustomer, customer);
                    Alert message = new Alert(AlertType.INFORMATION);
                    message.setHeaderText("Success");
                    message.setContentText("Register Successfully");
                    message.showAndWait();
                }else{
                    Alert message = new Alert(AlertType.ERROR);
                    message.setHeaderText("ERROR");
                    message.setContentText("Phone number has been taken");
                    message.showAndWait();
                }
            }else{
                Alert message = new Alert(AlertType.ERROR);
                message.setHeaderText("ERROR");
                message.setContentText("Invalid Input");
                message.showAndWait();
            }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
