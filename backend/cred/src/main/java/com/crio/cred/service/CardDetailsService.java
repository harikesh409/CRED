package com.crio.cred.service;

import com.crio.cred.dto.AddCardDTO;
import com.crio.cred.dto.CardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Card details service.
 *
 * @author harikesh.pallantla
 */
public interface CardDetailsService {
    /**
     * Add card.
     *
     * @param addCardDTO the add card dto
     * @return the card details if added successfully.
     */
    Optional<CardDTO> addCard(AddCardDTO addCardDTO);

    /**
     * Checks if the card exists with the given card number or not.
     *
     * @param cardNumber the card number
     * @return the boolean
     */
    Boolean isCardPresent(String cardNumber);

    /**
     * Checks if card is present or not with the given card id.
     *
     * @param cardId the card id
     * @return the boolean
     */
    Boolean isCardPresent(UUID cardId);


    /**
     * Gets all cards of the logged in user.
     *
     * @return the all cards of the current user
     */
    Page<CardDTO> getAllCardsByCurrentUser(Pageable pageable);

    /**
     * Gets card by id.
     *
     * @param cardId the card id
     * @return the card by id
     */
    Optional<CardDTO> getCardById(UUID cardId);
}