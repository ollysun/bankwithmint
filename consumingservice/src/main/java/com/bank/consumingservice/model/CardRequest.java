package com.bank.consumingservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CardRequest implements Serializable {
    private String scheme;

    private String type;

    private String bank;
}
