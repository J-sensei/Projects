import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

public class Record {
    private int id;
    private Customer customer;
    private Shop shop;
    private LocalDateTime dateTime;
    private DateTimeFormatter format;

    //for table
    private String time;
    private String date;
    //private String customerName;
    private String shopName;
    private String customerName;
    private String customerStatus;

    private Button flagBtn;

    public Record(){
        format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");//format
        //dateTime.format(format);
    }

    public Record(Customer customer, Shop shop){
        this();
        this.customer = customer;
        this.shop = shop;
        if(customer.getStatus() == 1){ //normal
            flagBtn = new Button("Flag Status");
            flagBtn.setOnAction(e -> {
                setRecordToCase();
                Alert message = new Alert(AlertType.INFORMATION);
                message.setHeaderText("Success");
                message.setContentText("Record(ID:" + this.id + ") is changed to case, please refresh the record list");
                message.showAndWait();
            }); //give this button an event
        }
    }

    public Record(int id, Customer customer, Shop shop, LocalDateTime dateTime){
        this(customer, shop);
        this.id = id;
        this.dateTime = dateTime;
    }

    //set this record to case
    public void setRecordToCase(){
        customer.setStatus(2);//set this customer to case
        try{
            FileCsv.changeFileCsv("CustomerLog.csv", customer, customer.getId()); //save into csv file
        }catch(IOException e){
            e.printStackTrace();
        }
        shop.changeShopStatus(dateTime);
    }

    //getter
    public int getId(){return id;}
    public Customer getCustomer(){return customer;}
    public Shop getShop(){return shop;}
    public LocalDateTime getDateTime(){return dateTime;}
    public String getDate(){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime temp;
        temp = dateTime;
        date = temp.format(dateFormat);
        return date;
    }
    public String getTime(){
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime temp;
        temp = dateTime;
        time = temp.format(timeFormat);
        return time;
    }
    public String getShopName(){
        shopName = shop.getShopName();
        return shopName;
    }
    public String getCustomerName(){
        customerName = customer.getName();
        return customerName;
    }
    public String getCustomerStatus(){
        customerStatus = customer.getStatusString();
        return customerStatus;
    }
    public Button getFlagBtn(){return flagBtn;}
    public DateTimeFormatter getFormat(){return format;};

    @Override
    public String toString(){
        return id + "," + customer.getId() + "," + shop.getId() + "," + dateTime.format(format);
    }
}
