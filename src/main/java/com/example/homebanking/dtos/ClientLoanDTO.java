package com.example.homebanking.dtos;

import com.example.homebanking.models.Client;
import com.example.homebanking.models.ClientLoan;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientLoanDTO {

    private Long id;
    private Double amount;
    private Integer payments;
    private String name;
    private Long idLoan;

    public ClientLoanDTO(ClientLoan clientLoan) {
        id = clientLoan.getId();
        amount = clientLoan.getAmount();
        payments = clientLoan.getPayments();
        name = clientLoan.getLoan().getName();
        idLoan = clientLoan.getLoan().getId();
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    public Long getIdLoan() {
        return idLoan;
    }
}
