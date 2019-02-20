package ca.jent.bank.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class User {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    @JsonProperty("accounts")
    private Map<String, Account> accounts = new HashMap<>();

    public User() {}

    public User(String firstname, String lastname, String email) {
        this.id = UUID.randomUUID().toString();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addAccount(Account account) {
        this.accounts.put(account.getId(), account);
    }

    public Account removeAccountById(String accountId) {
        if (accounts.containsKey(accountId)) {
            Account account = accounts.get(accountId);
            accounts.remove(accountId);
            return account;
        }
        throw new RuntimeException("Cannot remove " + this.firstname + " " + this.lastname + "'s Account with id '" + accountId + "' - Account not found.");
    }

    public List<Account> getUserAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public boolean accountExist(String accountId){
        return accounts.containsKey(accountId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id) && firstname.equals(user.firstname) && lastname.equals(user.lastname) && email.equals(
                user.email) && accounts.equals(user.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, email, accounts);
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + ", email='" + email + '\'' + ", accounts=" + accounts + '}';
    }
}
