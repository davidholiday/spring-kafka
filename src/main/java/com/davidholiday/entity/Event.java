package com.davidholiday.entity;


import jakarta.persistence.*;


@Entity
public class Event {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false)
    private String playerId;

    @Column(nullable=false)
    private String gameId;

    @Column(nullable=false)
    private String currencyCode;

    @Column(nullable=false)
    private double bet;

    @Column(nullable=false)
    private double payout;

    //

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    //

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    //

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    //

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    //

    public double getPayout() {
        return payout;
    }

    public void setPayout(double payout) {
        this.payout = payout;
    }

    //

    

}
