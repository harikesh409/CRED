package com.crio.cred.dto;

import com.crio.cred.entity.Transactions;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Card statement dto.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class CardStatementDTO {
    private UUID cardStatementId;
    private BigDecimal minDue;
    private BigDecimal totalDue;
    private BigDecimal maxAmount;
    private OffsetDateTime dueDate;
    private OffsetDateTime settleDate;
    private UUID cardId;
    private List<Transactions> transactions;
}
