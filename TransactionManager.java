import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TransactionManager {
    private Scanner scanner = new Scanner(System.in);

    public TransactionManager() {
    }

    public void deposit(List<Customer> customers, String accountNumber) {
        Optional<Customer> customerOptional = findCustomerByAccountNumber(customers, accountNumber);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            System.out.print("Enter deposit amount: ");
            double amount = scanner.nextDouble();
            customer.getAccount().deposit(amount);
            System.out.println("Deposited $" + amount + " to account " + accountNumber);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(List<Customer> customers, String accountNumber) {
        Optional<Customer> customerOptional = findCustomerByAccountNumber(customers, accountNumber);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            System.out.print("Enter withdrawal amount: ");
            double amount = scanner.nextDouble();
            boolean success = customer.getAccount().withdraw(amount);
            if (success) {
                System.out.println("Withdrew $" + amount + " from account " + accountNumber);
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transfer(List<Customer> customers, String fromAccountNumber) {
        System.out.print("Enter account number to transfer to: ");
        String toAccountNumber = scanner.next();

        Optional<Customer> toCustomerOptional = findCustomerByAccountNumber(customers, toAccountNumber);
        if (!toCustomerOptional.isPresent()) {
            System.out.println("Account not found for transfer.");
            return;
        }

        System.out.print("Account located. Please enter amount for transfer: ");
        double amount = scanner.nextDouble();

        Optional<Customer> fromCustomerOptional = findCustomerByAccountNumber(customers, fromAccountNumber);
        if (!fromCustomerOptional.isPresent()) {
            System.out.println("From Account not found.");
            return;
        }

        Customer fromCustomer = fromCustomerOptional.get();
        Account fromAccount = fromCustomer.getAccount();
        if (fromAccount.getBalance() >= amount) {
            boolean withdrawSuccess = fromAccount.withdraw(amount);
            if (withdrawSuccess) {
                Customer toCustomer = toCustomerOptional.get();
                Account toAccount = toCustomer.getAccount();
                toAccount.deposit(amount);
                System.out.println("Transferred $" + amount + " to account " + toAccountNumber);
            } else {
                System.out.println("Failed to withdraw from the from account.");
            }
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }

    public void bankDraft(List<Customer> customers, String accountNumber) {
        Optional<Customer> customerOptional = findCustomerByAccountNumber(customers, accountNumber);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            System.out.print("Payee Name: ");
            String payeeName = scanner.next();
            System.out.print("Amount for draft: ");
            double amount = scanner.nextDouble();
            if (customer.getAccount().getBalance() >= amount) {
                customer.getAccount().withdraw(amount);
                System.out.println("Sending job to printer, please prepare bank draft for " + payeeName + ".");
            } else {
                System.out.println("Insufficient funds for bank draft.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }
    public void balanceInquiry(List<Customer> customers, String accountNumber) {
        Optional<Customer> customerOptional = findCustomerByAccountNumber(customers, accountNumber);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            double balance = customer.getAccount().getBalance();
            System.out.println("Current balance of account " + accountNumber + ": $" + balance);
        } else {
            System.out.println("Account not found.");
        }
    }
    private Optional<Customer> findCustomerByAccountNumber(List<Customer> customers, String accountNumber) {
        return customers.stream()
                .filter(customer -> customer.getAccount().getAccountNumber().equals(accountNumber))
                .findFirst();
    }
}
