package com.crio.cred.dto;

import com.crio.cred.types.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
    private UUID transactionId;
    private BigDecimal amount;
    private String currency;
    private TransactionType transactionType;
    private OffsetDateTime transactionDate;
    private String category;
    private String vendor;
    private UUID cardStatementId;
}
