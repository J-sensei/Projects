// Soon Jie Kang (1201301760)

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javafx.scene.control.Button;

public class PendingUser {

    // User details
    private String username;
    private String password;
    private String name;
    private String phone;
    private int role;
    private Button approve;
    private Button delete;

    private static LinkedList<PendingUser> pendingList; // List of all pending user list in the system

    public PendingUser(String username, String password, String name, String phone, int role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;

        initButton();
    }

    //Initialize approve and delete button
    private void initButton() {
        approve = new Button("Approve");
        delete = new Button("Delete");
        // Add to user csv depends on the role, delete current object and save to PendingUser.csv
        approve.setOnAction(e -> { 
            String path = null;
            User user = null;
            switch(role) {
                case 1: 
                    path = "Agent.csv";
                    user = new Agent(username, password, name, phone);
                    break;
                case 2:
                    path = "Owner.csv"; 
                    user = new Owner(username, password, name, phone);
                    break;
                case 3: 
                    path = "Tenant.csv";
                    user = new Tenant(username, password, name, phone);
                    break;
            }
            try {
                Utils.writeCsvFile("csv/" + path, user); //Insert a new row
                pendingList.remove(this); //Remove current index
                Utils.writeCSvFile("csv/PendingUser.csv", pendingList); //rewrite to PendingUser.csv
                //loadPendingList(); //reload the list
                reloadUser();
                // refresh table
                PendingUserController controller = (PendingUserController)Main.getCurrentController();
                controller.getTable().getItems().remove(this);
            }catch(IOException ex) {
                ex.printStackTrace();
            }
         });
        // Delete current object and save to PendingUser.csv
        delete.setOnAction(e -> { 
            try {
                pendingList.remove(this); //Remove current index
                Utils.writeCSvFile("csv/PendingUser.csv", pendingList); //rewrite to PendingUser.csv
                // refresh table
                PendingUserController controller = (PendingUserController)Main.getCurrentController();
                controller.getTable().getItems().remove(this);
            }catch(IOException ex) {
                ex.printStackTrace();
            }
         });
    }

    //1 -> Agent, 2 -> Owner, 3 -> Tenant
    private String setRole(int role) { 
        switch(role) {
            case 1: return "Agent";
            case 2: return "Owner";
            case 3: return "Tenant";
        }
        return null;
    }

    // Reload or refresh the user list base on the role as new data is inserted
    private void reloadUser() throws IOException{
        switch(role) {
            case 1: Agent.loadAgent(); break;
            case 2: Owner.loadOwner(); break;
            case 3: Tenant.loadTenant(); break;
        }
    }

    //load pendingList
    public static void loadPendingList() throws IOException { 
        pendingList = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader("csv/PendingUser.csv"));
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            pendingList.add(new PendingUser(values[0], values[1], values[2], values[3], Integer.parseInt(values[4])));
        }
        reader.close();
    }

    // getter
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getRole() { return setRole(role); }
    public Button getApprove() { return approve; }
    public Button getDelete() { return delete; } 
    public static LinkedList<PendingUser> getPendingList() { return pendingList; }
    
    @Override
    public String toString() {
        return username + ',' + password + ',' + name + ',' + phone + ',' + role;
    }
}
