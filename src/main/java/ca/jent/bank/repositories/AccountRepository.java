package ca.jent.bank.repositories;

import ca.jent.bank.domain.Account;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        return accountIds
                .stream()
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

//    public static void marshall() throws IOException, URISyntaxException {
//
//        File store = getFile("data-stores/account-repository.json");
//
//        ObjectMapper jsonMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
//        List<Account> accounts = new ArrayList<>(accountStore.values());
//        Files.write(store.toPath(), jsonMapper.writeValueAsBytes(accounts), StandardOpenOption.TRUNCATE_EXISTING);
//
//    }
//
//    public static void unmarshall() throws URISyntaxException, IOException {
//
//        File store = getFile("data-stores/account-repository.json");
//
//        ObjectMapper jsonMapper = new ObjectMapper();
//        if (store.length() > 0) {
//            List<Account> accounts = jsonMapper.readValue(store, new TypeReference<List<Account>>(){});
//            accountStore = accounts.stream().collect(Collectors.toMap(Account::getId, account -> account));
//        } else {
//            accountStore = new HashMap<>();
//        }
//    }
//
//    public static File getFile(String filePath) throws URISyntaxException {
//        URL url = AccountRepository.class.getClassLoader().getResource(filePath);
//
//        Path location = Paths.get(url.toURI());
//        return location.toFile();
//    }
}
