package com.crio.cred.repository;

import com.crio.cred.entity.CardDetails;
import com.crio.cred.entity.CardStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The interface Card statement repository.
 *
 * @author harikesh.pallantla
 */
@Repository
public interface CardStatementRepository extends JpaRepository<CardStatement, UUID> {

    /**
     * Find card statement of a card which is not settled.
     *
     * @param cardId the card id
     * @return the card statement
     */
    CardStatement findCardStatementBySettleDateIsNullAndCardId(CardDetails cardId);

    /**
     * Find all by card statements id.
     *
     * @param cardId the card id
     * @return the list
     */
    List<CardStatement> findAllByCardId(CardDetails cardId);
}
