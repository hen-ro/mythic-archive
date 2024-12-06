package com.techelevator.model;

import java.util.Map;

public class CardCollection {

    private int collectionId;
    private int ownerId;

    private Map<Card, Integer> cards;

    private boolean isPublic;

    private String thumbnailUrl;

    private String collectionName;

    private String username;

    private int totalCards;

    public CardCollection() {

    }

    public CardCollection(int collectionId, int ownerId, Map<Card, Integer> cards, String collectionName, boolean isPublic) {
        this.collectionId = collectionId;
        this.ownerId = ownerId;
        this.cards = cards;
        this.collectionName = collectionName;
        this.isPublic = isPublic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(Integer totalCards) {
        this.totalCards = totalCards;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getCollectionName() {
        return collectionName;
    }
    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Map<Card, Integer> getCards() {
        return cards;
    }

    public void setCards(Map<Card, Integer> cards) {
        this.cards = cards;
    }

    public Integer getCardCount(Card card) {
        return cards.get(card);
    }

    public void setCardCount(Card card, int quantity) {
        this.cards.put(card, quantity);
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
