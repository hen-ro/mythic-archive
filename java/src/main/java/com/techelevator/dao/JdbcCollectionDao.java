package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Card;
import com.techelevator.model.CardCollection;
import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class JdbcCollectionDao implements CollectionDao{

    private final JdbcTemplate jdbcTemplate;
    private final String COLLECTIONS_SELECT = "SELECT collection_id, collection_name, user_id, is_public, thumbnail_url FROM public.collections";

    public JdbcCollectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CardCollection> getAllPublicCollections() {
        List<CardCollection> cardCollections = new ArrayList<>();
        String sql = COLLECTIONS_SELECT + " WHERE is_public";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                CardCollection cardCollection = mapRowToCollection(results);
                cardCollections.add(cardCollection);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return cardCollections;
    }


    private CardCollection mapRowToCollection(SqlRowSet rs) {
        JdbcCardDao card = new JdbcCardDao(jdbcTemplate);
        CardCollection cardCollection = new CardCollection();
        cardCollection.setCollectionId(rs.getInt("collection_id"));
        cardCollection.setCollectionName(rs.getString("collection_name"));
        List<Card> myCollection = card.getCardsInCollection(cardCollection.getCollectionId());
        if (rs.getString("thumbnail_url").isEmpty()) {
            if (myCollection.size() >= 1) {
                cardCollection.setThumbnailUrl(myCollection.getFirst().getImageUrl());
            } else {
                cardCollection.setThumbnailUrl("");
            }
        } else {
            cardCollection.setThumbnailUrl(rs.getString("thumbnail_url"));
        }
        return cardCollection;
    }
    public CardCollection getCollectionById(int collectionId) {
        CardCollection collection = null;
        String sql = COLLECTIONS_SELECT +  " WHERE collection_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            if (results.next()) {
                collection = mapRowToCollection(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return collection;
    }
    public CardCollection createNewCollection(CardCollection collection){
        CardCollection newCollection = null;
        String collectionSql = "INSERT INTO public.collections(collection_name, user_id, is_public, thumbnail_url) VALUES (?, ?, ?, ?)";
        try {
            int newCollectionId = jdbcTemplate.queryForObject(collectionSql, int.class, collection.getCollectionName());
            newCollection = getCollectionById(newCollectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCollection;
    };
    public CardCollection addCardToCollection(Card card, CardCollection collection){
        CardCollection newCollection = null;
        String collectionSql = "INSERT INTO public.cards_collections(card_id, collection_id) VALUES (?, ?) RETURNING collection_id";
        try {
            int newCollectionId = jdbcTemplate.queryForObject(collectionSql, int.class, card.getCardId(), collection.getCollectionId());
            newCollection = getCollectionById(newCollectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCollection;
    };
    public int removeCardFromCollection(Card card, CardCollection collection){
        int numberOfRows = 0;
        String removeCollectionSql = "DELETE FROM public.cards_collections WHERE card_id = ? AND collection_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(removeCollectionSql, card.getCardId(), collection.getCollectionId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    };
};
