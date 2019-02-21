package ca.jent.bank.services.pri;

import ca.jent.bank.domain.User;
import ca.jent.bank.repositories.UserRepository;

import java.util.List;

public class UserService {

    public User create(String firstname, String lastname, String email) {
        User user = new User(firstname, lastname, email);
        UserRepository.save(user);
        return user;
    }

    public User getUserById(String userId) {
        return UserRepository.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return UserRepository.getAllUsers();
    }

    public void deleteUserById(String userId) {
        UserRepository.deleteUser(userId);
    }

//    public List<Account> getUserAccounts(String userId) {
//        User user = UserRepository.getUserById(userId);
//        return user.getUserAccounts();
//    }
}
