package com.crio.cred.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Transaction type.
 *
 * @author harikesh.pallantla
 */
@Getter
@AllArgsConstructor
public enum TransactionType {
    /**
     * Debit transaction type.
     */
    DEBIT("debit"),
    /**
     * Credit transaction type.
     */
    CREDIT("credit");

    private final String code;
}
