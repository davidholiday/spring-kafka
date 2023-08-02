package com.davidholiday.json;


import java.time.LocalDateTime;

import org.joda.money.Money;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    public static final String PLAYER_ID_KEY = "player_id";
    public static final String GAME_ID_KEY = "game_id";
    public static final String BET_KEY = "bet";
    public static final String PAYOUT_KEY = "payout";
    public static final String TIMESTAMP_KEY = "timestamp";

    @JsonProperty(PLAYER_ID_KEY)
    private String playerId;

    @JsonProperty(GAME_ID_KEY)
    private String gameId;

    @JsonProperty(BET_KEY)
    private Money bet;

    @JsonProperty(PAYOUT_KEY)
    private Money payout;

    @JsonProperty(TIMESTAMP_KEY)
    private LocalDateTime timestamp;

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

    @JsonProperty(BET_KEY)
    public Money getBet() {
        return bet;
    }

    @JsonProperty(BET_KEY)
    public void setBet(Money bet) {
        this.bet = bet;
    }

    //

    @JsonProperty(PAYOUT_KEY)
    public Money getPayout() {
        return payout;
    }

    @JsonProperty(PAYOUT_KEY)
    public void setPayout(Money payout) {
        this.payout = payout;
    }

    //

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    //

    

}

