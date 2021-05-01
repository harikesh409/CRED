package com.crio.cred.repository;

import com.crio.cred.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface User repository.
 *
 * @author harikesh.pallantla
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Find by email id.
     *
     * @param emailId the email id
     * @return the user details if found
     */
    Optional<User> findByEmailId(String emailId);

    /**
     * Find by email id and password optional.
     *
     * @param emailId  the email id
     * @param password the password
     * @return the user details if found
     */
    Optional<User> findByEmailIdAndPassword(String emailId, String password);
}
