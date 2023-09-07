package com.example.homebanking.controllers;

import com.example.homebanking.dtos.TransactionDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.models.Transaction;
import com.example.homebanking.models.TransactionType;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;



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

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransactions(Authentication authentication, @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
                                                          @RequestParam Double amount, @RequestParam String description) {

        Client clientAuthenticated = clientRepository.findByEmail(authentication.getName());

        //String fromAccount = accountRepository.findAccountByClient(clientAuthenticated);
        //Set<Account> accounts = clientAuthenticated.getAccounts();
        //Account fromAccount = accounts.stream().filter(account -> accountRepository.)
        Account fromAccount = accountRepository.findAccountByNumberAndClient(fromAccountNumber, clientAuthenticated);

        if ((fromAccountNumber.isBlank() || toAccountNumber.isBlank() || amount == null || description.isBlank())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }else if(fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }else if(!accountRepository.existsByNumber(fromAccountNumber)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }else if(!accountRepository.existsAccountByNumberAndClient(fromAccountNumber, clientAuthenticated)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }else if (!accountRepository.existsByNumber(toAccountNumber)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }else if(fromAccount.getBalance()<amount){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }else{

            Account toAccount = accountRepository.findAccountByNumber(toAccountNumber);

            //creo las transacciones
            Transaction debitTransaction = new Transaction(fromAccountNumber + " " + description, TransactionType.DEBIT, -amount, LocalDateTime.now());
            Transaction creditTransaction = new Transaction(toAccountNumber + " " + description, TransactionType.CREDIT, amount, LocalDateTime.now());

            fromAccount.addTransaction(debitTransaction);
            toAccount.addTransaction(creditTransaction);

            //modifico los balances, para que se sumen o resten dependiendo el caso.
            Double newFromAccountBalance = fromAccount.getBalance()-amount;
            Double newToAccountBalance = toAccount.getBalance()+amount;
            fromAccount.setBalance(newFromAccountBalance);
            toAccount.setBalance(newToAccountBalance);

            //guardo las transactions y los account con los nuevos balances
            transactionRepository.save(debitTransaction);
            transactionRepository.save(creditTransaction);

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}