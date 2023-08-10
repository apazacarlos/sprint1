package com.example.homebanking.dtos;

import com.example.homebanking.models.Transaction;
import com.example.homebanking.models.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private String description;
    private TransactionType type;
    private Double amount;
    private LocalDateTime date;

    public TransactionDTO(Transaction transaction){
        id = transaction.getId();
        description = transaction.getDescription();
        type = transaction.getType();
        amount = transaction.getAmount();
        date = transaction.getDate();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
