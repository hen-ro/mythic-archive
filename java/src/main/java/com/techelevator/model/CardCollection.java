package com.techelevator.model;

import java.util.List;

public class CardCollection {

    private int collectionId;
    private int ownerId;

    private List<Card> cards;

    private boolean isPublic;

    private String thumbnailUrl;

    private String collectionName;

    public CardCollection() {

    }

    public CardCollection(int collectionId, int ownerId, List<Card> cards, String collectionName, boolean isPublic) {
        this.collectionId = collectionId;
        this.ownerId = ownerId;
        this.cards = cards;
        this.collectionName = collectionName;
        this.isPublic = isPublic;
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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards =cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
