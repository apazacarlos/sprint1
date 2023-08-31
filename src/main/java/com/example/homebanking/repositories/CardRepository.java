package com.example.homebanking.repositories;

import com.example.homebanking.models.Card;
import com.example.homebanking.models.CardColor;
import com.example.homebanking.models.CardType;
import com.example.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {

  Integer countCardByClientAndTypeAndColor(Client client, CardType cardType, CardColor cardColor);
  Boolean existsCardByClientAndTypeAndColor(Client client, CardType cardType, CardColor cardColor);
  Boolean existsByNumber(String number);
}
