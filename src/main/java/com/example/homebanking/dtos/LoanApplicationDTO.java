package com.example.homebanking.dtos;

import com.example.homebanking.models.Account;
import com.example.homebanking.models.ClientLoan;
import com.example.homebanking.models.Loan;

import java.util.stream.Collectors;

public class LoanApplicationDTO {
    public Long loanId;
    public Double amount;
    public Integer payments;
    public String toAccountNumber;

    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
