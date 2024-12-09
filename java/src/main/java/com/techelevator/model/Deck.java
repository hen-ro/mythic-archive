package com.techelevator.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Deck {

    private String deckName;

    private String deckFormat;
    private Map<UUID, Integer> cards;

    private int deckId;
    private int ownerId;

    private boolean isPublic;
    private String thumbnailUrl;
    private int totalCards;

    public Deck(int collectionId, int ownerId, Map<UUID, Integer> cards, String collectionName, boolean isPublic) {
        this.deckId = collectionId;
        this.ownerId = ownerId;
        this.cards = cards;
        this.deckName = deckName;
        this.isPublic = isPublic;
        this.deckFormat = deckFormat;
    }
}
