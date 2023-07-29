import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Base class representing a Bank Account
class BankAccount {
    private String accountNumber;
    private double accountBalance;

    // Constructor for BankAccount
    BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
        this.accountBalance = 0.0;
    }

    // Method to deposit money into the account
    void deposit(double amount) {
        accountBalance += amount;
    }

    // Method to withdraw money from the account
    void withdraw(double amount) {
        accountBalance -= amount;
    }

    // Method to get the current balance of the account
    double getAccountBalance() {
        return accountBalance;
    }
}

// CheckingAccount class inheriting from BankAccount
class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    // Constructor for CheckingAccount
    CheckingAccount(String accountNumber, double overdraftLimit) {
        super(accountNumber);
        this.overdraftLimit = overdraftLimit;
    }

    // Override withdraw method to handle overdraft limit
    @Override
    void withdraw(double amount) {
        double availableFunds = getAccountBalance() + overdraftLimit;
        if (amount <= availableFunds) {
            super.withdraw(amount);
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }

    // Method to simulate spending using a checking account
    void spend(double amount) {
        withdraw(amount);
    }
}

// SavingsAccount class inheriting from BankAccount
class SavingsAccount extends BankAccount {
    private double interestRate;

    // Constructor for SavingsAccount
    SavingsAccount(String accountNumber, double interestRate) {
        super(accountNumber);
        this.interestRate = interestRate;
    }

    // Method to calculate and deposit interest to the savings account
    void calculateInterest() {
        double interest = getAccountBalance() * interestRate / 100;
        deposit(interest);
    }
}

// CreditCardAccount class inheriting from BankAccount
class CreditCardAccount extends BankAccount {
    private double creditLimit;

    // Constructor for CreditCardAccount
    CreditCardAccount(String accountNumber, double creditLimit) {
        super(accountNumber);
        this.creditLimit = creditLimit;
    }

    // Override withdraw method to handle credit limit
    @Override
    void withdraw(double amount) {
        double availableCredit = creditLimit + getAccountBalance();
        if (amount <= availableCredit) {
            super.withdraw(amount);
        } else {
            System.out.println("Exceeded credit limit for withdrawal.");
        }
    }

    // Method to simulate spending using a credit card account
    void spend(double amount) {
        withdraw(amount);
    }
}

public class BankSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CheckingAccount checkingAccount = new CheckingAccount("C1001", 500);
        SavingsAccount savingsAccount = new SavingsAccount("S2001", 2.5);
        CreditCardAccount creditCardAccount = new CreditCardAccount("CC3001", 1000);

        System.out.println("Welcome to the Bank!");

        while (true) {
            // Display account type selection menu
            System.out.println("\nSelect an account type:");
            System.out.println("1. Checking Account");
            System.out.println("2. Savings Account");
            System.out.println("3. Credit Card Account");
            System.out.println("4. Exit");

            try {
                int choice = scanner.nextInt();

                if (choice == 1) {
                    operateBankAccount(checkingAccount, scanner);
                } else if (choice == 2) {
                    operateBankAccount(savingsAccount, scanner);
                } else if (choice == 3) {
                    operateBankAccount(creditCardAccount, scanner);
                } else if (choice == 4) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (java.util.InputMismatchException e) {
                // Handle the case when the user enters incorrect input (not an integer)
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Consume the invalid input to prevent an infinite loop
            }
        }
        scanner.close();
    }
    // Method to handle operations on a bank account
    private static void operateBankAccount(BankAccount account, Scanner scanner) {
        System.out.println("Select an action:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Get Account Balance");
        System.out.println("4. Exit");

        try {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful.");
                    break;
                case 2:
                    System.out.print("Enter the amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    System.out.println("Withdrawal successful.");
                    break;
                case 3:
                    System.out.println("Account Balance: CAD " + account.getAccountBalance());
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (java.util.InputMismatchException e) {
            // Handle the case when the user enters incorrect input (not a double)
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.nextLine(); // Consume the invalid input to prevent an infinite loop
        }
    }

    // Method to read account data from a file
    private static ArrayList<String> readAccountDataFromFile(String filename) {
        ArrayList<String> accountData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                accountData.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return accountData;
    }

    // Method to write account data to a file
    private static void writeAccountDataToFile(String filename, String accountData) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(accountData);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
