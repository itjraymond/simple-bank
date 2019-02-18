package ca.jent.bank.services.pri;

import ca.jent.bank.domain.Access;
import ca.jent.bank.domain.User;
import ca.jent.bank.repositories.AccessRepository;

/**
 * Hard dependency on User
 */
public class AccessService {

    public Access create(User user, String password) {
        Access access = new Access(user.getId(), user.getEmail(), password);
        AccessRepository.save(access);
        return access;
    }

    public Access authenticate(String email, String password) {
        return AccessRepository.getAccessFor(email,password);
    }

}
