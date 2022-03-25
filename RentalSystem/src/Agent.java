// Adam Koh Jia Le (1191101858)

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Agent extends User{

    private static LinkedList<Agent> agentList; // list contains all agent data in the system
    private String fullName; // Full name of the user
    private String phone; // phone number

    // load agent data into the agentList
    public static void loadAgent() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("csv/Agent.csv"));
        agentList = new LinkedList<>();
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            agentList.add(new Agent(values[0], values[1], values[2], values[3]));
        }
        reader.close();
    }
    
    // Constructor for login
    public Agent(String username, String password) {
        super(username, password);
    }

    public Agent(String username, String password, String fullName, String phone) {
        super(username, password);
        this.fullName = fullName;
        this.phone = phone;
    }

    // Update the agent data to the array and save to the csv file
    public void update() throws IOException {
        // Find the matching entry and reflect changes.
        for (int i = 0; i < agentList.size(); i++) {
            String loopingUsername = agentList.get(i).getUsername();
            if (loopingUsername.equals(username)) {
                agentList.get(i).setPassword(password);
                agentList.get(i).setFullName(fullName);
                agentList.get(i).setPhone(phone);
            }
        }

        // Writing the updated entry, as well as entire List into .csv file.
        try {
            FileWriter updateInfo = new FileWriter("csv/Agent.csv");
            for (int i = 0; i < agentList.size(); i++) {
                String usr = agentList.get(i).getUsername();
                String pw = agentList.get(i).getPassword();
                String fn = agentList.get(i).getFullName();
                String ph = agentList.get(i).getPhone();
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
            Utils.writeCsvFile("csv/PendingUser.csv", this, '1');
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    // Login, return the match user data with the same username and password
    @Override
    public User login() {
        
        for(int i = 0; i < agentList.size(); i++) { 
            if(agentList.get(i).getUsername().equals(username) && agentList.get(i).getPassword().equals(password)) {
                return agentList.get(i);
            }
        }

        return null;
    }

    //setter/getter
    public static LinkedList<Agent> getAgentList() { return agentList; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }

    @Override
    public String getRole() { return "Property Agent"; }

    @Override
    public String toString() {
        return username + ',' + password + ',' + fullName + ',' + phone;
    }
    
}
