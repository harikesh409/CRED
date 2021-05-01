package com.crio.cred.entity;

import com.crio.cred.types.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Currency;
import java.util.UUID;

/**
 * The type Transactions.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transactions extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID transactionId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private OffsetDateTime transactionDate;

    @OneToOne
    @JoinColumn(name = "category")
    private Category category;

    @OneToOne
    @JoinColumn(name = "vendor")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "card_statement_id")
    private CardStatement cardStatementId;
}
