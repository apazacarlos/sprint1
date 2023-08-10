package com.example.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    //propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String description;
    private TransactionType type;
    private Double amount;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    //Constructores
    public Transaction() {
    }

    public Transaction(String description, TransactionType type, Double amount, LocalDateTime date) {
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    //metodos

    public Long getId(){
        return id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Account getAccount(){
        return account;
    }

    public void setAccount (Account account){
        this.account = account;
    }
}
