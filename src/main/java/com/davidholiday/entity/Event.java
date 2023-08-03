package com.davidholiday.entity;

import jakarta.persistence.*;


@Entity
public class Event {
    
    public Event() {/* noop */ }

    public Event(com.davidholiday.json.Event jsonEvent) {
        this.playerId = jsonEvent.getPlayerId();
        this.gameId = jsonEvent.getGameId();
        this.currencyCode = jsonEvent.getCurrencyCode();
        this.bet = jsonEvent.getBet();
        this.payout = jsonEvent.getPayout();
        this.timestamp = jsonEvent.getTimestamp();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

    @Column(nullable=false)
    private String timestamp;

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    //

    
    

}
