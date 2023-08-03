package com.davidholiday.json;


import java.time.LocalDateTime;

import org.joda.money.Money;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    public static final String PLAYER_ID_KEY = "player_id";
    public static final String GAME_ID_KEY = "game_id";
    public static final String CURRENCY_CODE_KEY = "currency_code";
    public static final String BET_KEY = "bet";
    public static final String PAYOUT_KEY = "payout";
    public static final String TIMESTAMP_KEY = "timestamp";

    @JsonProperty(PLAYER_ID_KEY)
    private String playerId;

    @JsonProperty(GAME_ID_KEY)
    private String gameId;

    @JsonProperty(CURRENCY_CODE_KEY)
    private String currencyCode;

    @JsonProperty(BET_KEY)
    private double bet;

    @JsonProperty(PAYOUT_KEY)
    private double payout;

    @JsonProperty(TIMESTAMP_KEY)
    private String timestamp;

    //

    @JsonProperty(PLAYER_ID_KEY)
    public String getPlayerId() {
        return playerId;
    }

    @JsonProperty(PLAYER_ID_KEY)
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    //

    @JsonProperty(GAME_ID_KEY)
    public String getGameId() {
        return gameId;
    }

    @JsonProperty(GAME_ID_KEY)
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    //

    @JsonProperty(CURRENCY_CODE_KEY)
    public String getCurrencyCode() {
        return currencyCode;
    }

    @JsonProperty(CURRENCY_CODE_KEY) 
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    //

    @JsonProperty(BET_KEY)
    public double getBet() {
        return bet;
    }

    @JsonProperty(BET_KEY)
    public void setBet(double bet) {
        this.bet = bet;
    }

    //

    @JsonProperty(PAYOUT_KEY)
    public double getPayout() {
        return payout;
    }

    @JsonProperty(PAYOUT_KEY)
    public void setPayout(double payout) {
        this.payout = payout;
    }

    //

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    //

    

}

