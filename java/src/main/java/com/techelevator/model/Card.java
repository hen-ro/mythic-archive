package com.techelevator.model;

import java.util.UUID;

public class Card {

    private UUID cardId;

    private String cardName;

    private String imageUrl;

    public Card(UUID cardId, String cardName, String imageUrl) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.imageUrl = imageUrl;
    }

    public UUID getCardId() {
        return cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
