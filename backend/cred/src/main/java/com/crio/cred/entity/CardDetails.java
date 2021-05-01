package com.crio.cred.entity;

import com.crio.cred.types.CardPaymentService;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;
import java.util.UUID;

/**
 * The type Card details.
 *
 * @author harikesh.pallantla
 */
@Entity
@Table(name = "card_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"card_nick_name", "user_id"})
})
@Getter
@Setter
@SQLDelete(sql = "UPDATE card_details set is_active='f' where card_id=?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_active<>'f'")
public class CardDetails extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID cardId;

    @Column(name = "card_nick_name", nullable = false)
    private String cardNickName;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String nameOnCard;

    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    private Long cvv;

    @Column(nullable = false)
    private CardPaymentService cardPaymentService;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cardId", fetch = FetchType.LAZY)
    private List<CardStatement> cardStatementList;
}
