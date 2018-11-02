package de.adesso.blog.cep.creditcarddemo.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

public class Transaction {

    private CreditCard creditCard;

    private BigDecimal amount;

    private String receiverIban;

    private Date executionDate;

    private Locale executionLocation;

    private boolean personalApproved;
    
    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReceiverIban() {
        return receiverIban;
    }

    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public Locale getExecutionLocation() {
        return executionLocation;
    }

    public void setExecutionLocation(Locale executionLocation) {
        this.executionLocation = executionLocation;
    }

    public boolean isPersonalApproved() {
        return personalApproved;
    }

    public void setPersonalApproved(boolean personalApproved) {
        this.personalApproved = personalApproved;
    }
}
