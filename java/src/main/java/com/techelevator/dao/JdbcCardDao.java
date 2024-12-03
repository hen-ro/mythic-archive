package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Card;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;
//
//public class JdbcCardDao implements CardDao{
//    public List<Card> getCardsInCollection(int collectionId) {
//        List<Card> cardsInCollection = new ArrayList<>();
//        String sql = "SELECT card_id, card_name, image_url FROM cards" +
//                "JOIN cards_collections ON cards.card_id = cards_collections.card_id" +
//                "JOIN collections ON cards_collections.collection_id = collections.collection_id" +
//                "WHERE collections.collection.id = ?";
//        try {
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
//            while (results.next()) {
//                Card card = mapRowToCard(results);
//                cardCollections.add(cardCollection);
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        }
//        return cardsInCollection;
//    }
//}
