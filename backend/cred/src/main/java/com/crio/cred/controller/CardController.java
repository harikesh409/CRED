package com.crio.cred.controller;

import com.crio.cred.annotation.ApiPageable;
import com.crio.cred.configuration.SpringFoxConfig;
import com.crio.cred.dto.AddCardDTO;
import com.crio.cred.dto.CardDTO;
import com.crio.cred.model.ErrorDetails;
import com.crio.cred.service.CardDetailsService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

/**
 * The type Card controller.
 *
 * @author nitesh.tarani
 * @author harikesh.pallantla
 */
@RestController
@Validated
@Slf4j
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = SpringFoxConfig.CARD_TAG)
@SwaggerDefinition(
        info = @Info(description = "Card Operations", title = "Card Controller", version = "1.0")
)
public class CardController {
    private final CardDetailsService cardDetailsService;

    /**
     * Add a new card.
     *
     * @param cardDTO the details of the card
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED,
                    message = "Card added successfully.", response = CardDTO.class),
            @ApiResponse(code = HttpServletResponse.SC_CONFLICT,
                    message = "Card already added", response = ErrorDetails.class)
    })
    @ApiOperation(value = "Adds a new card with the given details.",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, authorizations = {@Authorization("JWT")})
    @PostMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCard(@Valid @RequestBody AddCardDTO cardDTO) {
        logger.trace("Entered addCard");
        if (cardDetailsService.isCardPresent(cardDTO.getCardNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorDetails(HttpStatus.CONFLICT, "Card already exists."));
        Optional<CardDTO> cardResponseDTO = cardDetailsService.addCard(cardDTO);
        if (cardResponseDTO.isPresent()) {
            CardDTO card = cardResponseDTO.get();
            logger.trace("Exited addCard");
            return ResponseEntity.created(URI.create("/cards/" + card.getCardId())).body(card);
        }
        logger.trace("Exited addCard");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Gets the list of all cards of the logged in user.
     *
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Cards returned successfully.",
                    response = CardDTO.class)
    })
    @ApiOperation(value = "Gets the list of all cards of the logged in user.", produces = MediaType.APPLICATION_JSON_VALUE,
            authorizations = {@Authorization("JWT")})
    @GetMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiPageable
    public ResponseEntity<?> getAllCardsByCurrentUser(@PageableDefault @ApiIgnore Pageable pageable) {
        logger.trace("Entered getAllCardsByCurrentUser");
        Page<CardDTO> cards = cardDetailsService.getAllCardsByCurrentUser(pageable);
        logger.trace("Exited getAllCardsByCurrentUser");
        return ResponseEntity.ok(cards);
    }

    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Card details returned successfully.", response = CardDTO.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Card Id not found.")
    })
    @ApiOperation(value = "Gets the card by card id.", produces = MediaType.APPLICATION_JSON_VALUE,
            authorizations = {@Authorization("JWT")})
    @GetMapping(value = "/cards/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCardById(@PathVariable
                                         @ApiParam(value = "credit card id", readOnly = true)
                                                 UUID cardId) {
        logger.trace("Entered getCardById");
        Optional<CardDTO> cardByNumber = cardDetailsService.getCardById(cardId);
        if (cardByNumber.isPresent()) {
            logger.trace("Exited getCardById");
            return ResponseEntity.ok(cardByNumber.get());
        }
        logger.trace("Exited getCardById");
        return ResponseEntity.noContent().build();
    }
}
