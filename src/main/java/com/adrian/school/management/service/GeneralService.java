package com.adrian.school.management.service;

import com.adrian.school.management.domain.General;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link General}.
 */
public interface GeneralService {
    /**
     * Save a general.
     *
     * @param general the entity to save.
     * @return the persisted entity.
     */
    General save(General general);

    /**
     * Updates a general.
     *
     * @param general the entity to update.
     * @return the persisted entity.
     */
    General update(General general);

    /**
     * Partially updates a general.
     *
     * @param general the entity to update partially.
     * @return the persisted entity.
     */
    Optional<General> partialUpdate(General general);

    /**
     * Get all the generals.
     *
     * @return the list of entities.
     */
    List<General> findAll();

    /**
     * Get the "id" general.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<General> findOne(Long id);

    /**
     * Delete the "id" general.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
