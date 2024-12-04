package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Card;
import com.techelevator.model.CardCollection;
import com.techelevator.model.User;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class JdbcCollectionDao implements CollectionDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCollectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CardCollection> getAllPublicCollections() {
        List<CardCollection> cardCollections = new ArrayList<>();
        String sql = "";
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

    //    private addCardToCollection(Card card){
    //
    //    };
    //    private removeCardFromCollection(UUID cardId){
    //
    //    };
}
