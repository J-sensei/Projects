// Liew Jiann Shen (1191100556)

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Admin extends User{

    private static LinkedList<Admin> adminList; // list contains all admin data in the system

    // load admin data into the adminList
    public static void loadAdmin() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("csv/Admin.csv"));
        adminList = new LinkedList<>();
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            adminList.add(new Admin(values[0], values[1]));
        }
        reader.close();
    }

    // Constructor for login purposes
    public Admin(String username, String password) {
        super(username, password);
    }

    // Update the admin data to the array and save to the csv file
    public void update() throws IOException {
        // Find the matching entry and reflect changes.
        for (int i = 0; i < adminList.size(); i++) {
            String loopingUsername = adminList.get(i).getUsername();
            if (loopingUsername.equals(username)) {
                adminList.get(i).setPassword(password);
            }
        }

        // Writing the updated entry, as well as entire List into .csv file.
        try {
            FileWriter updateInfo = new FileWriter("csv/Admin.csv");
            for (int i = 0; i < adminList.size(); i++) {
                String usr = adminList.get(i).getUsername();
                String pw = adminList.get(i).getPassword();
                updateInfo.write(usr + "," + pw);
                updateInfo.write("\n");
            }
            updateInfo.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Register, add a new admin data to the csv file
    @Override
    protected void register() {
        
        try {
            Utils.writeCsvFile("csv/Admin.csv", this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Login, return the match user data with the same username and password
    @Override
    protected User login() {

        for(int i = 0; i < adminList.size(); i++) { 
            if(adminList.get(i).getUsername().equals(username) && adminList.get(i).getPassword().equals(password)) {
                return adminList.get(i);
            }
        }

        return null;
    }

    //setter / getter

    public static LinkedList<Admin> getAdminList() { return adminList; }

    @Override
    public String getRole() { return "Admin"; }

    @Override
    public String toString() {
        return username + ',' + password;
    }
    
}
