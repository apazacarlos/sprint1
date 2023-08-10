package com.example.homebanking;

import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.models.Transaction;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.homebanking.models.TransactionType.CREDIT;
import static com.example.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
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


			Transaction transaction1 = new Transaction("Varios", CREDIT, 850.51, LocalDateTime.now());
			account1.addTransaction(transaction1);
			account1.setBalance(account1.getBalance()+transaction1.getAmount());
			transactionRepository.save(transaction1);
			accountRepository.save(account1);

			Transaction transaction2 = new Transaction("Varios", DEBIT, 1594.68, LocalDateTime.now());
			account3.addTransaction(transaction2);
			account3.setBalance(account3.getBalance()-transaction2.getAmount());
			transactionRepository.save(transaction2);
			accountRepository.save(account3);
		};
	}


}
