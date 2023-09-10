package com.adrian.school.management.web.rest;

import com.adrian.school.management.domain.DocsData;
import com.adrian.school.management.repository.DocsDataRepository;
import com.adrian.school.management.service.DocsDataService;
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
 * REST controller for managing {@link com.adrian.school.management.domain.DocsData}.
 */
@RestController
@RequestMapping("/api")
public class DocsDataResource {

    private final Logger log = LoggerFactory.getLogger(DocsDataResource.class);

    private static final String ENTITY_NAME = "docsData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocsDataService docsDataService;

    private final DocsDataRepository docsDataRepository;

    public DocsDataResource(DocsDataService docsDataService, DocsDataRepository docsDataRepository) {
        this.docsDataService = docsDataService;
        this.docsDataRepository = docsDataRepository;
    }

    /**
     * {@code POST  /docs-data} : Create a new docsData.
     *
     * @param docsData the docsData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docsData, or with status {@code 400 (Bad Request)} if the docsData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/docs-data")
    public ResponseEntity<DocsData> createDocsData(@Valid @RequestBody DocsData docsData) throws URISyntaxException {
        log.debug("REST request to save DocsData : {}", docsData);
        if (docsData.getId() != null) {
            throw new BadRequestAlertException("A new docsData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocsData result = docsDataService.save(docsData);
        return ResponseEntity
            .created(new URI("/api/docs-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /docs-data/:id} : Updates an existing docsData.
     *
     * @param id the id of the docsData to save.
     * @param docsData the docsData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docsData,
     * or with status {@code 400 (Bad Request)} if the docsData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docsData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/docs-data/{id}")
    public ResponseEntity<DocsData> updateDocsData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocsData docsData
    ) throws URISyntaxException {
        log.debug("REST request to update DocsData : {}, {}", id, docsData);
        if (docsData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docsData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocsData result = docsDataService.update(docsData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docsData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /docs-data/:id} : Partial updates given fields of an existing docsData, field will ignore if it is null
     *
     * @param id the id of the docsData to save.
     * @param docsData the docsData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docsData,
     * or with status {@code 400 (Bad Request)} if the docsData is not valid,
     * or with status {@code 404 (Not Found)} if the docsData is not found,
     * or with status {@code 500 (Internal Server Error)} if the docsData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/docs-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocsData> partialUpdateDocsData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocsData docsData
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocsData partially : {}, {}", id, docsData);
        if (docsData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docsData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocsData> result = docsDataService.partialUpdate(docsData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docsData.getId().toString())
        );
    }

    /**
     * {@code GET  /docs-data} : get all the docsData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docsData in body.
     */
    @GetMapping("/docs-data")
    public ResponseEntity<List<DocsData>> getAllDocsData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DocsData");
        Page<DocsData> page = docsDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /docs-data/:id} : get the "id" docsData.
     *
     * @param id the id of the docsData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docsData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/docs-data/{id}")
    public ResponseEntity<DocsData> getDocsData(@PathVariable Long id) {
        log.debug("REST request to get DocsData : {}", id);
        Optional<DocsData> docsData = docsDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(docsData);
    }

    /**
     * {@code DELETE  /docs-data/:id} : delete the "id" docsData.
     *
     * @param id the id of the docsData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/docs-data/{id}")
    public ResponseEntity<Void> deleteDocsData(@PathVariable Long id) {
        log.debug("REST request to delete DocsData : {}", id);
        docsDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
