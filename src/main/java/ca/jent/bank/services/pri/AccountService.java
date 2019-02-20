package ca.jent.bank.services.pri;

import ca.jent.bank.domain.Account;
import ca.jent.bank.domain.AccountTransaction;
import ca.jent.bank.repositories.AccountRepository;
import ca.jent.bank.repositories.AccountTransactionRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AccountService {


    public Account createAccount(String type) {
        Account account = new Account(type, 0.0);
        AccountTransaction accountTransaction = new AccountTransaction(account.getId(), "CREATE", 0.0);
        AccountRepository.save(account);
        AccountTransactionRepository.save(accountTransaction);

        return account;
    }

    public Account getAccountById(String accountId) {
        return AccountRepository.getAccountById(accountId);
    }

    public void deposit(String accountId, double amount) {
        Account account = getAccountById(accountId);
        account.setBalance(account.getBalance() + amount);
        AccountTransaction accountTransaction = new AccountTransaction(accountId, "DEPOSIT", amount);
        AccountTransactionRepository.save(accountTransaction);
    }

    public void withdraw(String accountId, double amount) {
        Account account = getAccountById(accountId);
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            AccountTransaction accountTransaction = new AccountTransaction(accountId, "WITHDRAW", amount);
            AccountTransactionRepository.save(accountTransaction);
            return;
        }
        throw new RuntimeException("Not enough funds.");
    }

    public void deleteAccountById(String accountId) {
        Account account = getAccountById(accountId);
        AccountTransaction accountTransaction = new AccountTransaction(accountId, "DELETE", account.getBalance());
        AccountRepository.delete(accountId);
        AccountTransactionRepository.save(accountTransaction);
    }

    public List<Account> getAccountsByIds(List<String> accountIds) {
        return AccountRepository.getAccountByIds(accountIds);
    }
}
