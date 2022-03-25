// Teoh Yee Chien (1201300833)

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Tenant extends User {

    private static LinkedList<Tenant> tenantList; // list contains all tenant data in the system
    private String fullName;
    private String phone;

    // load tenant data into the tenantList
    public static void loadTenant() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("csv/Tenant.csv"));
        tenantList = new LinkedList<>();
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            tenantList.add(new Tenant(values[0], values[1], values[2], values[3]));
        }
        reader.close();
    }

    // Contructor for login
    public Tenant(String username, String password) {
        super(username, password);
    }

    public Tenant(String username, String password, String fullName, String phone) { 

        super(username, password);
        this.fullName = fullName;
        this.phone = phone;

    }

    // Update the tenant data to the array and save to the csv file
    public void update() throws IOException {
        // Find the matching entry and reflect changes.
        for (int i = 0; i < tenantList.size(); i++) {
            String loopingUsername = tenantList.get(i).getUsername();
            if (loopingUsername.equals(username)) {
                tenantList.get(i).setPassword(password);
                tenantList.get(i).setFullName(fullName);
                tenantList.get(i).setPhone(phone);
            }
        }

        // Writing the updated entry, as well as entire List into .csv file.
        try {
            FileWriter updateInfo = new FileWriter("csv/Tenant.csv");
            for (int i = 0; i < tenantList.size(); i++) {
                String usr = tenantList.get(i).getUsername();
                String pw = tenantList.get(i).getPassword();
                String fn = tenantList.get(i).getFullName();
                String ph = tenantList.get(i).getPhone();
                updateInfo.write(usr + "," + pw + "," + fn + "," + ph);
                updateInfo.write("\n");
            }
            updateInfo.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Register, add a new admin data to the csv file
    @Override
    public void register() {

        try {
            Utils.writeCsvFile("csv/PendingUser.csv", this, '3');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Login, return the match user data with the same username and password
    @Override
    public User login() {
        
        for(int i = 0; i < tenantList.size(); i++) { 
            if(tenantList.get(i).getUsername().equals(username) && tenantList.get(i).getPassword().equals(password)) {
                return tenantList.get(i);
            }
        }

        return null;
    }

    //setter and getter
    public static LinkedList<Tenant> getTenantList() { return tenantList; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }

    @Override
    public String getRole(){ return "Property Tenant"; }

    @Override
    public String toString(){
        return username + ',' + password + ',' + fullName + ',' + phone;
    }
    
}
