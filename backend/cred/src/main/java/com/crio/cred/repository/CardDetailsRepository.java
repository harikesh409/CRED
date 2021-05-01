package com.crio.cred.repository;

import com.crio.cred.entity.CardDetails;
import com.crio.cred.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Card details repository.
 *
 * @author harikesh.pallantla
 */
@Repository
public interface CardDetailsRepository extends JpaRepository<CardDetails, UUID> {


    /**
     * Find by card number.
     *
     * @param cardNumber the card number
     * @return the card details if found.
     */
    Optional<CardDetails> findByCardNumber(String cardNumber);

    Page<CardDetails> findAllByUser(User user, Pageable pageable);
}
