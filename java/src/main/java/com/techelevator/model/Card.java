package com.techelevator.model;

import java.util.UUID;

public class Card {

    private UUID cardId;

    private String cardName;
    private String thumbnailUrl;

    private String imageUrl;

    public Card(){};

    public Card(UUID cardId, String cardName, String imageUrl, String thumbnailUrl) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
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

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
