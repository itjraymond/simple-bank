package ca.jent.bank.repositories;

import ca.jent.bank.domain.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountRepository {
    private static Map<String, Account> accountStore = new HashMap<>();

    public static void save(Account account) {
        accountStore.put(account.getId(), account);
    }

    public static Account getAccountById(String accountId) {
        if (accountStore.containsKey(accountId)) {
            return accountStore.get(accountId);
        }
        throw new RuntimeException("Account (id=" + accountId + ") not found");
    }

    public static List<Account> getAccountByIds(List<String> accountIds) {
        return accountIds.stream()
                         .filter(accountId -> accountStore.containsKey(accountId))
                         .map(accountId -> accountStore.get(accountId))
                         .collect(Collectors.toList());
    }

    public static void delete(String accountId) {
        if (accountStore.containsKey(accountId)) {
            accountStore.remove(accountId);
            return;
        }
        throw new RuntimeException("Cannot delete Account with id '" + accountId + "' - Account not found.");
    }

    public static List<Account> getDataStore() {
        return new ArrayList<>(accountStore.values());
    }

    public static void setDataStore(List<Account> accounts) {
        accountStore = accounts.stream().collect(Collectors.toMap(Account::getId, account -> account));
    }

}
