package com.example.homebanking.controllers;

import com.example.homebanking.dtos.ClientLoanDTO;
import com.example.homebanking.dtos.LoanApplicationDTO;
import com.example.homebanking.dtos.LoanDTO;
import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
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
public class LoanController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;



    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        List<Loan> listLoans = loanRepository.findAll();
        List<LoanDTO> listLoansDTO = listLoans.stream()
                .map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
        return listLoansDTO;
    }
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<LoanApplicationDTO> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplication){
        if(authentication!=null)
        {
            Client client = clientRepository.findByEmail(authentication.getName());
            Long idLoan = loanApplication.getLoanId();
            Loan loan = loanRepository.findLoanById(idLoan);
            String nameLoan = loan.getName();
            String accountNumber = loanApplication.getToAccountNumber();

            if(loanApplication.getPayments() == 0 || loanApplication.getAmount() <= 0){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else if(!loanRepository.existsLoanById(idLoan)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else if(!loanRepository.existsPaymentByName(nameLoan)){///
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else if(loanApplication.getAmount() > loan.getMaxAmount()){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else if(loan.getMaxAmount() < loanApplication.getAmount()){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else if(!loan.getPayments().stream().anyMatch(payment -> payment.equals(loanApplication.getPayments()))){///
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else if(!accountRepository.existsByNumber(accountNumber)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else if(!accountRepository.existsAccountByNumberAndClient(accountNumber, client)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else{

                Account account = accountRepository.findAccountByNumber(accountNumber);
                ClientLoan clientLoan = new ClientLoan(loanApplication.getAmount(), loanApplication.getPayments());
                client.addLoan(clientLoan);
                loan.addClient(clientLoan);

                clientLoanRepository.save(clientLoan);

                Transaction transaction = new Transaction(nameLoan + " loan approved", TransactionType.CREDIT, loanApplication.getAmount(), LocalDateTime.now());
                account.addTransaction(transaction);
                account.setBalance(account.getBalance()+ loanApplication.getAmount());

                transactionRepository.save(transaction);

                return new ResponseEntity<>(HttpStatus.CREATED);

            }
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
