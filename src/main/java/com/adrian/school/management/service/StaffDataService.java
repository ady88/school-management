package com.adrian.school.management.service;

import com.adrian.school.management.domain.StaffData;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link StaffData}.
 */
public interface StaffDataService {
    /**
     * Save a staffData.
     *
     * @param staffData the entity to save.
     * @return the persisted entity.
     */
    StaffData save(StaffData staffData);

    /**
     * Updates a staffData.
     *
     * @param staffData the entity to update.
     * @return the persisted entity.
     */
    StaffData update(StaffData staffData);

    /**
     * Partially updates a staffData.
     *
     * @param staffData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StaffData> partialUpdate(StaffData staffData);

    /**
     * Get all the staffData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StaffData> findAll(Pageable pageable);

    /**
     * Get the "id" staffData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StaffData> findOne(Long id);

    /**
     * Delete the "id" staffData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
