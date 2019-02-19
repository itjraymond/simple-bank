package ca.jent.bank.domain;

import java.util.Objects;
import java.util.UUID;

public class Account {
    private String id;
    private double balance;
    private String type;

    public Account(){
        this("CHEQUING", 0.0);
    }

    public Account(String type) {
        this(type, 0.0);
    }

    public Account(String type, double balance) {
        this.id = UUID.randomUUID().toString();
        this.balance = balance;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 && id.equals(account.id) && type.equals(account.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, type);
    }

    @Override
    public String toString() {
        return "Account{" + "id='" + id + '\'' + ", balance=" + balance + ", type='" + type + '\'' + '}';
    }
}
