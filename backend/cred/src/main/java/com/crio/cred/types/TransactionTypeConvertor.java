package com.crio.cred.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * The type Transaction type convertor.
 *
 * @author harikesh.pallantla
 */
@Converter(autoApply = true)
public class TransactionTypeConvertor implements AttributeConverter<TransactionType, String> {
    /**
     * Converts the value stored in the entity transactionStatus into the
     * data representation to be stored in the database.
     *
     * @param transactionType the entity transactionStatus value to be converted
     * @return the converted data to be stored in the database
     * column
     */
    @Override
    public String convertToDatabaseColumn(TransactionType transactionType) {
        if (transactionType == null)
            return null;
        return transactionType.getCode();
    }

    /**
     * Converts the data stored in the database column into the
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct <code>code</code> type for the corresponding
     * column for use by the JDBC driver: i.e., persistence providers are
     * not expected to do such type conversion.
     *
     * @param code the data from the database column to be
     *             converted
     * @return the converted value to be stored in the entity
     * attribute
     */
    @Override
    public TransactionType convertToEntityAttribute(String code) {
        if (code == null)
            return null;
        return Stream.of(TransactionType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
