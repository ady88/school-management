package com.adrian.school.management.web.rest;

import com.adrian.school.management.domain.MainNewsData;
import com.adrian.school.management.repository.MainNewsDataRepository;
import com.adrian.school.management.service.MainNewsDataService;
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
 * REST controller for managing {@link com.adrian.school.management.domain.MainNewsData}.
 */
@RestController
@RequestMapping("/api")
public class MainNewsDataResource {

    private final Logger log = LoggerFactory.getLogger(MainNewsDataResource.class);

    private static final String ENTITY_NAME = "mainNewsData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MainNewsDataService mainNewsDataService;

    private final MainNewsDataRepository mainNewsDataRepository;

    public MainNewsDataResource(MainNewsDataService mainNewsDataService, MainNewsDataRepository mainNewsDataRepository) {
        this.mainNewsDataService = mainNewsDataService;
        this.mainNewsDataRepository = mainNewsDataRepository;
    }

    /**
     * {@code POST  /main-news-data} : Create a new mainNewsData.
     *
     * @param mainNewsData the mainNewsData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mainNewsData, or with status {@code 400 (Bad Request)} if the mainNewsData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/main-news-data")
    public ResponseEntity<MainNewsData> createMainNewsData(@Valid @RequestBody MainNewsData mainNewsData) throws URISyntaxException {
        log.debug("REST request to save MainNewsData : {}", mainNewsData);
        if (mainNewsData.getId() != null) {
            throw new BadRequestAlertException("A new mainNewsData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MainNewsData result = mainNewsDataService.save(mainNewsData);
        return ResponseEntity
            .created(new URI("/api/main-news-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /main-news-data/:id} : Updates an existing mainNewsData.
     *
     * @param id the id of the mainNewsData to save.
     * @param mainNewsData the mainNewsData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mainNewsData,
     * or with status {@code 400 (Bad Request)} if the mainNewsData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mainNewsData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/main-news-data/{id}")
    public ResponseEntity<MainNewsData> updateMainNewsData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MainNewsData mainNewsData
    ) throws URISyntaxException {
        log.debug("REST request to update MainNewsData : {}, {}", id, mainNewsData);
        if (mainNewsData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mainNewsData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mainNewsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MainNewsData result = mainNewsDataService.update(mainNewsData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mainNewsData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /main-news-data/:id} : Partial updates given fields of an existing mainNewsData, field will ignore if it is null
     *
     * @param id the id of the mainNewsData to save.
     * @param mainNewsData the mainNewsData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mainNewsData,
     * or with status {@code 400 (Bad Request)} if the mainNewsData is not valid,
     * or with status {@code 404 (Not Found)} if the mainNewsData is not found,
     * or with status {@code 500 (Internal Server Error)} if the mainNewsData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/main-news-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MainNewsData> partialUpdateMainNewsData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MainNewsData mainNewsData
    ) throws URISyntaxException {
        log.debug("REST request to partial update MainNewsData partially : {}, {}", id, mainNewsData);
        if (mainNewsData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mainNewsData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mainNewsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MainNewsData> result = mainNewsDataService.partialUpdate(mainNewsData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mainNewsData.getId().toString())
        );
    }

    /**
     * {@code GET  /main-news-data} : get all the mainNewsData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mainNewsData in body.
     */
    @GetMapping("/main-news-data")
    public ResponseEntity<List<MainNewsData>> getAllMainNewsData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MainNewsData");
        Page<MainNewsData> page = mainNewsDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /main-news-data/:id} : get the "id" mainNewsData.
     *
     * @param id the id of the mainNewsData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mainNewsData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/main-news-data/{id}")
    public ResponseEntity<MainNewsData> getMainNewsData(@PathVariable Long id) {
        log.debug("REST request to get MainNewsData : {}", id);
        Optional<MainNewsData> mainNewsData = mainNewsDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mainNewsData);
    }

    /**
     * {@code DELETE  /main-news-data/:id} : delete the "id" mainNewsData.
     *
     * @param id the id of the mainNewsData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/main-news-data/{id}")
    public ResponseEntity<Void> deleteMainNewsData(@PathVariable Long id) {
        log.debug("REST request to delete MainNewsData : {}", id);
        mainNewsDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
