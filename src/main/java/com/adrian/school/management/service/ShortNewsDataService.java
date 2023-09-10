package com.adrian.school.management.service;

import com.adrian.school.management.domain.ShortNewsData;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ShortNewsData}.
 */
public interface ShortNewsDataService {
    /**
     * Save a shortNewsData.
     *
     * @param shortNewsData the entity to save.
     * @return the persisted entity.
     */
    ShortNewsData save(ShortNewsData shortNewsData);

    /**
     * Updates a shortNewsData.
     *
     * @param shortNewsData the entity to update.
     * @return the persisted entity.
     */
    ShortNewsData update(ShortNewsData shortNewsData);

    /**
     * Partially updates a shortNewsData.
     *
     * @param shortNewsData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShortNewsData> partialUpdate(ShortNewsData shortNewsData);

    /**
     * Get all the shortNewsData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShortNewsData> findAll(Pageable pageable);

    /**
     * Get the "id" shortNewsData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShortNewsData> findOne(Long id);

    /**
     * Delete the "id" shortNewsData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
