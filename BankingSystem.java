import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bank {
    private String name;
    private List<Customer> customers;

    Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void performComplianceChecks() {
        // Placeholder for compliance check implementation
        System.out.println("Performing compliance checks...");
    }

    public void interactWithUser() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to " + getName() + "! Select an action:");
            System.out.println("1: Add Customer");
            System.out.println("2: Deposit");
            System.out.println("3: Withdraw");
            System.out.println("4: Perform Compliance Checks");
            System.out.println("5: Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.next();
                    System.out.print("Enter initial balance: ");
                    double balance = scanner.nextDouble();
                    Account account = new Account(accountNumber, balance);
                    Customer customer = new Customer(name, account);
                    addCustomer(customer);
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    String accNumDeposit = scanner.next();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    performTransaction(accNumDeposit, depositAmount, true);
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    String accNumWithdraw = scanner.next();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    performTransaction(accNumWithdraw, withdrawAmount, false);
                    break;
                case 4:
                    performComplianceChecks();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        }
    }

    private void performTransaction(String accountNumber, double amount, boolean isDeposit) {
        for (Customer customer : customers) {
            if (customer.getAccount().getAccountNumber().equals(accountNumber)) {
                if (isDeposit) {
                    customer.getAccount().deposit(amount);
                    System.out.println("Deposited $" + amount + " to account " + accountNumber);
                } else {
                    customer.getAccount().withdraw(amount);
                    System.out.println("Withdrew $" + amount + " from account " + accountNumber);
                }
                return;
            }
        }
        System.out.println("Account not found.");
    }
}

class Account {
    private String accountNumber;
    private double balance;

    Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}

class Customer {
    private String name;
    private Account account;

    Customer(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Bank bank = new Bank("My Bank");
        bank.interactWithUser();
    }
}
