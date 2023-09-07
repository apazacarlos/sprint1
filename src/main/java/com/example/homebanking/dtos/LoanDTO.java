package com.example.homebanking.dtos;

import com.example.homebanking.models.ClientLoan;
import com.example.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanDTO {

    private Long id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments;
    public LoanDTO(Loan loan){
        id = loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payments = new ArrayList<>(loan.getPayments());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
