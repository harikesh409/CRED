package com.crio.cred.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * The type Add card statement dto.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
public class AddCardStatementDTO {
    @NotBlank(message = "Max Amount is mandatory.")
    private BigDecimal maxAmount;
    @NotBlank(message = "Min due Amount is mandatory.")
    private BigDecimal minDue;
    private BigDecimal totalDue = BigDecimal.ZERO;
    @Future(message = "Due date should be future date.")
    @NotBlank(message = "Due date is mandatory.")
    private OffsetDateTime dueDate;
    @NotBlank(message = "Card Id is mandatory.")
    private UUID cardId;
}
