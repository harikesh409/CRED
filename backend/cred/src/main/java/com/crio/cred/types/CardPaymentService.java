package com.crio.cred.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Card payment service.
 *
 * @author harikesh.pallantla
 */
@Getter
@AllArgsConstructor
public enum CardPaymentService {
    /**
     * American express card payment service.
     */
    AMERICAN_EXPRESS("AMEX"),
    /**
     * Visa card payment service.
     */
    VISA("VISA"),
    /**
     * Master card card payment service.
     */
    MASTER_CARD("MTC"),
    /**
     * Maestro card payment service.
     */
    MAESTRO("MAS"),
    /**
     * Discover card payment service.
     */
    DISCOVER("DIS"),
    /**
     * Rupay card payment service.
     */
    RUPAY("RUPAY");
    private final String code;
}
