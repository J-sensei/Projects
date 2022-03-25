import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer extends Person{
    private int id; //unique id
    private String password; // customer need password to login
    private String statusString;

    public Customer(int id, String name, String phone, int status, String password){
        super(phone, name, status);
        this.id = id;
        this.password = password;
    }

    //static
    public static Customer loadDataFromId(int id){
        String[] result = null;
        Customer customer;
        try{
            result = FileCsv.searchCsvResult("CustomerLog.csv", 0, Integer.toString(id));
        }catch(IOException e){
            e.printStackTrace();
        }

        customer = new Customer(Integer.parseInt(result[0]), result[2], result[1],
                                 Integer.parseInt(result[3]), result[4]);
        //System.out.println("Data: " + customer);//test
        return customer;
    }

    //getter
    public int getId(){return id;}
    public String getStatusString(){
        switch(getStatus()){
            case 1:
                statusString = "Normal";
                break;
            case 2:
                statusString = "Case";
                break;
            case 3:
                statusString = "Close";
                break;
        }
        return statusString;
    }
    //get all Customer status
    public static ArrayList<Integer> getAllCustomerStatus(){
        final String filename = "CustomerLog.csv";
        ArrayList<Integer> result = new ArrayList<>();

        int row = 0;
        try{
            row = FileCsv.getRowNumber(filename);
        }catch(IOException e){
            e.printStackTrace();
        }
        for(int i = 0; i < row; ++i){
            Customer tempCustomer = loadDataFromId(i + 1);
            result.add(tempCustomer.getStatus());
        }

        return result;
    }
    //get Customer Table
    public static ObservableList<Customer> getAllCustomerTable(){
        final String filename = "CustomerLog.csv";
        ObservableList<Customer> customerRecord = FXCollections.observableArrayList();
        int row = 0;
        try{
            row = FileCsv.getRowNumber(filename);
        }catch(IOException e){
            e.printStackTrace();
        }

        for(int i = 0; i < row; ++i){
            Customer tempCustomer = loadDataFromId(i + 1);
            customerRecord.add(tempCustomer);
        }

        return customerRecord;
    }

    @Override
    public String toString(){
        return id + "," + super.getPhone() + "," + super.getName() + "," + super.getStatus() + "," + password;
    }
}
