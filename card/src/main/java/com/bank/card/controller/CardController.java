package com.bank.card.controller;


import com.bank.card.response.CardSchemeResponse;
import com.bank.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping(value = "/card-scheme/verify/{cardparam}")
    public ResponseEntity<CardSchemeResponse> getCards(@PathVariable("cardparam") String param) {
        return new ResponseEntity<>(cardService.verifyCard(param), HttpStatus.OK);
    }
}
