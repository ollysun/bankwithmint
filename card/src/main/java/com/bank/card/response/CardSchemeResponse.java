package com.bank.card.response;

import com.bank.card.model.Card;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardSchemeResponse {

    private boolean status;
    @JsonProperty("payload")
    private Card card;

    public CardSchemeResponse(boolean status, Card card) {
        this.status = status;
        this.card = card;
    }
}
