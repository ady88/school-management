package com.adrian.school.management.web.rest;

import com.adrian.school.management.domain.ShortNewsData;
import com.adrian.school.management.repository.ShortNewsDataRepository;
import com.adrian.school.management.service.ShortNewsDataService;
import com.adrian.school.management.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.adrian.school.management.domain.ShortNewsData}.
 */
@RestController
@RequestMapping("/api")
public class ShortNewsDataResource {

    private final Logger log = LoggerFactory.getLogger(ShortNewsDataResource.class);

    private static final String ENTITY_NAME = "shortNewsData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShortNewsDataService shortNewsDataService;

    private final ShortNewsDataRepository shortNewsDataRepository;

    public ShortNewsDataResource(ShortNewsDataService shortNewsDataService, ShortNewsDataRepository shortNewsDataRepository) {
        this.shortNewsDataService = shortNewsDataService;
        this.shortNewsDataRepository = shortNewsDataRepository;
    }

    /**
     * {@code POST  /short-news-data} : Create a new shortNewsData.
     *
     * @param shortNewsData the shortNewsData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shortNewsData, or with status {@code 400 (Bad Request)} if the shortNewsData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/short-news-data")
    public ResponseEntity<ShortNewsData> createShortNewsData(@Valid @RequestBody ShortNewsData shortNewsData) throws URISyntaxException {
        log.debug("REST request to save ShortNewsData : {}", shortNewsData);
        if (shortNewsData.getId() != null) {
            throw new BadRequestAlertException("A new shortNewsData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShortNewsData result = shortNewsDataService.save(shortNewsData);
        return ResponseEntity
            .created(new URI("/api/short-news-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /short-news-data/:id} : Updates an existing shortNewsData.
     *
     * @param id the id of the shortNewsData to save.
     * @param shortNewsData the shortNewsData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shortNewsData,
     * or with status {@code 400 (Bad Request)} if the shortNewsData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shortNewsData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/short-news-data/{id}")
    public ResponseEntity<ShortNewsData> updateShortNewsData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShortNewsData shortNewsData
    ) throws URISyntaxException {
        log.debug("REST request to update ShortNewsData : {}, {}", id, shortNewsData);
        if (shortNewsData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shortNewsData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shortNewsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShortNewsData result = shortNewsDataService.update(shortNewsData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shortNewsData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /short-news-data/:id} : Partial updates given fields of an existing shortNewsData, field will ignore if it is null
     *
     * @param id the id of the shortNewsData to save.
     * @param shortNewsData the shortNewsData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shortNewsData,
     * or with status {@code 400 (Bad Request)} if the shortNewsData is not valid,
     * or with status {@code 404 (Not Found)} if the shortNewsData is not found,
     * or with status {@code 500 (Internal Server Error)} if the shortNewsData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/short-news-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShortNewsData> partialUpdateShortNewsData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShortNewsData shortNewsData
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShortNewsData partially : {}, {}", id, shortNewsData);
        if (shortNewsData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shortNewsData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shortNewsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShortNewsData> result = shortNewsDataService.partialUpdate(shortNewsData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shortNewsData.getId().toString())
        );
    }

    /**
     * {@code GET  /short-news-data} : get all the shortNewsData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shortNewsData in body.
     */
    @GetMapping("/short-news-data")
    public ResponseEntity<List<ShortNewsData>> getAllShortNewsData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ShortNewsData");
        Page<ShortNewsData> page = shortNewsDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /short-news-data/:id} : get the "id" shortNewsData.
     *
     * @param id the id of the shortNewsData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shortNewsData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/short-news-data/{id}")
    public ResponseEntity<ShortNewsData> getShortNewsData(@PathVariable Long id) {
        log.debug("REST request to get ShortNewsData : {}", id);
        Optional<ShortNewsData> shortNewsData = shortNewsDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shortNewsData);
    }

    /**
     * {@code DELETE  /short-news-data/:id} : delete the "id" shortNewsData.
     *
     * @param id the id of the shortNewsData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/short-news-data/{id}")
    public ResponseEntity<Void> deleteShortNewsData(@PathVariable Long id) {
        log.debug("REST request to delete ShortNewsData : {}", id);
        shortNewsDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
