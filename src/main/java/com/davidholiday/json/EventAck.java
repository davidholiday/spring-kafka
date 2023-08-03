package com.davidholiday.json;


import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventAck {

    public static final String PLAYER_ID_KEY = "player_id";
    public static final String GAME_ID_KEY = "game_id";
    public static final String RESPONSE_CODE_KEY = "response_code";

    @JsonProperty(PLAYER_ID_KEY)
    private String playerId;

    @JsonProperty(GAME_ID_KEY)
    private String gameId;

    @JsonProperty(RESPONSE_CODE_KEY)
    private HttpStatus responseCode;

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

    public HttpStatus getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(HttpStatus responseCode) {
        this.responseCode = responseCode;
    }

    //
    

}
