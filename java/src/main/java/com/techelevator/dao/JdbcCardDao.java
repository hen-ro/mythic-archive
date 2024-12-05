package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Card;
import com.techelevator.model.CardCollection;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCardDao implements CardDao{

    private final JdbcTemplate jdbcTemplate;
    public JdbcCardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private Card mapRowToCard(SqlRowSet rs){
        Card card = new Card();
        card.setCardId(rs.getObject("card_id", java.util.UUID.class));
        card.setCardName(rs.getString("card_name"));
        card.setImageUrl(rs.getString("image_url"));

        return card;
    }
    public List<Card> getCardsInCollection(int collectionId) {
        List<Card> cardsInCollection = new ArrayList<>();
        String sql = "SELECT card_id, card_name, image_url FROM cards" +
                "JOIN cards_collections ON cards.card_id = cards_collections.card_id" +
               "JOIN collections ON cards_collections.collection_id = collections.collection_id" +
                "WHERE collections.collection.id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
               Card card = mapRowToCard(results);
               cardsInCollection.add(card);
           }
        } catch (CannotGetJdbcConnectionException e) {
           throw new DaoException("Unable to connect to server or database", e);
        } return cardsInCollection;
    }
}
