import java.io.Console;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {
    private Map<String, String> userCredentials;

    public LoginManager() {
        userCredentials = new HashMap<>();
        userCredentials.put("admin", "admin123");
        userCredentials.put("user", "user123");
    }

    public String login() {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            return null;
        }

        System.out.print("Username: ");
        String username = console.readLine();

        System.out.print("Password: ");
        char[] passwordArray = console.readPassword(); 
        String password = new String(passwordArray);

        String correctPassword = userCredentials.get(username);
        if (correctPassword != null && correctPassword.equals(password)) {
            return username; 
        } else {
            return null; 
        }
    }

    public static void main(String[] args) {
        LoginManager manager = new LoginManager();
        String user = manager.login();
        if (user != null) {
            System.out.println("\nLogin successful.");
        } else {
            System.out.println("\nLogin failed.");
        }
    }
}
