package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CardCollection;
import com.techelevator.model.User;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JdbcCollectionDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCollectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CardCollection> getAllPublicCollections() {
        List<CardCollection> collections = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash, role FROM users";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToCollection(results);
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }


    private User mapRowToCollection(SqlRowSet rs) {
        CardCollection collection = new CardCollection();
        collection.setCollectionId(rs.getInt("user_id"));
        collection.setCards(rs.getString);
        return collection;
    }
}
