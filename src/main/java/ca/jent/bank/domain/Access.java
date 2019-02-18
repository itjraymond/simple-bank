package ca.jent.bank.domain;

import java.util.UUID;

public class Access {

    private String id;
    private String userId;
    private String email;
    private String password;

    private Access() {}

    public Access(String userId, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Access{" + "id='" + id + '\'' + ", userId='" + userId + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }
}
