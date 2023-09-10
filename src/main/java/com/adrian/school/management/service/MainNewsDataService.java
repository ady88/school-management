package com.adrian.school.management.service;

import com.adrian.school.management.domain.MainNewsData;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MainNewsData}.
 */
public interface MainNewsDataService {
    /**
     * Save a mainNewsData.
     *
     * @param mainNewsData the entity to save.
     * @return the persisted entity.
     */
    MainNewsData save(MainNewsData mainNewsData);

    /**
     * Updates a mainNewsData.
     *
     * @param mainNewsData the entity to update.
     * @return the persisted entity.
     */
    MainNewsData update(MainNewsData mainNewsData);

    /**
     * Partially updates a mainNewsData.
     *
     * @param mainNewsData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MainNewsData> partialUpdate(MainNewsData mainNewsData);

    /**
     * Get all the mainNewsData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MainNewsData> findAll(Pageable pageable);

    /**
     * Get the "id" mainNewsData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MainNewsData> findOne(Long id);

    /**
     * Delete the "id" mainNewsData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
