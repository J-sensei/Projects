import java.io.IOException;

public class Login {
    private final String filenameCustomer = "CustomerLog.csv";
    private final String filenameShop = "ShopLog.csv";

    private Customer customer;
    private Shop shop;

    public Login(){
        FileCsv.checkFileExist(filenameCustomer);
        FileCsv.checkFileExist(filenameShop);
    }

    public boolean checkCustomerPhone(String phone){
        boolean exist = false;

        try{
            exist = FileCsv.searchCsvDataExist(filenameCustomer, 1, phone);
        }catch(IOException e){
            e.printStackTrace();
        }

        if(exist) return true;
        else return false;
    }

    public boolean checkCustomerPassword(String phone, String password){
        String[] result = null;
        try{
           result = FileCsv.searchCsvResult(filenameCustomer, 1, phone);
        }catch(IOException e){
            e.printStackTrace();
        }
        if(password.equals(result[4]))
            return true;
        else
            return false;

    }

    public Customer loginCustomer(String phone, String password){
        String[] result = null;
        try{
            result = FileCsv.searchCsvResult(filenameCustomer, 1, phone);
        }catch(IOException e){
            e.printStackTrace();
        }

        customer = Customer.loadDataFromId(Integer.parseInt(result[0]));

        return customer;
    }

    public boolean checkShopExist(String phone){
        boolean exist = false;

        try{
            exist = FileCsv.searchCsvDataExist(filenameShop, 1, phone);
        }catch(IOException e){
            e.printStackTrace();
        }

        if(exist) return true;
        else return false;
    }

    public Shop loginShop(String phone){
        String[] result = null;
        try{
            result = FileCsv.searchCsvResult(filenameShop, 1, phone);
        }catch(IOException e){
            e.printStackTrace();
        }

        shop = Shop.loadDataFromId(Integer.parseInt(result[0]));

        return shop;
    }

}
