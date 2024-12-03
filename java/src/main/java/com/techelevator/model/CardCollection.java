package com.techelevator.model;

import java.util.List;

public class CardCollection {

    private int collectionId;

    private List<Card> cards;

    private boolean isPublic;

    public CardCollection() {

    }

    public CardCollection(int collectionId, List<Card> cards, boolean isPublic) {
        this.collectionId = collectionId;
        this.cards = cards;
        this.isPublic = isPublic;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
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
}
