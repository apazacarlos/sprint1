package com.example.homebanking.controllers;

import com.example.homebanking.dtos.CardDTO;
import com.example.homebanking.models.CardColor;
import com.example.homebanking.models.Card;
import com.example.homebanking.models.CardType;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.CardRepository;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    public int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    @RequestMapping("clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getCards()
                .stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }
    @PostMapping("/clients/current/cards")
    public ResponseEntity<CardDTO> createCards(Authentication authentication, @RequestParam CardColor cardColor, @RequestParam CardType cardType){
        if(authentication != null){

            String cardNumber;

            do{
                int cardNumber1 = getRandomNumber(1000, 9999);
                int cardNumber2 = getRandomNumber(1000, 9999);
                int cardNumber3 = getRandomNumber(1000, 9999);
                int cardNumber4 = getRandomNumber(1000, 9999);
                cardNumber = cardNumber1 + " "+ cardNumber2+ " "+ cardNumber3+ " "+ cardNumber4;
            }
            while(cardRepository.existsByNumber(cardNumber));

            int cvvNumber = getRandomNumber(100, 999);
            Client client = clientRepository.findByEmail(authentication.getName());
            String cardholder = client.getFirstName() + " " + client.getLastName();

            long numberOfCards = cardRepository.countCardByClientAndTypeAndColor(client, cardType, cardColor);
            boolean existsCards = cardRepository.existsCardByClientAndTypeAndColor(client, cardType, cardColor);

            if(numberOfCards >= 3 || existsCards){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else{
                Card card = new Card(cardholder,
                        cardType,
                        cardColor,
                        cardNumber,
                        cvvNumber,
                        LocalDate.now(),
                        LocalDate.now().plusYears(5));
                cardRepository.save(card);
                client.addCard(card);
                clientRepository.save(client);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
