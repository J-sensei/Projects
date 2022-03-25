// Teoh Yee Chien (1201300833)

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PendingTenant {

    private int pid; //property id
    private Tenant tenant; //tenant username
    private String status; //approve, reject, pending

    private Button approve; // button to approve this tenant, allow them to rent the property

    private static LinkedList<PendingTenant> pendingTenantList; // List contains all pending tenant data in the system

    public PendingTenant(int pid, String tenant, String status) {
        this.pid = pid;
        this.status = status;
        
        for(int i = 0; i < Tenant.getTenantList().size(); i++) {
            if(Tenant.getTenantList().get(i).getUsername().equals(tenant)) {
                this.tenant = Tenant.getTenantList().get(i);
            }
        }

        approve = new Button("Approve");
        approve.setOnAction(e -> {

            Property property = null;

            //Set property status to inactive & tenant reting to current tenant
            for(int i = 0; i < Property.getPropertyList().size(); i++) {
                if(Property.getPropertyList().get(i).getId() == pid) {
                    property = Property.getPropertyList().get(i);
                    property.setStatus("inactive");
                    property.setTenant(this.tenant.getUsername());
                    property.setNoPendingTenant(0);
                    break;
                }
            }
            // Save to Property.csv
            try {
                Utils.writeCSvFile("csv/Property.csv", Property.getPropertyList());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            this.status = "approve"; // change this pending tenant to approve

            for(int i = 0; i < property.getPendingTenant().size(); i++) {
                if(!property.getPendingTenant().get(i).getStatus().equals("approve"))
                property.getPendingTenant().get(i).setStatus("reject");
            }

            try {
                Utils.writeCSvFile("csv/TenantPending.csv", pendingTenantList);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            property.setPendingTenant(new LinkedList<PendingTenant>());
            MyPropertyController controller = (MyPropertyController)Main.getCurrentController();
            controller.getObervableList().remove(property);

            //Close pending tenant window
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        });
    }

    // Load all the pending tenant data to the pendingTenantList
    public static void loadPendingTenantList() throws IOException {

        pendingTenantList = new LinkedList<PendingTenant>();
        BufferedReader reader = new BufferedReader(new FileReader("csv/TenantPending.csv"));
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            pendingTenantList.add(new PendingTenant(Integer.parseInt(values[0]), values[1], values[2]));
        }
        reader.close();

    }

    //Setter
    public void setStatus(String status) { this.status = status; }

    //Getters
    public int getPid() { return pid; }
    public Tenant getTenant() { return tenant; }

    public String getUsername() { return tenant.getUsername(); }
    public String getFullName() { return tenant.getFullName(); }
    public String getPhone() { return tenant.getPhone(); }
    public String getStatus() { return status; }

    public Button getApprove() { return approve; }

    public static LinkedList<PendingTenant> getPendingTenantList() { return pendingTenantList; }

    @Override
    public String toString() {
        return (String)(pid + "," + tenant.getUsername() + "," + status);
    }
    
}
