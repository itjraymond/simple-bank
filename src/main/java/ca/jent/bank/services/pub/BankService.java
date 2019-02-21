package ca.jent.bank.services.pub;

import ca.jent.bank.domain.Access;
import ca.jent.bank.domain.Account;
import ca.jent.bank.domain.AccountTransaction;
import ca.jent.bank.domain.User;
import ca.jent.bank.services.pri.AccessService;
import ca.jent.bank.services.pri.AccountService;
import ca.jent.bank.services.pri.AccountTransactionService;
import ca.jent.bank.services.pri.PersistenceService;
import ca.jent.bank.services.pri.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class BankService {

    private static UserService userService = new UserService();
    private static AccessService accessService = new AccessService();
    private static AccountService accountService = new AccountService();
    private static AccountTransactionService accountTransactionService = new AccountTransactionService();
    private static PersistenceService persistenceService = new PersistenceService();

    public User registerUser(String firstname, String lastname, String email, String password) {
        User user = userService.create(firstname, lastname, email);
        accessService.create(user, password);
        return user;
    }

    public User signIn(String email, String password) {
        Access access = accessService.authenticate(email, password);
        return userService.getUserById(access.getUserId());
    }

    public Account createBankAccountFor(User user, String type) {
        Account account = accountService.createAccount(type);
        // user.addAccount(account);
        user.addAccountId(account.getId());
        return account;
    }

    public Account getBankAccountFor(User user, String accountId) {
        if (user.accountIdExist(accountId)) {
            return accountService.getAccountById(accountId);
        }
        throw new RuntimeException("This user account does not exist");
    }

    public List<Account> getBankAccountsFor(User user) {
//        return userService.getUserAccounts(user.getId());
        return accountService.getAccountsByIds(user.getUserAccountIds());
    }

    public void deposit(User user, String accountId, double amount) {
        if (user.accountIdExist(accountId)) {
            accountService.deposit(accountId, amount);
            return;
        }
        throw new RuntimeException("Account does not belong to this user.");
    }

    public void withdraw(User user, String accountId, double amount) {
        if (user.accountIdExist(accountId)) {
            accountService.withdraw(accountId, amount);
            return;
        }
        throw new RuntimeException("Account does not belong to this user.");
    }

    public void deleteUserAccount(User user, String accountId) {
        if (user.accountIdExist(accountId)) {
            accountService.deleteAccountById(accountId);
            user.removeAccountId(accountId);
        }
    }

    public List<Account> getAccountByUser(User user) {
//        List<String> accountIds = user.getUserAccounts().stream().map(Account::getId).collect(Collectors.toList());
        return accountService.getAccountsByIds(user.getUserAccountIds());
//        return accountService.getAccountsByIds(accountIds);
    }

    public List<AccountTransaction> getAccountActivity(User user, String accountId) {
        if (user.accountIdExist(accountId)) {
            return accountTransactionService.getAccountTransactionByAccountId(accountId);
        }
        throw new RuntimeException("Account does not belong to this user.");
    }

    public List<AccountTransaction> getAccountActivity(User user) {
//        List<String> accounts = user.getUserAccounts().stream().map(Account::getId).collect(Collectors.toList());
        return accountTransactionService.getAccountTransactions(user.getUserAccountIds());
    }

    public List<User> getBankUsers() {
        return userService.getAllUsers();
    }

    public void saveDataStores() {
        persistenceService.saveAllRepositories();
    }

    public void restoreDataStores() {
        persistenceService.restoreAllRepositories();
    }
}
