// Soon Jie Kang (1201301760)

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Property {

    //Property Details
    private int id;
    private String username;
	private int size;
    private float price;
	private String address;
    private int postcode;
    private String type;
	private int noRoom;
    private int noBathRoom;
    private int noPendingTenant;

    //Facilities
    private String swimmingPool;
    private String airCon;
    private String wifi;
    private String waterHeater;

    private String status; // active, inactive, pending
    private String tenant; // if inactive whos the tenant renting it
    private String comment; // comment of this property

    //Buttons
    private Button detail; // button to display full details of this property
    private Button approve, delete; // approve pending property to the property and delete the pending property
    private Button commentBtn; // go to a page for the user to write the comment
    private Button edit; // button to edit this property
    private Button drop; // button to drop or delete this property class
    private Button openPendingTenant;
    private Button apply; // Tenant apply property

    // Check Box 
    private CheckBox hidden;

    // Image
    private String imagePath;
    private Image image;

    // List
    private LinkedList<PendingTenant> pendingTenant; // Pending tenant list of this property

    private static LinkedList<Property> property; // All approved property list
    private static LinkedList<Property> pendingProperty; // All pending property list

    // Constucture for property
    public Property(int id, String username, String address, int postcode, String type, int size, float price, int noRoom, int noBathRoom,
    String swimmingPool, String airCon, String wifi, String waterHeater, String status, String tenant, String comment, String img) {
        setId(id);
        setUsername(username);
        setSize(size);
        setPrice(price);
        setAddress(address);
        setPostcode(postcode);
        setType(type);
        setNoRoom(noRoom);
        setNoBathRoom(noBathRoom);
        setSwimmingPool(swimmingPool);
        setAirCon(airCon);
        setWifi(wifi);
        setWaterheater(waterHeater);
        setStatus(status);
        setTenant(tenant);
        setComment(comment);
        initPendingTenant();
        initButton();
        initPropertyButton();
        noPendingTenant = pendingTenant.size();

        imagePath = img;
        image = new Image(new File(imagePath).toURI().toString());
    }

    // Contructure for pending property
    public Property(String username, String address, int postcode, String type, int size, float price, int noRoom, int noBathRoom,
                    String swimmingPool, String airCon, String wifi, String waterHeater, String img) {
        id = -1; // assign an invalid id
        setUsername(username);
        setAddress(address);
        setPostcode(postcode);
        setType(type);
        setSize(size);
        setPrice(price);
        setNoRoom(noRoom);
        setNoBathRoom(noBathRoom);
        setSwimmingPool(swimmingPool);
        setAirCon(airCon);
        setWifi(wifi);
        setWaterheater(waterHeater);
        status = "pending";
        initButton();
        initPendingButton();

        imagePath = img;
        image = new Image(new File(imagePath).toURI().toString());
    }

    // Buttons Initialization for both approved and pending property
    private void initButton() {

        detail = new Button("Details");
        edit = new Button("Edit");

        detail.setOnAction(e -> {
            Parent root = null;
            PropertyDetailController controller = new PropertyDetailController(this);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PropertyDetail.fxml"));
                loader.setController(controller);
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Property Details");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image("file:assets/icon2.png"));
                stage.show();
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        edit.setOnAction(e -> {
            Parent root = null;
            EditPropertyController controller = new EditPropertyController(this);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProperty.fxml"));
                loader.setController(controller);
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Edit Property");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image("file:assets/icon4.png"));
                stage.show();
            }catch (Exception ex) {
                ex.printStackTrace();
            }           
        });

    }

    // Initialize only for approved propertys
    private void initPropertyButton() {

        commentBtn = new Button("Comment");
        drop = new Button("Delete");
        openPendingTenant = new Button("View");
        hidden = new CheckBox();
        apply = new Button("Apply");

        if(status.equals("hidden")) hidden.setSelected(true); //check the box if property is hidden initially

        commentBtn.setOnAction(e -> {
            Parent root = null;
            CommentController controller2 = new CommentController(this);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Comment.fxml"));
                loader.setController(controller2);
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Comment Property: " + id);
                stage.setScene(new Scene(root));
                stage.show();
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        drop.setOnAction(e -> {
            Alert message = new Alert(AlertType.CONFIRMATION);
            message.setTitle("Delete Property ID: " + id);
            message.setHeaderText("Are you sure u want to delete this property?");
            message.setContentText("All data in this system included pending rental\nin this property will be delete.");
            
            Optional<ButtonType> result = message.showAndWait();

            if(result.get() == ButtonType.OK) {
                // Delete image file
                File file = new File(imagePath);
                if(!imagePath.equals("assets/default.jpg"))
                    file.delete();

                // Delete data in csv
                property.remove(this);
                try {
                    Utils.writeCSvFile("csv/Property.csv", property);
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Refresh page
                if(User.getCurrentUser() instanceof Admin) {
                    ManagePropertyController controller = (ManagePropertyController)Main.getCurrentController();
                    controller.getObervableList().remove(this);
                }else {
                    MyPropertyController controller = (MyPropertyController)Main.getCurrentController();
                    controller.getObervableList().remove(this);
                }
            }
        });

        openPendingTenant.setOnAction(e -> {
            Parent root = null;
            PendingTenantController controller = new PendingTenantController(pendingTenant);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PendingTenant.fxml"));
                loader.setController(controller);
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Pending Tenant Details");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        hidden.setOnAction(e -> {
            if(hidden.isSelected()) {
                status = "hidden";
            }else {
                if(tenant != null) {
                    status = "inactive";
                }else {
                    status = "active";
                }
            }

            try {
                Utils.writeCSvFile("csv/Property.csv", property);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Refresh table
            MyPropertyController controller = (MyPropertyController)Main.getCurrentController();
            controller.getTable().refresh();
        });

        apply.setOnAction(e -> {
            try {
                boolean matching = false;
                if (pendingTenant.size() > 0) {
                    for (int i = 0; i < pendingTenant.size(); i++) {
                        System.out.println("Current USer:" + ((Tenant)User.getCurrentUser()).getUsername() + ", Pending TEnant: "  + pendingTenant.get(i).getUsername() );
                        if (id == pendingTenant.get(i).getPid()
                            && ((Tenant)User.getCurrentUser()).getUsername().equals(pendingTenant.get(i).getUsername())) {
                            matching = true;
                        } else {
                            matching = false;
                        }
                    }
                } else {
                    matching = false;
                }

                if (matching == true) {
                    RentPropertyController.rentalApplicationExisted();
                } else {
                    PendingTenant pt = new PendingTenant(id, ((Tenant)User.getCurrentUser()).getUsername(), "pending");
                    PendingTenant.getPendingTenantList().add(pt);
                    Utils.writeCSvFile("csv/TenantPending.csv", PendingTenant.getPendingTenantList());
                    pendingTenant.add(pt);
                    noPendingTenant = pendingTenant.size();
                    RentPropertyController.confirmRentalApplication();

                    RentPropertyController controller = (RentPropertyController)Main.getCurrentController();
                    controller.getTable().getItems().remove(this);
                }       

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    // Button initialization only for pending property
    private void initPendingButton() {

        approve = new Button("Approve");
        delete = new Button("Delete");

        approve.setOnAction(e -> {
            if(property.size() <= 0)
                id = 1; // give the first id if property list is empty
            else
                id = property.get(property.size() - 1).getId() + 1; // new id for new property

            status = "active";
            tenant = null;
            comment = null;

            try {
                Utils.writeCsvFile("csv/Property.csv", this.toString());
                pendingProperty.remove(this); // remove the current pending property from the list
                Utils.writeCsvPendingPropertyFile("csv/PropertyPending.csv", pendingProperty); // save to property peding file
                loadProperty(); // reload property list
                // refresh table
                ApprovePropertyController controller = (ApprovePropertyController)Main.getCurrentController();
                controller.getObervableList().remove(this);
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        delete.setOnAction(e -> {
            try {
                // Delete image file
                File file = new File(imagePath);
                if(!imagePath.equals("assets/default.jpg"))
                    file.delete();

                // Delete data in csv

                pendingProperty.remove(this); // just delete it from the pending property list
                Utils.writeCsvPendingPropertyFile("csv/PropertyPending.csv", pendingProperty); // save to property peding file
                //main.changeScene("ApproveProperty.fxml");
                // Refresh page
                if(User.getCurrentUser() instanceof Admin) {
                    ApprovePropertyController controller = (ApprovePropertyController)Main.getCurrentController();
                    controller.getObervableList().remove(this);
                }else {
                    MyPropertyController controller = (MyPropertyController)Main.getCurrentController();
                    controller.getObervableList().remove(this);
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    //Pending Tenant Initialization
    private void initPendingTenant() {

        pendingTenant = new LinkedList<PendingTenant>();

        for(int i = 0; i < PendingTenant.getPendingTenantList().size(); i++) {
            if(id == PendingTenant.getPendingTenantList().get(i).getPid() && PendingTenant.getPendingTenantList().get(i).getStatus().equals("pending")) {
                pendingTenant.add(PendingTenant.getPendingTenantList().get(i));
            }
        }

    }

    //to read information from csv into the linkedlist property
    public static void loadProperty() throws IOException {
        // read property.csv into a list of lines.
        property = new LinkedList<Property>();
        List<String> lines = Files.readAllLines(Paths.get("csv/Property.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by semicolon with total of 16 items
            String[] items = lines.get(i).split(";");
            property.add(new Property(Integer.parseInt(items[0]), items[1], items[2], Integer.parseInt(items[3]), items[4], 
                        Integer.parseInt(items[5]), Float.parseFloat(items[6]), Integer.parseInt(items[7]), Integer.parseInt(items[8]),
            items[9], items[10], items[11], items[12], items[13], items[14], items[15], items[16]));
		}
    }

    // Load pending property to the system
    public static void loadPendingProperty() throws IOException {
        pendingProperty = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader("csv/PropertyPending.csv"));
        String line;

        while((line = reader.readLine()) != null) {
            String[] values = line.split(";");
            pendingProperty.add(new Property(values[0], values[1], Integer.parseInt(values[2]), values[3], Integer.parseInt(values[4]), 
                                        Float.parseFloat(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7]), 
                                        values[8], values[9], values[10], values[11], values[12]));
        }
        reader.close();
    }

    // add or edit the comment specific property and save to the file
    public void editComment(String comment) {
        setComment(comment);
        try {
            Utils.writeCSvFile("csv/Property.csv", property);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // setter / getter
    public void setPendingTenant(LinkedList<PendingTenant> pendingTenant) {
        this.pendingTenant = pendingTenant;
    }

    public static LinkedList<Property> getPropertyList() { 
        return property; 
    }

    public static LinkedList<Property> getPendingPropertyList() {
        return pendingProperty;
    }

    public LinkedList<PendingTenant> getPendingTenant() {
        return pendingTenant;
    }

    //Getter / Setter
    public int getId() {
        return id;
    }

    public String getIdString(){
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    } 

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    } 

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    } 

    public float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    } 

    public float getRentalRate() {
        return (float)(Math.round( (price/size)*100.0 )/100.0);
    }

    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    } 

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    } 

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    } 

    public int getNoRoom() {
        return noRoom;
    }

    public void setNoRoom(int noRoom) { 
        this.noRoom = noRoom;
    } 

    public int getNoBathRoom() {
        return noBathRoom;
    }

    public void setNoBathRoom(int noBathRoom) {
        this.noBathRoom = noBathRoom;
    } 

    public String getSwimmingPool() {
        return swimmingPool;
    }

    public void setSwimmingPool(String swimmingPool) {
        this.swimmingPool = swimmingPool;
    } 

    public String getAirCon() {
        return airCon;
    }

    public void setAirCon(String airCon) {
        this.airCon = airCon;
    } 

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    } 

    public String getWaterHeater() {
        return waterHeater;
    }

    public void setWaterheater(String waterHeater) {
        this.waterHeater = waterHeater;
    } 

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTenant(String tenant) {
        if(tenant.equals("null")) 
            this.tenant = null;
        else
            this.tenant = tenant;
    }

    public String getTenant() {
        return tenant;
    }

    public void setComment(String comment) {
        if(comment.equals("null") || comment.isEmpty()) 
            this.comment = null;
        else
            this.comment = comment;     
    }

    public String getComment() {
        if(comment == null) return "-";
        else return comment;
    }

    public void setNoPendingTenant(int noPendingTenant) {
        this.noPendingTenant = noPendingTenant;
    }

    public int getNoPendingTenant() {
        return noPendingTenant;
    }

    // Return buttons

    public Button getDetail() {
        return detail;
    }

    public Button getApprove() {
        return approve;
    }

    public Button getDelete() {
        return delete;
    }

    public Button getCommentBtn() {
        return commentBtn;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getDrop() {
        return drop;
    }

    public Button getOpenPendingTenant() {
        return openPendingTenant;
    }

    public Button getApply() {
        return apply;
    }

    // Return hidden check box
    public CheckBox getHidden() {
        return hidden;
    }

    // Image
    public Image getImage() {
        return image;
    }


    // Return Format that match to Property.csv
    @Override
    public String toString() {
        return id + ";" + username + ";" + address + ";" + postcode + ";" + type + ";" + size + ";" + price + ";" 
                + noRoom + ";" + noBathRoom + ";" + swimmingPool + ";" + airCon + ";" + wifi + ";" + waterHeater 
                + ";" + status + ";" + tenant + ";" + comment + ";" + imagePath;
    }

    // Return Format that match to PropertyPending.csv
    public String getPendingProperty() {
        return username + ";" + address + ";" + postcode + ";" + type + ";" + size + ";"
                + price + ';' + noRoom + ";" + noBathRoom + ";" + swimmingPool + ";" + airCon + ";" + wifi + ";" 
                + waterHeater + ";" + imagePath;
    }
}