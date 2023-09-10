package com.adrian.school.management.web.rest;

import com.adrian.school.management.domain.Other;
import com.adrian.school.management.repository.OtherRepository;
import com.adrian.school.management.service.OtherService;
import com.adrian.school.management.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.adrian.school.management.domain.Other}.
 */
@RestController
@RequestMapping("/api")
public class OtherResource {

    private final Logger log = LoggerFactory.getLogger(OtherResource.class);

    private static final String ENTITY_NAME = "other";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OtherService otherService;

    private final OtherRepository otherRepository;

    public OtherResource(OtherService otherService, OtherRepository otherRepository) {
        this.otherService = otherService;
        this.otherRepository = otherRepository;
    }

    /**
     * {@code POST  /others} : Create a new other.
     *
     * @param other the other to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new other, or with status {@code 400 (Bad Request)} if the other has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/others")
    public ResponseEntity<Other> createOther(@RequestBody Other other) throws URISyntaxException {
        log.debug("REST request to save Other : {}", other);
        if (other.getId() != null) {
            throw new BadRequestAlertException("A new other cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Other result = otherService.save(other);
        return ResponseEntity
            .created(new URI("/api/others/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /others/:id} : Updates an existing other.
     *
     * @param id the id of the other to save.
     * @param other the other to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated other,
     * or with status {@code 400 (Bad Request)} if the other is not valid,
     * or with status {@code 500 (Internal Server Error)} if the other couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/others/{id}")
    public ResponseEntity<Other> updateOther(@PathVariable(value = "id", required = false) final Long id, @RequestBody Other other)
        throws URISyntaxException {
        log.debug("REST request to update Other : {}, {}", id, other);
        if (other.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, other.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otherRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Other result = otherService.update(other);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, other.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /others/:id} : Partial updates given fields of an existing other, field will ignore if it is null
     *
     * @param id the id of the other to save.
     * @param other the other to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated other,
     * or with status {@code 400 (Bad Request)} if the other is not valid,
     * or with status {@code 404 (Not Found)} if the other is not found,
     * or with status {@code 500 (Internal Server Error)} if the other couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/others/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Other> partialUpdateOther(@PathVariable(value = "id", required = false) final Long id, @RequestBody Other other)
        throws URISyntaxException {
        log.debug("REST request to partial update Other partially : {}, {}", id, other);
        if (other.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, other.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otherRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Other> result = otherService.partialUpdate(other);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, other.getId().toString())
        );
    }

    /**
     * {@code GET  /others} : get all the others.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of others in body.
     */
    @GetMapping("/others")
    public List<Other> getAllOthers() {
        log.debug("REST request to get all Others");
        return otherService.findAll();
    }

    /**
     * {@code GET  /others/:id} : get the "id" other.
     *
     * @param id the id of the other to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the other, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/others/{id}")
    public ResponseEntity<Other> getOther(@PathVariable Long id) {
        log.debug("REST request to get Other : {}", id);
        Optional<Other> other = otherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(other);
    }

    /**
     * {@code DELETE  /others/:id} : delete the "id" other.
     *
     * @param id the id of the other to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/others/{id}")
    public ResponseEntity<Void> deleteOther(@PathVariable Long id) {
        log.debug("REST request to delete Other : {}", id);
        otherService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
