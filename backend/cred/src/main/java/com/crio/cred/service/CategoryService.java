package com.crio.cred.service;

import com.crio.cred.entity.Category;

import java.util.Optional;

/**
 * The interface Category service.
 *
 * @author harikesh.pallantla
 */
public interface CategoryService {
    /**
     * Add a category
     *
     * @param categoryName the category
     * @return the category
     */
    Category addCategory(String categoryName);

    /**
     * Gets or adds the category.
     *
     * @param categoryName the category name
     * @return the or add category
     */
    Category getOrAddCategory(String categoryName);

    /**
     * Gets category by name.
     *
     * @param categoryName the category
     * @return the category
     */
    Optional<Category> getCategoryByName(String categoryName);

    /**
     * Gets the category by id.
     *
     * @param categoryId the category id
     * @return the category by id
     */
    Optional<Category> getCategoryById(Long categoryId);
}
