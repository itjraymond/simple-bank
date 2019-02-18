package ca.jent.bank.services.pri;

import ca.jent.bank.domain.AccountTransaction;
import ca.jent.bank.domain.User;
import ca.jent.bank.repositories.AccountTransactionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AccountTransactionService {

    public List<AccountTransaction> getAccountTransactionByAccountId(String accountId) {
        return AccountTransactionRepository.getAccountTransactionByAccountId(accountId);
    }

    public List<AccountTransaction> getAccountTransactions(List<String> accountIds) {
        return accountIds
                .stream()
                .sorted()
                .flatMap(accountId -> AccountTransactionRepository.getAccountTransactionByAccountId(accountId).stream().sorted(
                        Comparator.comparing(AccountTransaction::getTimestamp)))
                .collect(Collectors.toList());
    }
}
