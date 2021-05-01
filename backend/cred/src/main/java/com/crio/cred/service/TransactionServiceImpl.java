package com.crio.cred.service;

import com.crio.cred.dto.*;
import com.crio.cred.entity.CardStatement;
import com.crio.cred.entity.Category;
import com.crio.cred.entity.Transactions;
import com.crio.cred.entity.Vendor;
import com.crio.cred.exception.LimitExceededException;
import com.crio.cred.repository.TransactionsRepository;
import com.crio.cred.types.TransactionType;
import com.crio.cred.util.Utils;
import io.github.benas.randombeans.randomizers.range.LocalDateTimeRangeRandomizer;
import io.github.benas.randombeans.randomizers.time.ZoneOffsetRandomizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionServiceImpl implements TransactionService {

    private final TransactionsRepository transactionsRepository;
    private final ModelMapper modelMapper;
    private final CardStatementService cardStatementService;
    private final VendorService vendorService;
    private final CategoryService categoryService;
    private final EntityManager entityManager;

    /**
     * Add the transaction.
     *
     * @param addTransactionDTO the add transaction dto
     * @return the transaction dto
     */
    @Override
    @Transactional
    public TransactionDTO addTransaction(UUID cardId, AddTransactionDTO addTransactionDTO) throws LimitExceededException {
        logger.trace("Entered addTransaction");
        CardStatementDTO statementDTO =
                cardStatementService.getOutstandingStatement(cardId);
        CardStatement cardStatement = modelMapper.map(statementDTO, CardStatement.class);
        BigDecimal totalDue = statementDTO.getTotalDue();
        totalDue = totalDue.add(addTransactionDTO.getAmount());
        statementDTO.setTotalDue(totalDue);
        if (totalDue.compareTo(statementDTO.getMaxAmount()) > 0) {
            throw new LimitExceededException("Maximum limit of the credit card is exceeded.");
        }

        BigDecimal minDue = totalDue.divide(BigDecimal.TEN, RoundingMode.CEILING);
        statementDTO.setMinDue(minDue);
        cardStatementService.updateCardStatement(statementDTO);

        if (addTransactionDTO.getTransactionDate() == null) {
            LocalDateTimeRangeRandomizer localDateTimeRangeRandomizer = LocalDateTimeRangeRandomizer
                    .aNewLocalDateTimeRangeRandomizer(LocalDateTime.now().minusMonths(5), LocalDateTime.now());
            ZoneOffsetRandomizer zoneOffsetRandomizer = ZoneOffsetRandomizer.aNewZoneOffsetRandomizer();
            OffsetDateTime randomPast = OffsetDateTime.of(localDateTimeRangeRandomizer.getRandomValue(),
                    zoneOffsetRandomizer.getRandomValue());
            addTransactionDTO.setTransactionDate(randomPast);
        }

        Transactions transaction = modelMapper.map(addTransactionDTO, Transactions.class);
        transaction.setCardStatementId(cardStatement);

        try {
            Currency currency = Currency.getInstance(addTransactionDTO.getCurrency());
            transaction.setCurrency(currency);
        } catch (IllegalArgumentException | NullPointerException exception) {
            logger.error("Invalid currency code.");
            throw new IllegalArgumentException("Invalid Currency Code.");
        }

        logger.trace("Getting/ adding vendor");
        Vendor vendor = vendorService.getOrAddVendor(addTransactionDTO.getVendor().toLowerCase());
        transaction.setVendor(vendor);

        logger.trace("Getting/ adding category");
        Category category = categoryService.getOrAddCategory(addTransactionDTO.getCategory().toLowerCase());
        transaction.setCategory(category);

        Transactions savedTransaction = transactionsRepository.save(transaction);
        logger.trace("Exited addTransaction");
        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }

    /**
     * Add payment transaction.
     *
     * @param cardId                the card id
     * @param paymentTransactionDTO the payment transaction dto
     * @return the transaction dto
     */
    @Override
    @Transactional
    public TransactionDTO addPayment(UUID cardId, PaymentTransactionDTO paymentTransactionDTO) {
        logger.trace("Entered addPayment");
        CardStatementDTO statementDTO =
                cardStatementService.getOutstandingStatement(cardId);
        CardStatement cardStatement = modelMapper.map(statementDTO, CardStatement.class);
        BigDecimal paymentAmount = paymentTransactionDTO.getAmount();

        BigDecimal totalDue = statementDTO.getTotalDue();
        if (paymentAmount.compareTo(totalDue) > 0) {
            throw new IllegalArgumentException("Payment cannot be greater than outstanding due.");
        }

        BigDecimal minDue = statementDTO.getMinDue();
        if (minDue.compareTo(paymentAmount) > 0) {
            throw new IllegalArgumentException("The minimum due amount is: " + minDue);
        }

        totalDue = totalDue.subtract(paymentAmount);
        statementDTO.setTotalDue(totalDue);
        statementDTO.setSettleDate(OffsetDateTime.now());
        if (totalDue.compareTo(BigDecimal.ZERO) == 0) {
            minDue = BigDecimal.ZERO;
        } else {
            minDue = totalDue.divide(BigDecimal.TEN, RoundingMode.CEILING);
        }
        statementDTO.setMinDue(minDue);
        cardStatementService.updateCardStatement(statementDTO);

        Transactions transaction = modelMapper.map(paymentTransactionDTO, Transactions.class);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setTransactionDate(OffsetDateTime.now());
        transaction.setCardStatementId(cardStatement);
        Vendor vendor = vendorService.getOrAddVendor("CRED");
        transaction.setVendor(vendor);

        try {
            Currency currency = Currency.getInstance(paymentTransactionDTO.getCurrency());
            transaction.setCurrency(currency);
        } catch (IllegalArgumentException | NullPointerException exception) {
            logger.error("Invalid currency code.");
            throw new IllegalArgumentException("Invalid Currency Code.");
        }

        Transactions savedTransaction = transactionsRepository.save(transaction);
        TransactionDTO transactionDTO = modelMapper.map(savedTransaction, TransactionDTO.class);

        AddCardStatementDTO addCardStatementDTO = modelMapper.map(statementDTO, AddCardStatementDTO.class);
        OffsetDateTime newDueDate = addCardStatementDTO.getDueDate().plusMonths(1);
        addCardStatementDTO.setDueDate(newDueDate);
        cardStatementService.addCardStatement(addCardStatementDTO);

        logger.trace("Exited addPayment");
        return transactionDTO;
    }

    /**
     * Add transaction statement list.
     *
     * @param cardId       the card id
     * @param month        the month
     * @param year         the year
     * @param transactions the transactions
     * @return the list
     */
    @Override
    public List<TransactionDTO> addTransactionStatement(UUID cardId, int month, int year,
                                                        List<AddTransactionDTO> transactions) throws LimitExceededException {
        logger.trace("Entered addTransactionStatement");
        logger.debug("Transactions count: " + transactions.size());
        List<TransactionDTO> addedTransactions = new ArrayList<>();
        for (AddTransactionDTO transactionDTO : transactions) {
            if (transactionDTO.getTransactionDate() == null) {
                OffsetDateTime dateTime = OffsetDateTime.of(LocalDate.of(year, month, 1), LocalTime.now(ZoneOffset.UTC), ZoneOffset.UTC);
                transactionDTO.setTransactionDate(dateTime);
            }
            TransactionDTO addedTransaction = addTransaction(cardId, transactionDTO);
            addedTransactions.add(addedTransaction);
        }
        logger.trace("Exited addTransactionStatement");
        return addedTransactions;
    }

    /**
     * Gets transaction statement.
     *
     * @param cardId   the credit card id
     * @param month    the month
     * @param year     the year
     * @param pageable the pageable
     * @return the transaction statement
     */
    @Override
    public Page<TransactionDTO> getTransactionStatement(UUID cardId, int month, int year,
                                                        Pageable pageable) {
        List<CardStatementDTO> statements = cardStatementService.getCardStatementByCardId(cardId);
        List<CardStatement> cardStatements = Utils.mapList(modelMapper, statements, CardStatement.class);
        LocalDate localDate = LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.firstDayOfMonth());
        OffsetDateTime start = OffsetDateTime.of(localDate, LocalTime.MIN, ZoneOffset.UTC);
        localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        OffsetDateTime end = OffsetDateTime.of(localDate, LocalTime.MIN, ZoneOffset.UTC);
        Page<Transactions> all = transactionsRepository
                .findAllByCardStatementIdInAndTransactionDateBetween(cardStatements, start, end, pageable);
        return Utils.mapEntityPageIntoDtoPage(modelMapper, all, TransactionDTO.class);
    }

    /**
     * Gets smart statement by category.
     *
     * @param cardId the card id
     * @param month  the month
     * @param year   the year
     * @return the smart statement by category
     */
    @Override
    public List<CategoryStatementDTO> getSmartStatementByCategory(UUID cardId, int month, int year) {
        logger.trace("Entered getSmartStatementByCategory");
        LocalDate localDate = LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.firstDayOfMonth());
        OffsetDateTime start = OffsetDateTime.of(localDate, LocalTime.MIN, ZoneOffset.UTC);
        localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        OffsetDateTime end = OffsetDateTime.of(localDate, LocalTime.MIN, ZoneOffset.UTC);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoryStatementDTO> query = criteriaBuilder.createQuery(CategoryStatementDTO.class);
        Root<Transactions> root = query.from(Transactions.class);
        Expression<Number> sumExpression = criteriaBuilder.sum(root.get("amount"));
        query.multiselect(
                root.get("category").get("category"),
                sumExpression
        );
        query.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("cardStatementId").get("cardId").get("cardId"), cardId),
                        criteriaBuilder.between(root.get("transactionDate"), start, end),
                        criteriaBuilder.equal(root.get("transactionType"), TransactionType.DEBIT)
                )
        );
        query.groupBy(root.get("category").get("category"));
        query.orderBy(criteriaBuilder.desc(sumExpression));
        List<CategoryStatementDTO> resultList = entityManager.createQuery(query).getResultList();
        logger.trace("Exited getSmartStatementByCategory");
        return resultList;
    }

    /**
     * Gets smart statement by vendor.
     *
     * @param cardId the card id
     * @param month  the month
     * @param year   the year
     * @return the smart statement by vendor
     */
    @Override
    public List<VendorStatementDTO> getSmartStatementByVendor(UUID cardId, int month, int year) {
        logger.trace("Entered getSmartStatementByVendor");
        LocalDate localDate = LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.firstDayOfMonth());
        OffsetDateTime start = OffsetDateTime.of(localDate, LocalTime.MIN, ZoneOffset.UTC);
        localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        OffsetDateTime end = OffsetDateTime.of(localDate, LocalTime.MIN, ZoneOffset.UTC);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VendorStatementDTO> query = criteriaBuilder.createQuery(VendorStatementDTO.class);
        Root<Transactions> root = query.from(Transactions.class);
        Expression<Number> sumExpression = criteriaBuilder.sum(root.get("amount"));
        Expression<Long> countExpression = criteriaBuilder.count(root.get("vendor").get("vendor"));
        query.multiselect(
                root.get("vendor").get("vendor"),
                sumExpression,
                countExpression
        );
        query.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("cardStatementId").get("cardId").get("cardId"), cardId),
                        criteriaBuilder.between(root.get("transactionDate"), start, end),
                        criteriaBuilder.equal(root.get("transactionType"), TransactionType.DEBIT)
                )
        );
        query.groupBy(root.get("vendor").get("vendor"));
        query.orderBy(criteriaBuilder.desc(countExpression), criteriaBuilder.desc(sumExpression));
        List<VendorStatementDTO> resultList = entityManager.createQuery(query).getResultList();
        logger.trace("Exited getSmartStatementByVendor");
        return resultList;
    }
}
