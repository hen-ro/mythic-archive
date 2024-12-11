package com.techelevator.model;

import java.util.List;

public class CollectionStats {
    private List<CardTypeCount> cardTypeCounts;
    private List<ManaCostCount> manaCostCounts;
    private List<RarityCount> rarityCounts;
    private Double totalCollectionPrice;
    private List<SetNameCount> setNameCounts;

    private List<CardColorCount> cardColorCounts;

    public List<CardTypeCount> getCardTypeCounts() {
        return cardTypeCounts;
    }

    public void setCardTypeCounts(List<CardTypeCount> cardTypeCounts) {
        this.cardTypeCounts = cardTypeCounts;
    }

    public List<ManaCostCount> getManaCostCounts() {
        return manaCostCounts;
    }

    public void setManaCostCounts(List<ManaCostCount> manaCostCounts) {
        this.manaCostCounts = manaCostCounts;
    }

    public List<RarityCount> getRarityCounts() {
        return rarityCounts;
    }

    public void setRarityCounts(List<RarityCount> rarityCounts) {
        this.rarityCounts = rarityCounts;
    }

    public Double getTotalCollectionPrice() {
        return totalCollectionPrice;
    }

    public void setTotalCollectionPrice(Double totalCollectionPrice) {
        this.totalCollectionPrice = totalCollectionPrice;
    }

    public List<SetNameCount> getSetNameCounts() {
        return setNameCounts;
    }

    public void setSetNameCounts(List<SetNameCount> setNameCounts) {
        this.setNameCounts = setNameCounts;
    }

    public List<CardColorCount> getCardColorCounts() {
        return cardColorCounts;
    }

    public void setCardColorCounts(List<CardColorCount> cardColorCounts) {
        this.cardColorCounts = cardColorCounts;
    }
}
