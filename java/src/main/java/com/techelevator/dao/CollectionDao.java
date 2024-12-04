package com.techelevator.dao;

import com.techelevator.model.Card;
import com.techelevator.model.CardCollection;

import java.util.List;

public interface CollectionDao {

    List<CardCollection> getAllPublicCollections();

    int removeCardFromCollection(Card card, CardCollection collection);

    CardCollection addCardToCollection(Card card, CardCollection collection);

    CardCollection createNewCollection(CardCollection collection);
    CardCollection getCollectionById(int collectionId);
}
