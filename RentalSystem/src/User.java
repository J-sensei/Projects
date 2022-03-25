// Liew Jiann Shen (1191100556)

import java.io.IOException;

public abstract class User {

    // Basic user details
    protected String username;
    protected String password;

    private static User currentUser = null; // current User login into this system

    protected User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected abstract void register(); // register, inserted a new data to the system
    protected abstract User login(); // login, match the user details

    // logout the system
    public static void logout() throws IOException {
        Main main = new Main();
        currentUser = null;
        main.changeScene("Login.fxml");
    }

    //setter
    public static void setCurrentUser(User user){ currentUser = user; }

    //getter

    public abstract String getRole();

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public String getUsername(){ return username; }
    public String getPassword(){ return password; }

    public static User getCurrentUser(){ return currentUser; }
}
