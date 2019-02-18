package ca.jent.bank.repositories;

import ca.jent.bank.domain.Access;

import java.util.HashMap;
import java.util.Map;

public class AccessRepository {

    private static Map<String, Access> accessStore = new HashMap<>();

    public static void save(Access access) {
        accessStore.put(access.getEmail(), access);
    }


    public static Access getAccessFor(String email, String password) {
        Access access;
        if (accessStore.containsKey(email)) {
            access = accessStore.get(email);
            if (access.getPassword().equals(password)) {
                return access;
            }
        }
        throw new RuntimeException("Access denied");
    }
}
