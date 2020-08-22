package com.bank.card.service;

import com.bank.card.exception.CardNotFoundException;
import com.bank.card.model.Card;
import com.bank.card.model.CardResponse;
import com.bank.card.repository.CardRepository;
import com.bank.card.request.CardRequest;
import com.bank.card.response.CardSchemeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class CardService {

    private final RestTemplate restTemplate = new RestTemplate();

    private CardRequest cardRequest = new CardRequest();

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ProducerService producerService;


    public CardSchemeResponse verifyCard(String param) {
        CardResponse getCard = null;
        CardSchemeResponse cardSchemeResponse = null;
        Card card = null;
        try {
            UriComponents builder = UriComponentsBuilder.fromUriString("https://lookup.binlist.net")
                    .path("/{cardno}").buildAndExpand(param);
            getCard = restTemplate.getForObject(builder.toString(), CardResponse.class);
            card = new Card(getCard.getScheme(), getCard.getType(),
                    getCard.getBank().getName());
            card = cardRepository.save(card);
            cardSchemeResponse = new CardSchemeResponse(true,card);
            publishToQueue(card);
        }catch (HttpClientErrorException ex){
            log.info("client error " + ex.getMessage());
            throw new CardNotFoundException("card number not found");
        }catch (Exception e){
            log.info("exception error " + e.getMessage());
            throw new CardNotFoundException("card number not found on exception");
        }
        return cardSchemeResponse;
    }

    private void publishToQueue(Card card) {
        BeanUtils.copyProperties(card, cardRequest);
        //publish the card to kafka queue
        producerService.produce(cardRequest);
    }
}
