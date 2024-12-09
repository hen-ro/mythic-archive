package com.techelevator.controller;


import com.techelevator.dao.CardDao;
import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/collections")
@CrossOrigin
public class CollectionController {

    private final CardDao cardDao;
    private final UserDao userDao;
    private final CollectionDao collectionDao;

    public CollectionController(UserDao userDao, CollectionDao collectionDao, CardDao cardDao) {
        this.userDao = userDao;
        this.collectionDao = collectionDao;
        this.cardDao = cardDao;
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
    public ResponseEntity<CardCollection> addCardToCollection(@RequestBody AdjustCardRequestDto addCard, Principal principal) {
        try {
            User user = userDao.getUserByUsername(principal.getName());
            CardCollection collection = collectionDao.getCollectionByUserId(user.getId());
            Card cardToAdd = cardDao.getCardById(addCard.getCard().getCardId());
            //If SELECT statement returns null, add card to cards database
            if (cardToAdd == null) {
                cardToAdd = cardDao.createNewCard(addCard.getCard());
            }
            //If card is already in collection, call function to update database
            if (collectionDao.isCardInCollection(addCard.getCard().getCardId(), collection.getCollectionId())) {
                collectionDao.addExistingCardToCollection(cardToAdd, collection, addCard.getQuantity());
            } //else call function to insert new record
            else {
                collectionDao.addCardsToCollection(cardToAdd, collection, addCard.getQuantity());
            }
            collection = collectionDao.getCollectionByUserId(user.getId());
            return new ResponseEntity<>(collection, HttpStatus.OK);
        } catch (DaoException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/remove-all")
    public ResponseEntity<Integer> removeAllCardsOfTypeFromCollection(@RequestBody AdjustCardRequestDto removeAll, Principal principal) {
        try {
            User user = userDao.getUserByUsername(principal.getName());
            CardCollection collection = collectionDao.getCollectionByUserId(user.getId());
            collectionDao.removeAllCardsOfTypeFromCollection(removeAll.getCard().getCardId(), collection.getCollectionId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DaoException e) {
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Integer> removeCardsFromCollection(@RequestBody AdjustCardRequestDto removeCard, Principal principal) {
        try {
            User user = userDao.getUserByUsername(principal.getName());
            collectionDao.removeCardsFromCollection(removeCard.getCard(), collectionDao.getCollectionByUserId(user.getId()), removeCard.getQuantity());
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
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/by-user")
    public int getCollectionIdByUser(Principal principal) {
        try {
                User user = userDao.getUserByUsername(principal.getName());
                int collectionId = collectionDao.getCollectionByUserId(user.getId()).getCollectionId();
                return collectionId;
            } catch (DaoException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }






