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

import static com.example.homebanking.models.CardType.CREDIT;
import static com.example.homebanking.models.CardType.DEBIT;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    public int getRandomCardNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
    public int getRandomCvvCardNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<CardDTO> createCards(Authentication authentication, @RequestParam CardColor cardColor, @RequestParam CardType cardType){
        if(authentication != null){
            int cardNumber1 = getRandomCardNumber(1000, 9999);
            int cardNumber2 = getRandomCardNumber(1000, 9999);
            int cardNumber3 = getRandomCardNumber(1000, 9999);
            int cardNumber4 = getRandomCardNumber(1000, 9999);

            String cardNumber = cardNumber1 + " "+ cardNumber2+ " "+ cardNumber3+ " "+ cardNumber4;

            int cvvNumber = getRandomCvvCardNumber(100, 999);
            Client client = clientRepository.findByEmail(authentication.getName());
            String cardholder = client.getFirstName() + " " + client.getLastName();
            Set<Card> cards = client.getCards();
            int counterD = 0;
            int counterC = 0;
            for (Card element: cards){
                if(element.getType()==DEBIT){
                    counterD +=1;
                }else if(element.getType()==CREDIT){
                    counterC +=1;
                }
            }
            if(counterD >= 3){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else if (counterD < 3){
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
            }else if(counterC >= 3){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else if (counterC < 3){
                Card card;
                card = new Card(cardholder,
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
