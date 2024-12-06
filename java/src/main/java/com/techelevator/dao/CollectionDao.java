package com.techelevator.dao;

import com.techelevator.model.Card;
import com.techelevator.model.CardCollection;
import com.techelevator.model.CardCollectionDto;

import java.util.List;

public interface CollectionDao {

    List<CardCollection> getAllPublicCollections();

    int removeCardFromCollection(Card card, CardCollection collection);

    CardCollection addCardToCollection(Card card, int collection);

    CardCollection createNewCollection(CardCollectionDto collection);
    CardCollection getCollectionById(int collectionId);
}
