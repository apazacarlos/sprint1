package com.example.homebanking.dtos;

import com.example.homebanking.models.Card;
import com.example.homebanking.models.CardColor;
import com.example.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private Integer cvv;
    private LocalDate fromDate, thruDate;

    public CardDTO(Card card) {
        id = card.getId();
        cardHolder = card.getCardHolder();
        type = card.getType();
        color = card.getColor();
        number = card.getNumber();
        cvv = card.getCvv();
        fromDate = card.getFromDate();
        thruDate = card.getThruDate();
    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }
}
