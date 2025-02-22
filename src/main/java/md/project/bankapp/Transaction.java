package md.project.bankapp;

import java.time.LocalDate;
import java.util.Date;

public class Transaction {
    private final Date date;
    private final String sender;
    private final String receiver;
    private final double amount;

    public Transaction(Date date, String sender, String receiver, double amount) {
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }
}
