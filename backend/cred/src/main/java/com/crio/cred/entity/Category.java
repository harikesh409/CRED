package com.crio.cred.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Category.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String category;
}
