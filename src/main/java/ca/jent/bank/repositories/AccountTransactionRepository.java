package ca.jent.bank.repositories;

import ca.jent.bank.domain.AccountTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountTransactionRepository {

    private static Map<String, AccountTransaction> accountTransactionStore = new HashMap<>();

    public static void save(AccountTransaction accountTransaction) {
        accountTransactionStore.put(accountTransaction.getId(), accountTransaction);
    }

    public static AccountTransaction getAccountTransactionById(String accountTransactionId) {
        if (accountTransactionStore.containsKey(accountTransactionId)) {
            return accountTransactionStore.get(accountTransactionId);
        }
        throw new RuntimeException("Account Transaction with id '" + accountTransactionId + "' not found.");
    }

    public static List<AccountTransaction> getAccountTransactionByAccountId(String accountId) {
        return accountTransactionStore
                .values()
                .stream()
                .filter(accountTransaction -> accountTransaction.getAccountId().equals(accountId))
                .collect(Collectors.toList());
    }

    public static List<AccountTransaction> getDataStore() {
        return new ArrayList<>(accountTransactionStore.values());
    }

    public static void setDataStore(List<AccountTransaction> accountTransactions) {
        accountTransactionStore = accountTransactions.stream().collect(Collectors.toMap(AccountTransaction::getId, accountTransaction -> accountTransaction));
    }
}
