package com.crio.cred.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CategoryStatementDTO {
    private String category;
    private BigDecimal amount;
}
