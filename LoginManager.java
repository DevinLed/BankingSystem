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

    public boolean login() {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            return false;
        }

        System.out.print("Username: ");
        String username = console.readLine();

        System.out.print("Password: ");
        StringBuilder passwordBuilder = new StringBuilder();
        try {
            while (true) {
                char passChar = (char) System.in.read();
                if (passChar == '\n' || passChar == '\r') break; // Enter key pressed
                passwordBuilder.append(passChar);
                System.out.print("*");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String password = passwordBuilder.toString();

        String correctPassword = userCredentials.get(username);
        return correctPassword != null && correctPassword.equals(password);
    }

    public static void main(String[] args) {
        LoginManager manager = new LoginManager();
        if (manager.login()) {
            System.out.println("\nLogin successful.");
        } else {
            System.out.println("\nLogin failed.");
        }
    }
}
