package ca.jent.bank.services.pri;

import ca.jent.bank.domain.Access;
import ca.jent.bank.domain.Account;
import ca.jent.bank.domain.AccountTransaction;
import ca.jent.bank.domain.User;
import ca.jent.bank.repositories.AccessRepository;
import ca.jent.bank.repositories.AccountRepository;
import ca.jent.bank.repositories.AccountTransactionRepository;
import ca.jent.bank.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class PersistenceService {

    public void saveAllRepositories() {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            List<Account> accounts = AccountRepository.getDataStore();
            File accountStore = getFile("data-stores/account-repository.json");
            Files.write(accountStore.toPath(), mapper.writeValueAsBytes(accounts), StandardOpenOption.TRUNCATE_EXISTING);

            List<User> users = UserRepository.getDataStore();
            File userStore = getFile("data-stores/user-repository.json");
            Files.write(userStore.toPath(), mapper.writeValueAsBytes(users), StandardOpenOption.TRUNCATE_EXISTING);

            List<Access> access = AccessRepository.getDataStore();
            File accessStore = getFile("data-stores/access-repository.json");
            Files.write(accessStore.toPath(), mapper.writeValueAsBytes(access), StandardOpenOption.TRUNCATE_EXISTING);

            List<AccountTransaction> accountTransactions = AccountTransactionRepository.getDataStore();
            File accountTxStore = getFile("data-stores/account-transaction-repository.json");
            Files.write(accountTxStore.toPath(), mapper.writeValueAsBytes(accountTransactions), StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("JSON Parsing exception!!  or Problems with the file");
        }
    }

    public void restoreAllRepositories() {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            File accountStore = getFile("data-stores/account-repository.json");
            List<Account> accounts = Collections.emptyList();
            if (accountStore.length() > 0) {
                accounts = mapper.readValue(accountStore, new TypeReference<List<Account>>(){});
            }
            AccountRepository.setDataStore(accounts);

            File userStore = getFile("data-stores/user-repository.json");
            List<User> users = Collections.emptyList();
            if (userStore.length() > 0) {
                users = mapper.readValue(userStore, new TypeReference<List<User>>(){});
            }
            UserRepository.setDataStore(users);

            File accessStore = getFile("data-stores/access-repository.json");
            List<Access> accesses = Collections.emptyList();
            if (accessStore.length() > 0) {
                accesses = mapper.readValue(accessStore, new TypeReference<List<Access>>(){});
            }
            AccessRepository.setDataStore(accesses);

            File accountTxStore = getFile("data-stores/account-transaction-repository.json");
            List<AccountTransaction> accountTransactions = Collections.emptyList();
            if (accountTxStore.length() > 0) {
                accountTransactions = mapper.readValue(accountTxStore, new TypeReference<List<AccountTransaction>>(){});
            }
            AccountTransactionRepository.setDataStore(accountTransactions);

        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unable to recover data store.");
        }
    }


    public static File getFile(String filePath) throws URISyntaxException {
        URL url = AccountRepository.class.getClassLoader().getResource(filePath);

        Path location = Paths.get(url.toURI());
        return location.toFile();
    }

}
