package com.crio.cred.repository;

import com.crio.cred.entity.CardStatement;
import com.crio.cred.entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The interface Transactions repository.
 *
 * @author harikesh.pallantla
 */
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {
    Page<Transactions> findAllByCardStatementIdAndTransactionDateBetween(CardStatement cardStatementId, OffsetDateTime transactionDate, OffsetDateTime transactionDate2, Pageable pageable);

    Page<Transactions> findAllByCardStatementIdInAndTransactionDateBetween(List<CardStatement> cardStatements, OffsetDateTime transactionDate, OffsetDateTime transactionDate2, Pageable pageable);
}
