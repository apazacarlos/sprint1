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

		};
	}


}
