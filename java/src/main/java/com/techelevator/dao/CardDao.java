package com.techelevator.dao;

import com.techelevator.model.Card;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CardDao {

    Map<Card, Integer> getCardsInCollection(int collectionId);
    Card getCardById(UUID cardId);
    Card createNewCard(Card card);
}
