package ca.jent.bank.services.pri;

import ca.jent.bank.domain.Account;
import ca.jent.bank.repositories.AccountRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PersistenceService {

    public void saveAllRepositories() {
        try {
            AccountRepository.marshall();

        } catch (IOException | URISyntaxException e) {
                throw new RuntimeException("JSON Parsing exception!!  or Problems with the file");
        }
    }

    public void restoreAllRepositories() {
        try {
            AccountRepository.unmarshall();

        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException("Unable to recover data store.");
        }
    }



    public static void marshall() throws IOException, URISyntaxException {

        File store = getFile("data-stores/account-repository.json");

        ObjectMapper jsonMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        List<Account> accounts = new ArrayList<>(accountStore.values());
        Files.write(store.toPath(), jsonMapper.writeValueAsBytes(accounts), StandardOpenOption.TRUNCATE_EXISTING);

    }

    public static void unmarshall() throws URISyntaxException, IOException {

        File store = getFile("data-stores/account-repository.json");

        ObjectMapper jsonMapper = new ObjectMapper();
        if (store.length() > 0) {
            List<Account> accounts = jsonMapper.readValue(store, new TypeReference<List<Account>>(){});
            accountStore = accounts.stream().collect(Collectors.toMap(Account::getId, account -> account));
        } else {
            accountStore = new HashMap<>();
        }
    }

    public static File getFile(String filePath) throws URISyntaxException {
        URL url = AccountRepository.class.getClassLoader().getResource(filePath);

        Path location = Paths.get(url.toURI());
        return location.toFile();
    }

}
