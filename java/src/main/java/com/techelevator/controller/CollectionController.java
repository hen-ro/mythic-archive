package com.techelevator.controller;


import com.techelevator.dao.CardDao;
import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.JdbcCardDao;
import com.techelevator.dao.JdbcCollectionDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Card;
import com.techelevator.model.CardCollection;
import com.techelevator.model.CardCollectionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<CardCollection> addCardToCollection(@RequestBody Card card, int collectionId, int quantity) {
        try {
            CardCollection collection = collectionDao.getCollectionById(collectionId);
            Card cardToAdd = cardDao.getCardById(card.getCardId());
            if (cardToAdd == null) {
                //create card
                cardToAdd = cardDao.createNewCard(card);
            }
            //If card is already in collection, call function to update database
            if (collectionDao.isCardInCollection(card.getCardId(), collectionId)) {
                collectionDao.addExistingCardToCollection(cardToAdd, collection, quantity);
            } //else call function to insert new record
            else {
                collectionDao.addCardsToCollection(cardToAdd, collection, quantity);
            }

            return new ResponseEntity<>(collection, HttpStatus.OK);
        } catch (DaoException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/remove-all")
    public ResponseEntity<Integer> removeAllCardsOfTypeFromCollection(UUID cardId, int collectionId) {
        try {
            collectionDao.removeAllCardsOfTypeFromCollection(cardId, collectionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DaoException e) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Integer> removeCardsFromCollection(@RequestBody Card card, int collectionId, int quantity) {
        try {
            collectionDao.removeCardsFromCollection(card, collectionDao.getCollectionById(collectionId), quantity);
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





