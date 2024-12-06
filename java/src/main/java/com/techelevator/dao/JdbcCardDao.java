package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Card;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JdbcCardDao implements CardDao{
    private final String CARDS_SELECT = "SELECT cards.card_id, cards.card_name, cards.image_url, cards.thumbnail_url, cards_collections.quantity FROM public.cards";

    private final JdbcTemplate jdbcTemplate;
    public JdbcCardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private Card mapRowToCard(SqlRowSet rs){
        Card card = new Card();
        card.setCardId(rs.getObject("card_id", java.util.UUID.class));
        card.setCardName(rs.getString("card_name"));
        card.setImageUrl(rs.getString("image_url"));
        card.setThumbnailUrl(rs.getString("thumbnail_url"));
        return card;
    }
    @Override
    public Map<Card, Integer> getCardsInCollection(int collectionId) {
        Map<Card, Integer> cardsInCollection = new HashMap<>();
        String sql = CARDS_SELECT +
                     " JOIN cards_collections ON cards.card_id = cards_collections.card_id" +
                     " JOIN collections ON cards_collections.collection_id = collections.collection_id" +
                     " WHERE collections.collection_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            while (results.next()) {
               Card card = mapRowToCard(results);
               cardsInCollection.put(card, results.getInt("quantity"));
           }
        } catch (CannotGetJdbcConnectionException e) {
           throw new DaoException("Unable to connect to server or database", e);
        } return cardsInCollection;
    }

    @Override
    public Card getCardById(UUID cardId) {
        Card card = null;
        String sql = CARDS_SELECT +  " WHERE card_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cardId);
            if (results.next()) {
                card = mapRowToCard(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return card;
    }
    @Override
    public Card createNewCard(Card card) {
        Card newCard = null;
        String cardSql = "INSERT INTO public.cards(card_id, card_name, image_url, thumbnail_url)" +
                " VALUES (?, ?, ?, ?) RETURNING card_id";
        try {
            UUID newCardId = jdbcTemplate.queryForObject(cardSql, UUID.class, card.getCardId(), card.getCardName(), card.getImageUrl(), card.getThumbnailUrl());
            newCard = getCardById(newCardId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCard;
    }
}
