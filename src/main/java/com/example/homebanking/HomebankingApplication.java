package com.example.homebanking;

import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) {
		return (args)-> {

			Client client = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(client);
			Client client1 = new Client("Carlos", "Apaza", "charlie@mindhub.com");
			clientRepository.save(client1);

			Account account1 = new Account();
			account1.setNumber("VIN-001");
			account1.setBalance(5000f);
			account1.setLocalDate(LocalDate.now());
			account1.setClient(client);
			Account account2 = new Account();
			account2.setNumber("VIN-002");
			account2.setBalance(7500f);
			account2.setLocalDate(LocalDate.now().plusDays(1));
			account1.setClient(client);

			client.addAccount(account1);
			client.addAccount(account2);
			accountRepository.save(account1);
			accountRepository.save(account2);


			Account account3 = new Account();
			account3.setNumber("VIN-001");
			account3.setBalance(8000f);
			account3.setLocalDate(LocalDate.now());
			account3.setClient(client1);
			Account account4 = new Account();
			account4.setNumber("VIN-002");
			account4.setBalance(15000f);
			account4.setLocalDate(LocalDate.now().plusDays(1));
			account4.setClient(client1);

			client.addAccount(account3);
			client.addAccount(account4);
			accountRepository.save(account3);
			accountRepository.save(account4);

		};
	}


}
