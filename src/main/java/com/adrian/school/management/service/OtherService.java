package com.adrian.school.management.service;

import com.adrian.school.management.domain.Other;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Other}.
 */
public interface OtherService {
    /**
     * Save a other.
     *
     * @param other the entity to save.
     * @return the persisted entity.
     */
    Other save(Other other);

    /**
     * Updates a other.
     *
     * @param other the entity to update.
     * @return the persisted entity.
     */
    Other update(Other other);

    /**
     * Partially updates a other.
     *
     * @param other the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Other> partialUpdate(Other other);

    /**
     * Get all the others.
     *
     * @return the list of entities.
     */
    List<Other> findAll();

    /**
     * Get the "id" other.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Other> findOne(Long id);

    /**
     * Delete the "id" other.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
