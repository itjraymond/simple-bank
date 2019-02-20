package ca.jent.bank.repositories;

import ca.jent.bank.domain.Access;
import ca.jent.bank.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static List<Access> getDataStore() {
        return new ArrayList<>(accessStore.values());
    }

    public static void setDataStore(List<Access> accesses) {
        accessStore = accesses.stream().collect(Collectors.toMap(Access::getEmail, access -> access));
    }
}
