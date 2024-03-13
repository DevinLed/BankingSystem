import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class AdvancedBankingSystem {
    public static void main(String[] args) {
        printWelcomeScreen();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        } while (choice != 2);
    }

    private static void handleLogin() {
        LoginManager loginManager = new LoginManager();

        while (!loginManager.login()) {
            System.out.println("Login failed. Please try again.");
        }
        System.out.println("Login successful!");
        Bank bank = new Bank("My Bank");
        bank.mainMenu();
    }

    private static void printWelcomeScreen() {
        System.out.println(
            "__________                __     ________              .__        \n" +
            "\\______   \\_____    ____ |  | __ \\______ \\   _______  _|__| ____  \n" +
            " |    |  _/\\__  \\  /    \\|  |/ /  |    |  \\_/ __ \\  \\/ /  |/    \\ \n" +
            " |    |   \\ / __ \\|   |  \\    <   |    `   \\  ___/\\   /|  |   |  \\\n" +
            " |______  /(____  /___|  /__|_ \\ /_______  /\\___  >\\_/ |__|___|  /\n" +
            "        \\/      \\/     \\/     \\/         \\/     \\/             \\/ \n" +
            "                                                                 \n" +
            ".____                 .__                                          \n" +
            "|    |    ____   ____ |__| ____                                    \n" +
            "|    |   /  _ \\ / ___\\|  |/    \\                                  \n" +
            "|    |__(  <_> ) /_/  >  |   |  \\                                 \n" +
            "|_______ \\____/\\___  /|__|___|  /                                 \n" +
            "        \\/    /_____/         \\/                                   "
        );
    }
}