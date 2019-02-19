package ca.jent.bank.repositories;

import ca.jent.bank.domain.Account;
import ca.jent.bank.domain.User;
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
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {

    private static Map<String, User> userStore = new HashMap<>();

    public static void save(User user) {
        userStore.put(user.getId(), user);
    }

    public static User getUserById(String userId) {
        if (userStore.containsKey(userId)) {
            return userStore.get(userId);
        }
        throw new RuntimeException("User with id '" + userId + "' not found.");
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    public static void deleteUser(String userId) {
        if (userStore.containsKey(userId)) {
            userStore.remove(userId);
            return;
        }

        throw new RuntimeException("Cannot delete user with id '" + userId + "' - User does not exist.");
    }

    public static void marshall() throws IOException, URISyntaxException {

        File store = getFile("data-stores/user-repository.json");

        ObjectMapper jsonMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        List<User> users = new ArrayList<>(userStore.values());
        Files.write(store.toPath(), jsonMapper.writeValueAsBytes(users), StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void unmarshall() throws URISyntaxException, IOException {

        File store = getFile("data-stores/user-repository.json");

        ObjectMapper jsonMapper = new ObjectMapper();
        if (store.length() > 0) {
            List<User> users = jsonMapper.readValue(store, new TypeReference<List<User>>(){});
            userStore = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        } else {
            userStore = new HashMap<>();
        }
    }

    public static File getFile(String filePath) throws URISyntaxException {
        URL url = AccountRepository.class.getClassLoader().getResource(filePath);

        Path location = Paths.get(url.toURI());
        return location.toFile();
    }

}
