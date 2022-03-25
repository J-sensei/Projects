// Adam Koh Jia Le (1191101858)

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Owner extends User{

    private static LinkedList<Owner> ownerList; // list contains all owner data in the system
    private String fullName;
    private String phone;

    // load owner data into the ownerList
    public static void loadOwner() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("csv/Owner.csv"));
        ownerList = new LinkedList<>();
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            ownerList.add(new Owner(values[0], values[1], values[2], values[3]));
        }
        reader.close();
    }

    // Contructor for login
    public Owner(String username, String password) {
        super(username, password);
    }
    
    public Owner(String username, String password, String fullName, String phone) {
        super(username, password);
        this.fullName = fullName;
        this.phone = phone;
    }

    // Update the owner data to the array and save to the csv file
    public void update() throws IOException {
        // Find the matching entry and reflect changes.
        for (int i = 0; i < ownerList.size(); i++) {
            String loopingUsername = ownerList.get(i).getUsername();
            if (loopingUsername.equals(username)) {
                ownerList.get(i).setPassword(password);
                ownerList.get(i).setFullName(fullName);
                ownerList.get(i).setPhone(phone);
            }
        }

        // Writing the updated entry, as well as entire List into .csv file.
        try {
            FileWriter updateInfo = new FileWriter("csv/Owner.csv");
            for (int i = 0; i < ownerList.size(); i++) {
                String usr = ownerList.get(i).getUsername();
                String pw = ownerList.get(i).getPassword();
                String fn = ownerList.get(i).getFullName();
                String ph = ownerList.get(i).getPhone();
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
            Utils.writeCsvFile("csv/PendingUser.csv", this, '2');
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    // Login, return the match user data with the same username and password
    @Override
    public User login() {
        
        for(int i = 0; i < ownerList.size(); i++) { 
            if(ownerList.get(i).getUsername().equals(username) && ownerList.get(i).getPassword().equals(password)) {
                return ownerList.get(i);
            }
        }

        return null;
    }

    //setter/getter
    public static LinkedList<Owner> getOwnerList() { return ownerList; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }

    @Override
    public String getRole() { return "Property Owner"; }

    @Override
    public String toString() {
        return username + ',' + password + ',' + fullName + ',' + phone;
    }
    
}
