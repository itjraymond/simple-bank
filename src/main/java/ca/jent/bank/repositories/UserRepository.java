package ca.jent.bank.repositories;

import ca.jent.bank.domain.User;

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
}
