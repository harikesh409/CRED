package com.crio.cred.service;

import com.crio.cred.dto.AddCardStatementDTO;
import com.crio.cred.dto.CardStatementDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Card statement service.
 *
 * @author harikesh.pallantla
 */
public interface CardStatementService {
    /**
     * Add card statement optional.
     *
     * @param addCardStatementDTO the add card statement dto
     * @return the optional
     */
    Optional<CardStatementDTO> addCardStatement(AddCardStatementDTO addCardStatementDTO);

    /**
     * Update card statement.
     *
     * @param cardStatementDTO the card statement dto
     * @return the card statement dto
     */
    CardStatementDTO updateCardStatement(CardStatementDTO cardStatementDTO);


    /**
     * Gets card statement by card id.
     *
     * @param cardId the card id
     * @return the card statement by card id
     */
    List<CardStatementDTO> getCardStatementByCardId(UUID cardId);

    /**
     * Gets outstanding statement.
     *
     * @param cardId the card id
     * @return the outstanding statement
     */
    CardStatementDTO getOutstandingStatement(UUID cardId);
}
