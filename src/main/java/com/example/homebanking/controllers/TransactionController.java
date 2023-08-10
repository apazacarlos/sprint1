package com.example.homebanking.controllers;

import com.example.homebanking.dtos.TransactionDTO;
import com.example.homebanking.models.Transaction;
import com.example.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        List<Transaction> listTransaction = transactionRepository.findAll();
        List<TransactionDTO> listTransactionDTO = listTransaction
                .stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toList());
        return listTransactionDTO;
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDTO getTransactionById(@PathVariable Long id){
        return new TransactionDTO(transactionRepository.findById(id).orElse(null));
    }
}
