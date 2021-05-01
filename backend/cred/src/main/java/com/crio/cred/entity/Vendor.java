package com.crio.cred.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Vendor.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
@Entity
@Table(name = "vendor")
public class Vendor extends BaseEntity {
    @Id
    @GeneratedValue
    private Long vendorId;

    @Column(nullable = false, unique = true)
    private String vendor;
}
