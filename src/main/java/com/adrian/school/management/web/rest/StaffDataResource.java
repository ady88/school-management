package com.adrian.school.management.web.rest;

import com.adrian.school.management.domain.StaffData;
import com.adrian.school.management.repository.StaffDataRepository;
import com.adrian.school.management.service.StaffDataService;
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
 * REST controller for managing {@link com.adrian.school.management.domain.StaffData}.
 */
@RestController
@RequestMapping("/api")
public class StaffDataResource {

    private final Logger log = LoggerFactory.getLogger(StaffDataResource.class);

    private static final String ENTITY_NAME = "staffData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaffDataService staffDataService;

    private final StaffDataRepository staffDataRepository;

    public StaffDataResource(StaffDataService staffDataService, StaffDataRepository staffDataRepository) {
        this.staffDataService = staffDataService;
        this.staffDataRepository = staffDataRepository;
    }

    /**
     * {@code POST  /staff-data} : Create a new staffData.
     *
     * @param staffData the staffData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staffData, or with status {@code 400 (Bad Request)} if the staffData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/staff-data")
    public ResponseEntity<StaffData> createStaffData(@Valid @RequestBody StaffData staffData) throws URISyntaxException {
        log.debug("REST request to save StaffData : {}", staffData);
        if (staffData.getId() != null) {
            throw new BadRequestAlertException("A new staffData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaffData result = staffDataService.save(staffData);
        return ResponseEntity
            .created(new URI("/api/staff-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /staff-data/:id} : Updates an existing staffData.
     *
     * @param id the id of the staffData to save.
     * @param staffData the staffData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staffData,
     * or with status {@code 400 (Bad Request)} if the staffData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staffData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/staff-data/{id}")
    public ResponseEntity<StaffData> updateStaffData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StaffData staffData
    ) throws URISyntaxException {
        log.debug("REST request to update StaffData : {}, {}", id, staffData);
        if (staffData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, staffData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!staffDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StaffData result = staffDataService.update(staffData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staffData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /staff-data/:id} : Partial updates given fields of an existing staffData, field will ignore if it is null
     *
     * @param id the id of the staffData to save.
     * @param staffData the staffData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staffData,
     * or with status {@code 400 (Bad Request)} if the staffData is not valid,
     * or with status {@code 404 (Not Found)} if the staffData is not found,
     * or with status {@code 500 (Internal Server Error)} if the staffData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/staff-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StaffData> partialUpdateStaffData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StaffData staffData
    ) throws URISyntaxException {
        log.debug("REST request to partial update StaffData partially : {}, {}", id, staffData);
        if (staffData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, staffData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!staffDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StaffData> result = staffDataService.partialUpdate(staffData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staffData.getId().toString())
        );
    }

    /**
     * {@code GET  /staff-data} : get all the staffData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staffData in body.
     */
    @GetMapping("/staff-data")
    public ResponseEntity<List<StaffData>> getAllStaffData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of StaffData");
        Page<StaffData> page = staffDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /staff-data/:id} : get the "id" staffData.
     *
     * @param id the id of the staffData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staffData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/staff-data/{id}")
    public ResponseEntity<StaffData> getStaffData(@PathVariable Long id) {
        log.debug("REST request to get StaffData : {}", id);
        Optional<StaffData> staffData = staffDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(staffData);
    }

    /**
     * {@code DELETE  /staff-data/:id} : delete the "id" staffData.
     *
     * @param id the id of the staffData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/staff-data/{id}")
    public ResponseEntity<Void> deleteStaffData(@PathVariable Long id) {
        log.debug("REST request to delete StaffData : {}", id);
        staffDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
