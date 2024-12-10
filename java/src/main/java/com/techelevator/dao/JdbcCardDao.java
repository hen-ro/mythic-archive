package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Card;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class JdbcCardDao implements CardDao{
    private final String CARDS_SELECT = "SELECT cards.card_id, cards.card_name, cards.card_type, cards.mana_cost, cards.mana_value," +
                                        " cards.rarity, cards.price, cards.set_name, cards.image_url, cards.thumbnail_url" +
                                        " FROM public.cards";
    private final JdbcTemplate jdbcTemplate;
    public JdbcCardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private Card mapRowToCard(SqlRowSet rs){
        Card card = new Card();
        String cardIdString = rs.getString("card_id");
        UUID cardId = UUID.fromString(cardIdString);
        card.setCardId(cardId);
        card.setCardName(rs.getString("card_name"));
        card.setCardType(rs.getString("card_type"));
        card.setManaCost(rs.getString("mana_cost"));
        card.setManaValue(rs.getInt("mana_value"));
        card.setRarity(rs.getString("rarity"));
        card.setPrice(rs.getBigDecimal("price"));
        card.setSetName(rs.getString("set_name"));
        card.setImageUrl(rs.getString("image_url"));
        card.setThumbnailUrl(rs.getString("thumbnail_url"));
        return card;
    }
    @Override
    public Map<UUID, Integer> getCardMapForCollection(int collectionId) {
        Map<UUID, Integer> cardsInCollection = new HashMap<>();
        String sql = "SELECT cards.card_id, cards.card_name, cards.card_type, cards.mana_cost, cards.mana_value, " +
                     " cards.rarity, cards.price, cards.set_name, cards.image_url, cards.thumbnail_url, cards_collections.quantity FROM public.cards" +
                     " JOIN cards_collections ON cards.card_id = cards_collections.card_id" +
                     " JOIN collections ON cards_collections.collection_id = collections.collection_id" +
                     " WHERE collections.collection_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            while (results.next()) {
               Card card = mapRowToCard(results);
               cardsInCollection.put(card.getCardId(), results.getInt("quantity"));
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
        String cardSql = "INSERT INTO public.cards(card_id, card_name, card_type, mana_cost, mana_value," +
                " rarity, price, set_name, image_url, thumbnail_url)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING card_id";
        try {
            UUID newCardId = jdbcTemplate.queryForObject(cardSql, UUID.class, card.getCardId(), card.getCardName(), card.getCardType(), card.getManaCost(),
                                    card.getManaValue(), card.getRarity(), card.getPrice(), card.getSetName(), card.getImageUrl(), card.getThumbnailUrl());
            newCard = getCardById(newCardId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCard;
    }
}
