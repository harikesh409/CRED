package com.crio.cred.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * The type User.
 *
 * @author harikesh.pallantla
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@SQLDelete(sql = "UPDATE users set is_active='f' where user_id=?", check = ResultCheckStyle.COUNT)
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String emailId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private boolean isActive = true;
}
