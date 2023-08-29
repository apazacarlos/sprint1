package com.example.homebanking.models;

import com.example.homebanking.repositories.AccountRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {

    //propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String firstName, lastName, email, password;
    private Boolean isAdmin;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Card> cards = new HashSet<>();

    //constructores
    public Client() {
    }

    public Client(String firstName, String lastName, String email, String password, Boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    //metodos
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);
    }

    public void addLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }

    public List<ClientLoan> getLoans(){
        return new ArrayList<>(clientLoans);
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void addCard(Card card){
        card.setClient(this);
        this.cards.add(card);
    }
}
