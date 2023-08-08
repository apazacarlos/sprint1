package com.example.homebanking.dtos;

import com.example.homebanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private Long id;

    private String name, lastname, email;
    private Set<AccountDTO> accounts;

    public ClientDTO(Client client){
        id= client.getId();
        name = client.getName();
        lastname = client.getLastname();
        email = client.getEmail();
        accounts = client.getAccounts()
                .stream()
                .map(element -> new AccountDTO(element))
                .collect(Collectors.toSet());
    }

    public Long getId(){return id;}

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
}
