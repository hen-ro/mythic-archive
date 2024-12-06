package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCollectionDao implements CollectionDao{
    private CardDao cardDao;
    private final JdbcTemplate jdbcTemplate;
    private final String COLLECTIONS_SELECT = "SELECT collection_id, collection_name, user_id, is_public, thumbnail_url FROM public.collections";

    public JdbcCollectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.cardDao = new JdbcCardDao(jdbcTemplate);
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
    @Override
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
    @Override
    public CardCollection createNewCollection(CardCollectionDto collection){
        CardCollection newCollection = null;
        String collectionSql = "INSERT INTO public.collections(collection_name, user_id, username) VALUES (?, ?, ?) RETURNING collection_id";
        try {
            int newCollectionId = jdbcTemplate.queryForObject(collectionSql, int.class, collection.getCollectionName(), collection.getOwnerId(), collection.getUsername());
            newCollection = getCollectionById(newCollectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCollection;
    };
    @Override
    public CardCollection addCardToCollection(Card card, int collectionId){
        CardCollection updatedCollection = null;
        String collectionSql = "INSERT INTO public.cards_collections(card_id, collection_id) VALUES (?, ?) RETURNING collection_id";
        try {
            int updatedCollectionId = jdbcTemplate.queryForObject(collectionSql, int.class, card.getCardId(), collectionId);
            updatedCollection = getCollectionById(updatedCollectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedCollection;
    };
    @Override
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
    @Override
    public int setCollectionPublic(int collectionId) {
        int numberOfRows = 0;
        String setPublicSql = "UPDATE public.collections" +
                " SET is_public= true" +
                " WHERE collection_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(setPublicSql, collectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    @Override
    public int setCollectionThumbnail(int collectionId, String thumbnail_url) {
        int numberOfRows = 0;
        String setThumbnailSql = "UPDATE public.collections" +
                                 " SET thumbnail_url = ?" +
                                 " WHERE collection_id = ?";
        try {
            numberOfRows = jdbcTemplate.update(setThumbnailSql, thumbnail_url, collectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private CardCollection mapRowToCollection(SqlRowSet rs) {
        CardCollection cardCollection = new CardCollection();
        cardCollection.setCollectionId(rs.getInt("collection_id"));
        cardCollection.setCollectionName(rs.getString("collection_name"));
        cardCollection.setOwnerId(rs.getInt("user_id"));
        cardCollection.setIsPublic(rs.getBoolean("is_public"));
        //Get the cards in the collection
        List<Card> cardsInCollection = cardDao.getCardsInCollection(cardCollection.getCollectionId());
        cardCollection.setCardCount(cardsInCollection.size());
        cardCollection.setUsername(rs.getString("username"));
        //Check if the thumbnail image has been set
        if (rs.getString("thumbnail_url").isEmpty()) {
            //If the collection has no thumbnail set, set to a default image
            cardCollection.setThumbnailUrl("/images/6398f26cb77dc209f3628aeb_Whopper.png");
        } else {
            cardCollection.setThumbnailUrl(rs.getString("thumbnail_url"));
        }
        //Set the list of cards in the collection
        cardCollection.setCards(cardsInCollection);
        return cardCollection;
    }
};
