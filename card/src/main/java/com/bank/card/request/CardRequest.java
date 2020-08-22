package com.bank.card.request;

import lombok.Data;

@Data
public class CardRequest {
    private String scheme;

    private String type;

    private String bank;
}
