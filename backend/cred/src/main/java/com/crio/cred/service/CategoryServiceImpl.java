package com.crio.cred.service;

import com.crio.cred.entity.Category;
import com.crio.cred.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Add a category
     *
     * @param categoryName the category
     * @return the category
     */
    @Override
    public Category addCategory(String categoryName) {
        logger.trace("Entered addCategory");
        Category category = new Category();
        category.setCategory(categoryName);
        Category savedCategory = categoryRepository.save(category);
        logger.debug("Saved category id: " + savedCategory.getCategoryId());
        logger.trace("Exited addCategory");
        return savedCategory;
    }

    /**
     * Gets or adds the category.
     *
     * @param categoryName the category name
     * @return the or add category
     */
    @Override
    public Category getOrAddCategory(String categoryName) {
        logger.trace("Entered getOrAddCategory");
        Optional<Category> getCategory = getCategoryByName(categoryName);
        if (getCategory.isPresent()) {
            logger.trace("Exited getOrAddCategory");
            return getCategory.get();
        }
        Category category = addCategory(categoryName);
        logger.trace("Exited getOrAddCategory");
        return category;
    }

    /**
     * Gets category by name.
     *
     * @param categoryName the category
     * @return the category
     */
    @Override
    public Optional<Category> getCategoryByName(String categoryName) {
        return categoryRepository.getCategoryByCategory(categoryName);
    }

    /**
     * Gets the category by id.
     *
     * @param categoryId the category id
     * @return the category by id
     */
    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
