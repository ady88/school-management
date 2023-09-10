package com.adrian.school.management.service;

import com.adrian.school.management.domain.DocsData;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DocsData}.
 */
public interface DocsDataService {
    /**
     * Save a docsData.
     *
     * @param docsData the entity to save.
     * @return the persisted entity.
     */
    DocsData save(DocsData docsData);

    /**
     * Updates a docsData.
     *
     * @param docsData the entity to update.
     * @return the persisted entity.
     */
    DocsData update(DocsData docsData);

    /**
     * Partially updates a docsData.
     *
     * @param docsData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocsData> partialUpdate(DocsData docsData);

    /**
     * Get all the docsData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocsData> findAll(Pageable pageable);

    /**
     * Get the "id" docsData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocsData> findOne(Long id);

    /**
     * Delete the "id" docsData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
