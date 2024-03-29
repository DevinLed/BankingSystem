import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    private String name;
    private List<Customer> customers = new ArrayList<>();
    private TransactionManager transactionManager = new TransactionManager();
    private Scanner scanner = new Scanner(System.in);

    Bank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void createAccount() {
        scanner.nextLine(); // Flush the buffer
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        Account account = new Account(accountNumber, balance);
        Customer customer = new Customer(name, account);
        customers.add(customer);
        System.out.println("Account created successfully.");
    }

    private void closeAccount(Customer customer) {
        System.out.println("Are you sure you want to close the account? (yes/no)");
        String confirmation = scanner.next().toLowerCase();

        if (confirmation.equals("yes")) {
            if (customers.remove(customer)) {
                System.out.println("Account closed successfully.");
            } else {
                System.out.println("Account not found or could not be removed.");
            }
        } else {
            System.out.println("Account closure canceled.");
        }
    }

    private void closeSetAccount() {
        System.out.print("Enter account number to close: ");
        String accountNumber = scanner.next();
        Optional<Customer> customerOptional = customers.stream()
                .filter(c -> c.getAccount().getAccountNumber().equals(accountNumber))
                .findFirst();

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            System.out.println("Are you sure you want to close the account? (yes/no)");
            String confirmation = scanner.next().toLowerCase();

            if ("yes".equals(confirmation)) {
                if (customers.remove(customer)) {
                    System.out.println("Account closed successfully.");
                } else {
                    System.out.println("Account not found or could not be removed.");
                }
            } else {
                System.out.println("Account closure canceled.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private void checkAccountBalance(Customer customer) {
        System.out.println("Account balance: $" + customer.getAccount().getBalance());
    }

    public void addCustomer() {
        System.out.print("Enter customer name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        Account account = new Account(accountNumber, balance);
        Customer customer = new Customer(name, account);
        customers.add(customer);
        System.out.println("Customer added successfully.");
    }

    public void performComplianceChecks() {
        System.out.println("Performing compliance checks...");
    }

    private Optional<Customer> findCustomerByAccountNumber(String accountNumber) {
        return customers.stream()
                .filter(c -> c.getAccount().getAccountNumber().equals(accountNumber))
                .findFirst();
    }

    public void transactionsMenu() {
        if (customers.isEmpty()) {
            System.out.println("No accounts found. Please create an account first.");
            return;
        }

        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        Optional<Customer> customerOptional = findCustomerByAccountNumber(accountNumber);
        if (!customerOptional.isPresent()) {
            System.out.println("Account not found.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\nTransactions Menu:");
            System.out.println("1: Deposit");
            System.out.println("2: Withdraw");
            System.out.println("3: Transfer");
            System.out.println("4: Bank Draft");
            System.out.println("5: Balance Inquiry");
            System.out.println("6: Exit to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    transactionManager.deposit(customers, accountNumber);
                    break;
                case 2:
                    transactionManager.withdraw(customers, accountNumber);
                    break;
                case 3:
                    transactionManager.transfer(customers, accountNumber);
                    break;
                case 4:
                    transactionManager.bankDraft(customers, accountNumber);
                    break;
                case 5:
                    transactionManager.balanceInquiry(customers, accountNumber);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public void accountMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nAccount Menu:");
            System.out.println("1: Open Account");
            System.out.println("2: Close Account");
            System.out.println("3: Return");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    closeSetAccount();
                    break;
                case 3:
                    running = false;
                    System.out.println("Returning to Main Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    break;
            }
        }
    }

    public void printAllCustomers() {
        System.out.println("\nAll Current Clients:");
        if (customers.isEmpty()) {
            System.out.println("No clients found.");
            return;
        }
        for (Customer customer : customers) {
            Account account = customer.getAccount();
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("Balance: $" + account.getBalance());
            System.out.println("---------------");
        }
    }

    public void mainMenu(String loggedInUser) {
        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1: Customer");
            System.out.println("2: Transactions");
            System.out.println("3: Account");
            System.out.println("4: Settings");
            if ("admin".equals(loggedInUser)) {
                System.out.println("5: Print all current clients");
                System.out.println("6: Exit");
            } else {
                System.out.println("5: Exit");
            }
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    customerMenu();
                    break;
                case 2:
                    transactionsMenu();
                    break;
                case 3:
                    accountMenu();
                    break;
                case 4:
                    System.out.println("Settings are not implemented yet.");
                    break;
                case 5:
                    if ("admin".equals(loggedInUser)) {
                        printAllCustomers();
                    } else {
                        running = false;
                        System.out.println("Exiting...");
                    }
                    break;
                case 6:
                    if ("admin".equals(loggedInUser)) {
                        running = false;
                        System.out.println("Exiting...");
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        }
    }

    public void manageIdentification(Customer customer) {
        boolean running = true;
        while (running) {
            System.out.println("\nIdentification Management:");
            System.out.println("1. Enter new identification");
            System.out.println("2. Update identification");
            System.out.println("3. Check identification");
            System.out.println("4. Return to Customer Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    enterNewIdentification(customer);
                    break;
                case 2:
                    updateIdentification(customer);
                    break;
                case 3:
                    checkIdentification(customer);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void enterNewIdentification(Customer customer) {
        System.out.print("Enter new identification (max 10 digits): ");
        String newId = scanner.next();
        if (newId.length() <= 10) {
            customer.setIdentificationNumber(newId);
            System.out.println("Identification updated successfully.");
        } else {
            System.out.println("Invalid identification. Please ensure it is max 10 digits.");
        }
    }

    private void updateIdentification(Customer customer) {
        System.out.print("Enter existing identification: ");
        String existingId = scanner.next();
        if (existingId.equals(customer.getIdentificationNumber())) {
            System.out.print("Enter new identification (max 10 digits): ");
            String newId = scanner.next();
            if (newId.length() <= 10) {
                customer.setIdentificationNumber(newId);
                System.out.println("Identification updated successfully.");
            } else {
                System.out.println("Invalid identification. Please ensure it is max 10 digits.");
            }
        } else {
            System.out.println("Identification mismatch.");
        }
    }

    private void checkIdentification(Customer customer) {
        String id = customer.getIdentificationNumber();
        if (id != null && !id.isEmpty()) {
            System.out.println("Identification for account " + customer.getAccount().getAccountNumber() + ": " + id);
        } else {
            System.out.println("No identification set for this account.");
        }
    }

    public void manageContactDetails(Customer customer) {
        boolean running = true;
        while (running) {
            System.out.println("\nContact Details Management:");
            System.out.println("1. Phone");
            System.out.println("2. Email");
            System.out.println("3. Return to Customer Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    managePhone(customer);
                    break;
                case 2:
                    manageEmail(customer);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void managePhone(Customer customer) {
        System.out.println("Phone Management:");
        System.out.println("1. Enter new phone number");
        System.out.println("2. Update phone number");
        System.out.println("3. Remove phone number");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                enterNewPhoneNumber(customer);
                break;
            case 2:
                updatePhoneNumber(customer);
                break;
            case 3:
                removePhoneNumber(customer);
                break;
            case 4:
                // Back to Contact Details Management
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void manageEmail(Customer customer) {
        System.out.println("Email Management:");
        System.out.println("1. Enter new email");
        System.out.println("2. Update email");
        System.out.println("3. Remove email");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                enterNewEmail(customer);
                break;
            case 2:
                updateEmail(customer);
                break;
            case 3:
                removeEmail(customer);
                break;
            case 4:
                // Back to Contact Details Management
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void enterNewPhoneNumber(Customer customer) {
        System.out.print("Enter new phone number (max 10 digits): ");
        String newPhone = scanner.next();
        if (newPhone.length() <= 10) {
            customer.setPhone(newPhone);
            System.out.println("Phone number updated successfully.");
        } else {
            System.out.println("Invalid phone number. Please ensure it is max 10 digits.");
        }
    }

    private void updatePhoneNumber(Customer customer) {
        System.out.print("Enter existing phone number: ");
        String existingPhone = scanner.next();
        if (existingPhone.equals(customer.getPhone())) {
            System.out.print("Enter new phone number (max 10 digits): ");
            String newPhone = scanner.next();
            if (newPhone.length() <= 10) {
                customer.setPhone(newPhone);
                System.out.println("Phone number updated successfully.");
            } else {
                System.out.println("Invalid phone number. Please ensure it is max 10 digits.");
            }
        } else {
            System.out.println("Phone number mismatch.");
        }
    }

    private void removePhoneNumber(Customer customer) {
        customer.setPhone(null);
        System.out.println("Phone number removed successfully.");
    }

    private void enterNewEmail(Customer customer) {
        System.out.print("Enter new email address: ");
        String newEmail = scanner.next();
        if (isValidEmail(newEmail)) {
            customer.setEmail(newEmail);
            System.out.println("Email address updated successfully.");
        } else {
            System.out.println("Invalid email address format.");
        }
    }

    private void updateEmail(Customer customer) {
        System.out.print("Enter existing email address: ");
        String existingEmail = scanner.next();
        if (existingEmail.equals(customer.getEmail())) {
            System.out.print("Enter new email address: ");
            String newEmail = scanner.next();
            if (isValidEmail(newEmail)) {
                customer.setEmail(newEmail);
                System.out.println("Email address updated successfully.");
            } else {
                System.out.println("Invalid email address format.");
            }
        } else {
            System.out.println("Email address mismatch.");
        }
    }

    private void removeEmail(Customer customer) {
        customer.setEmail(null);
        System.out.println("Email address removed successfully.");
    }

    private boolean isValidEmail(String email) {
        // Simple email format validation (requires '@')
        return email.contains("@");
    }

    public void manageAddress(Customer customer) {
        boolean running = true;
        while (running) {
            System.out.println("\nAddress Management:");
            System.out.println("1. Enter new address");
            System.out.println("2. Update address");
            System.out.println("3. Address Inquiry");
            System.out.println("4. Remove address");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over
            switch (choice) {
                case 1:
                    enterNewAddress(customer);
                    break;
                case 2:
                    updateAddress(customer);
                    break;
                case 3:
                    addressInquiry(customer);
                    break;
                case 4:
                    removeAddress(customer);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void enterNewAddress(Customer customer) {
        System.out.print("Enter new address:\n1. # & Street: ");
        String street = scanner.nextLine();
        System.out.print("2. Town/City: ");
        String city = scanner.nextLine();
        System.out.print("3. Province: ");
        String province = scanner.nextLine();
        System.out.print("4. Postal Code: ");
        String postalCode = scanner.nextLine();

        Address address = new Address(street, city, province, postalCode);
        customer.setAddress(address);
        System.out.println("Address updated successfully.");
    }

    private void updateAddress(Customer customer) {
        System.out.print("Do you want to update the existing address? (y/n): ");
        String updateChoice = scanner.next().toLowerCase();
        scanner.nextLine(); // Consume the newline left-over
        if (updateChoice.equals("y")) {
            enterNewAddress(customer);
        }
    }

    private void addressInquiry(Customer customer) {
        Address address = customer.getAddress();
        if (address != null) {
            System.out.println("\nAddress Inquiry:");
            System.out.println("Street: " + address.getStreet());
            System.out.println("City: " + address.getCity());
            System.out.println("Province: " + address.getProvince());
            System.out.println("Postal Code: " + address.getPostalCode());
        } else {
            System.out.println("No address found for the account.");
        }
    }

    private void removeAddress(Customer customer) {
        customer.setAddress(null);
        System.out.println("Address removed successfully.");
    }

    public void customerMenu() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        Optional<Customer> customerOptional = customers.stream()
                .filter(c -> c.getAccount().getAccountNumber().equals(accountNumber))
                .findFirst();

        if (!customerOptional.isPresent()) {
            System.out.println("Customer not found. Do you want to create a new account? (y/n): ");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("y")) {
                createAccount();
            }
            return;
        }

        Customer customer = customerOptional.get();
        boolean running = true;
        while (running) {
            System.out.println("\nCustomer and Account Management:");
            System.out.println("1. Close Account");
            System.out.println("2. Check Account Balance");
            System.out.println("3. Identification");
            System.out.println("4. Contact Details");
            System.out.println("5. Address");
            System.out.println("6. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    closeAccount(customer);
                    break;
                case 2:
                    checkAccountBalance(customer);
                    break;
                case 3:
                    manageIdentification(customer);
                    break;
                case 4:
                    manageContactDetails(customer);
                    break;
                case 5:
                    manageAddress(customer);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

}