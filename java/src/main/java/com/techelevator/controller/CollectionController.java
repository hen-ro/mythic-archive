package com.techelevator.controller;


import com.techelevator.dao.CardDao;
import com.techelevator.dao.CollectionDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.AdjustCardRequestDto;
import com.techelevator.model.Card;
import com.techelevator.model.CardCollection;
import com.techelevator.model.CardCollectionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/collections")
@CrossOrigin
public class CollectionController {

    private final CardDao cardDao;

    private final CollectionDao collectionDao;

    public CollectionController(CardDao cardDao, CollectionDao collectionDao){
        this.cardDao = cardDao;
        this.collectionDao = collectionDao;
    }

    @RequestMapping(value = "/all-public", method = RequestMethod.GET)
    public List<CardCollection> getAllPublicCollections() {
        return collectionDao.getAllPublicCollections();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CardCollection getCollectionById(@PathVariable int id) {
        try {
            CardCollection collection;
            collection =  collectionDao.getCollectionById(id);
            if (collection == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find specified collection.");
            }
            return collection;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}/cards")
    public List<Card> getCardsInCollection(@PathVariable int id) {
        try {
            List<Card> cards = collectionDao.getCardsInCollection(id);
            return cards;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<CardCollection> createNewCollection(@RequestBody CardCollectionDto collection) {
        try {
            CardCollection createdCollection = collectionDao.createNewCollection(collection);

            return new ResponseEntity<>(createdCollection, HttpStatus.CREATED);
        } catch (DaoException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/add")
    public ResponseEntity<CardCollection> addCardToCollection(@RequestBody AdjustCardRequestDto addCard) {
        try {
            CardCollection collection = collectionDao.getCollectionById(addCard.getCollectionId());
            Card cardToAdd = cardDao.getCardById(addCard.getCard().getCardId());
            //If SELECT statement returns null, add card to cards database
            if (cardToAdd == null) {
                cardToAdd = cardDao.createNewCard(addCard.getCard());
            }
            //If card is already in collection, call function to update database
            if (collectionDao.isCardInCollection(addCard.getCard().getCardId(), addCard.getCollectionId())) {
                collectionDao.addExistingCardToCollection(cardToAdd, collection, addCard.getQuantity());
            } //else call function to insert new record
            else {
                collectionDao.addCardsToCollection(cardToAdd, collection, addCard.getQuantity());
            }

            return new ResponseEntity<>(collection, HttpStatus.OK);
        } catch (DaoException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/remove-all")
    public ResponseEntity<Integer> removeAllCardsOfTypeFromCollection(@RequestBody AdjustCardRequestDto removeAll) {
        try {
            collectionDao.removeAllCardsOfTypeFromCollection(removeAll.getCard().getCardId(), removeAll.getCollectionId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DaoException e) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Integer> removeCardsFromCollection(@RequestBody AdjustCardRequestDto removeCard) {
        try {
            collectionDao.removeCardsFromCollection(removeCard.getCard(), collectionDao.getCollectionById(removeCard.getCollectionId()), removeCard.getQuantity());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DaoException e) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/set-public")
    public ResponseEntity<Integer> setCollectionPublic(@PathVariable int id) {
        try {
            int numberOfRows = collectionDao.setCollectionPublic(id);
            return new ResponseEntity<>(numberOfRows, HttpStatus.OK);
        } catch (DaoException e) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/set-thumbnail")
    public ResponseEntity<Integer> setCollectionThumbnail(@PathVariable int id, String thumbnail_url) {
        try {
            int numberOfRows = collectionDao.setCollectionThumbnail(id, thumbnail_url);
            return new ResponseEntity<>(numberOfRows, HttpStatus.OK);
        } catch (DaoException e) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}





