package com.adrian.school.management.service;

import com.adrian.school.management.domain.NewsData;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NewsData}.
 */
public interface NewsDataService {
    /**
     * Save a newsData.
     *
     * @param newsData the entity to save.
     * @return the persisted entity.
     */
    NewsData save(NewsData newsData);

    /**
     * Updates a newsData.
     *
     * @param newsData the entity to update.
     * @return the persisted entity.
     */
    NewsData update(NewsData newsData);

    /**
     * Partially updates a newsData.
     *
     * @param newsData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NewsData> partialUpdate(NewsData newsData);

    /**
     * Get all the newsData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NewsData> findAll(Pageable pageable);

    /**
     * Get the "id" newsData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NewsData> findOne(Long id);

    /**
     * Delete the "id" newsData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
