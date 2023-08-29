package com.example.homebanking.controllers;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        List<Account> listAccount = accountRepository.findAll();
        List<AccountDTO> listAccountDTO = listAccount
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
        return listAccountDTO;
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<AccountDTO> createAccount(Authentication authentication){

        if(authentication != null){
            int accountNumber = getRandomNumber(10000000, 99999999);
            Client client = clientRepository.findByEmail(authentication.getName());
            if(client.getAccounts().size() >= 3){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else{
                Account account = new Account(("VIN-"+accountNumber), 0d, LocalDate.now());
                accountRepository.save(account);
                client.addAccount(account);
                clientRepository.save(client);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
