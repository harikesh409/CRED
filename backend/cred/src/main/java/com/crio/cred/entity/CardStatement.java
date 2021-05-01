package com.crio.cred.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Card statement.
 *
 * @author harikesh.pallantla
 */
@Entity
@Getter
@Setter
@Table(name = "card_statement")
public class CardStatement extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID cardStatementId;

    @Column(nullable = false)
    private BigDecimal maxAmount;

    @Column(nullable = false)
    private BigDecimal minDue;

    @Column(nullable = false)
    private BigDecimal totalDue;

    @Column(nullable = false)
    private OffsetDateTime dueDate;

    @Column
    private OffsetDateTime settleDate;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardDetails cardId;

    @OneToMany(mappedBy = "cardStatementId")
    private List<Transactions> transactions;
}
