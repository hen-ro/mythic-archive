package com.techelevator.controller;


import com.techelevator.dao.CardDao;
import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.JdbcCardDao;
import com.techelevator.dao.JdbcCollectionDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CardCollection;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
public class CollectionController {

    private final CardDao cardDao;

    private final CollectionDao collectionDao;

    public CollectionController(CardDao cardDao, CollectionDao collectionDao){
        this.cardDao = cardDao;
        this.collectionDao = collectionDao;
    }

    @RequestMapping(value = "/collections", method = RequestMethod.GET)
    public List<CardCollection> getAllPublicCollections() {
        return collectionDao.getAllPublicCollections();
    }
    @RequestMapping(value = "/collections/{id}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/collections", method = RequestMethod.POST)
    public CardCollection createNewCollection(@RequestBody CardCollection collection) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        collection.setOwnerId(userId);
        collection.setPublic(false);
        collection = collectionDao.createNewCollection(collection);
        return collection;
    }
}





