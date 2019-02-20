package ca.jent.bank.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class AccountTransaction {
    private String id;
    private String accountId;
    private String type;
    private double amount;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime timestamp;

    public AccountTransaction(){}

    public AccountTransaction(String accountId, String type, double amount) {
        this.id = UUID.randomUUID().toString();
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountTransaction that = (AccountTransaction) o;
        return Double.compare(
                that.amount,
                amount
        ) == 0 && id.equals(that.id) && accountId.equals(that.accountId) && type.equals(that.type) && timestamp.equals(
                that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, type, amount, timestamp);
    }

    @Override
    public String toString() {
        return "AccountTransaction{" + "id='" + id + '\'' + ", accountId='" + accountId + '\'' + ", type='" + type + '\'' + ", amount=" + amount + ", timestamp=" + timestamp + '}';
    }
}
