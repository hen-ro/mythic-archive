package com.techelevator.model;

public class AdjustCardRequestDto {
    private Card card;
    private int quantity;

    public AdjustCardRequestDto() {
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
