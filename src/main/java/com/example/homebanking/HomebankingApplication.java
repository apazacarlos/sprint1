package com.example.homebanking;

import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args)-> {

			Client client = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(client);
			Client client1 = new Client("Carlos", "Apaza", "charlie@mindhub.com");
			clientRepository.save(client1);

			Account account1 = new Account("VIN-001",5000d,LocalDate.now());
			Account account2 = new Account("VIN-002",7500d,LocalDate.now().plusDays(1));
			client.addAccount(account1);
			client.addAccount(account2);
			accountRepository.save(account1);
			accountRepository.save(account2);


			Account account3 = new Account("VIN-001",4000d,LocalDate.now());
			Account account4 = new Account("VIN-002",3500d,LocalDate.now().plusDays(1));
			client1.addAccount(account3);
			client1.addAccount(account4);
			accountRepository.save(account3);
			accountRepository.save(account4);


			Transaction transaction1 = new Transaction("Varios", TransactionType.CREDIT, 850.51, LocalDateTime.now());
			account1.addTransaction(transaction1);
			account1.setBalance(account1.getBalance()+transaction1.getAmount());
			transactionRepository.save(transaction1);
			accountRepository.save(account1);

			Transaction transaction2 = new Transaction("Varios", TransactionType.DEBIT, 1594.68, LocalDateTime.now());
			account3.addTransaction(transaction2);
			account3.setBalance(account3.getBalance()-transaction2.getAmount());
			transactionRepository.save(transaction2);
			accountRepository.save(account3);


			Loan loan1 = new Loan("Mortgage", 500000d, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal", 100000d, List.of(6,12,24));
			Loan loan3 = new Loan("Vehicle", 300000d, List.of(6,12,24,36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000d, 60, client, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000d, 12, client, loan2);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan(100000d, 24, client1, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000d, 36, client1, loan3);

			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card("Melba Morel", CardType.DEBIT, CardColor.GOLD, "5444 6555 7666 8777", 678, LocalDate.now(), LocalDate.now().plusYears(5));
			Card card2 = new Card("Melba Morel", CardType.CREDIT, CardColor.TITANIUM, "4333 5444 9888 7666", 250, LocalDate.now(), LocalDate.now().plusYears(5));
			Card card3 = new Card("Carlos Apaza", CardType.DEBIT, CardColor.SILVER, "5152 6463 8685 0405", 749, LocalDate.now(), LocalDate.now().plusYears(5));

			client.addCard(card1);
			client.addCard(card2);
			client1.addCard(card3);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

		};
	}


}
