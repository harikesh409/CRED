package com.crio.cred.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentTransactionDTO {
    @NotNull(message = "Amount is mandatory.")
    private BigDecimal amount;

    private String currency = "INR";
}
