package com.crio.cred.repository;

import com.crio.cred.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Category repository.
 *
 * @author harikesh.pallantla
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> getCategoryByCategory(String category);
}
