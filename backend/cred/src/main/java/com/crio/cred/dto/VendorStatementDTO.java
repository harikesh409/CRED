package com.crio.cred.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class VendorStatementDTO {
    private String vendor;
    private BigDecimal amount;
    private Long count;
}
