package ca.jent.bank.services.pub;

import ca.jent.bank.domain.Access;
import ca.jent.bank.domain.Account;
import ca.jent.bank.domain.User;
import ca.jent.bank.services.pri.AccessService;
import ca.jent.bank.services.pri.AccountService;
import ca.jent.bank.services.pri.UserService;

import java.util.List;

public class BankService {

    private static UserService userService = new UserService();
    private static AccessService accessService = new AccessService();
    private static AccountService accountService = new AccountService();

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
        user.addAccount(account);
        return account;
    }

    public Account getBankAccountFor(User user, String accountId) {
        Account account;
        if (user.accountExist(accountId)) {
            return accountService.getAccountById(accountId);
        }
        throw new RuntimeException("This user account does not exist");
    }

    public List<Account> getBankAccountsFor(User user) {
        return userService.getUserAccounts(user.getId());
    }

    public void deposit(User user, String accountId, double amount) {
        if (user.accountExist(accountId)) {
            accountService.deposit(accountId, amount);
            return;
        }
        throw new RuntimeException("Account does not belong to this user.");
    }

    public void withdraw(User user, String accountId, double amount) {
        if (user.accountExist(accountId)) {
            accountService.withdraw(accountId, amount);
            return;
        }
        throw new RuntimeException("Account does not belong to this user.");
    }
}
