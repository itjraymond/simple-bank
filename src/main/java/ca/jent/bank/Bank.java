package ca.jent.bank;

import ca.jent.bank.domain.Access;
import ca.jent.bank.domain.Account;
import ca.jent.bank.domain.AccountTransaction;
import ca.jent.bank.domain.User;
import ca.jent.bank.services.pri.AccessService;
import ca.jent.bank.services.pri.AccountService;
import ca.jent.bank.services.pri.AccountTransactionService;
import ca.jent.bank.services.pri.UserService;
import ca.jent.bank.services.pub.BankService;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Bank {

    private Scanner scanner = new Scanner(System.in);
    private static BankService bankService = new BankService();

    private User userContext;
    private Account accountContext;

    public static void main(String[] args) {

        Bank cliBank = new Bank();
        bankService.restoreDataStores();

        while(true) {
            try {
                cliBank.displayOperations();
                System.out.println("");
                cliBank.displayCurrentState();
                System.out.println("");
                System.out.print("> ");
                String operation = cliBank.scanner.nextLine();
                switch (operation) {
                    case "1":
                        cliBank.userContext = cliBank.signIn();
                        cliBank.accountContext = null;
                        break;

                    case "2":
                        cliBank.userContext = cliBank.register();
                        cliBank.accountContext = null;
                        break;

                    case "3":
                        cliBank.showUsers();
                        break;

                    case "4":
                        cliBank.accountContext = cliBank.createBankAccount(cliBank.userContext);
                        break;

                    case "5":
                        cliBank.userContext = cliBank.switchUser();
                        cliBank.accountContext = null;
                        break;

                    case "6":
                        cliBank.accountContext = cliBank.switchAccount(cliBank.userContext);
                        break;

                    case "7":
                        cliBank.accountContext = cliBank.switchAccount(cliBank.userContext);
                        break;

                    case "8":
                        cliBank.deposit(cliBank.userContext, cliBank.accountContext.getId());
                        break;

                    case "9":
                        cliBank.withdraw(cliBank.userContext, cliBank.accountContext.getId());
                        break;

                    case "10":
                        cliBank.showAccountTransactions(cliBank.userContext, cliBank.accountContext.getId());
                        break;

                    case "11":
                        cliBank.saveDataStores();
                        break;

                    case "12":
                        cliBank.showUserAccounts();
                        break;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void showUserAccounts() {
        bankService.getAccountByUser(userContext)
                   .forEach(System.out::println);
    }

    public User register() {
        System.out.println("Enter your fist name:");
        String firstname = scanner.nextLine();
        System.out.println("Enter your last name:");
        String lastname = scanner.nextLine();
        System.out.println("Enter your eamil:");
        String email = scanner.nextLine();
        System.out.println("Enter a password:");
        String password = scanner.nextLine();
        User user = bankService.registerUser(firstname, lastname, email, password);
        return user;
    }

    public User signIn() {
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        return bankService.signIn(email, password);
    }

    public Account createBankAccount(User user) {
        System.out.println("Enter the account type:  SAVING or CHEQUING");
        String type = scanner.nextLine();
        return bankService.createBankAccountFor(user, type);
    }

    public void deposit(User user, String accountId) {
        System.out.println("Enter the amount you want to deposit:");
        String amount = scanner.nextLine();
        bankService.deposit(user, accountId, Double.valueOf(amount));
    }

    public void withdraw(User user, String accountId) {
        System.out.println("Enter the amount you want to withdraw:");
        String amount = scanner.nextLine();
        bankService.withdraw(user, accountId, Double.valueOf(amount));
    }

    public User switchUser() {
        return signIn();
    }

    public Account switchAccount(User user) {
        user.getUserAccounts().stream().forEach(System.out::println);
        System.out.println("Enter the first few char of the account ID you want to switch to:");
        String accountIdPart = scanner.nextLine();
        return user.getUserAccounts()
                   .stream()
                   .filter(account -> account.getId().startsWith(accountIdPart))
                   .findFirst()
                   .orElseThrow(() -> new RuntimeException("Did not find the account"));
    }

    public void showAccountTransactions(User user, String accountId) {
        List<AccountTransaction> tx = bankService.getAccountActivity(user, accountId);
        tx.stream()
          .sorted(Comparator.comparing(AccountTransaction::getTimestamp))
          .forEach(System.out::println);
    }

    public void showUsers() {
        bankService.getBankUsers().stream().forEach(System.out::println);
    }

    public void saveDataStores() {
        bankService.saveDataStores();
    }

    public void restoreDataStores() {
        bankService.restoreDataStores();
    }

    public void displayCurrentState() {
        if (userContext != null) {
            System.out.println("Current User:    " + userContext.getFirstname() + " " + userContext.getLastname());

            if (accountContext != null) {
                System.out.println("Current Account: " + accountContext.getId() + "  type: " + accountContext.getType());
                System.out.println("Current Balance: $" + accountContext.getBalance());
            }
        }
    }

    public void displayOperations() {
        System.out.println("Select one of the following operations");
        System.out.println(" 1 Login");
        System.out.println(" 2 Register");
        System.out.println(" 3 Show bank users");
        if (userContext != null) {
            System.out.println(" 4 Create a new Bank Account.");
            System.out.println(" 5 Switch user");
            System.out.println(" 6 Select user bank account");

            if (accountContext != null) {
                System.out.println(" 7 Switch account");
                System.out.println(" 8 Deposit into current account");
                System.out.println(" 9 Withdraw from current account");
                System.out.println(" 10 Show account transactions");
            }
            System.out.println(" 13 Show current user accounts");
        }
        System.out.println(" 11 Save data stores");
        System.out.println(" 12 Restore data stores");

    }
}
