package com.example.homebanking.repositories;

import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long>
{

    String findAccountByClient(Client client);
    Boolean existsByNumber(String number);
    Boolean existsAccountByNumberAndClient(String number, Client client);
    Account findAccountByNumberAndClient(String number, Client client);
    Account findAccountByNumber(String number);

}
