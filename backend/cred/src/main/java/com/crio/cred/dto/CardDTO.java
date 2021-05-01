package com.crio.cred.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * The type Card dto.
 *
 * @author nithesh.tarani
 * @author harikesh.pallantla
 */
@Getter
@Setter
public class CardDTO {

    private UUID cardId;
    private String cardNickName;
    private String cardNumber;
    private String nameOnCard;
    private String expiryDate;
    private String cardPaymentService;
    private OffsetDateTime dueDate;
    private BigDecimal totalDue;
    private BigDecimal minDue;
}
