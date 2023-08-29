package com.example.homebanking.dtos;

import com.example.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private Long id;

    private String firstName, lastName, email;
    private Boolean isAdmin;
    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;
    private Set<CardDTO> cards;

    public ClientDTO(Client client){
        id= client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        isAdmin = client.getAdmin();
        accounts = client.getAccounts()
                .stream()
                .map(element -> new AccountDTO(element))
                .collect(Collectors.toSet());
        loans = client.getLoans()
                .stream()
                .map(element -> new ClientLoanDTO(element))
                .collect(Collectors.toSet());
        cards = client.getCards()
                .stream()
                .map(element -> new CardDTO(element))
                .collect(Collectors.toSet());
    }

    public Long getId(){return id;}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
