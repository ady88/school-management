package com.adrian.school.management.web.rest;

import com.adrian.school.management.domain.NewsData;
import com.adrian.school.management.repository.NewsDataRepository;
import com.adrian.school.management.service.NewsDataService;
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
 * REST controller for managing {@link com.adrian.school.management.domain.NewsData}.
 */
@RestController
@RequestMapping("/api")
public class NewsDataResource {

    private final Logger log = LoggerFactory.getLogger(NewsDataResource.class);

    private static final String ENTITY_NAME = "newsData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NewsDataService newsDataService;

    private final NewsDataRepository newsDataRepository;

    public NewsDataResource(NewsDataService newsDataService, NewsDataRepository newsDataRepository) {
        this.newsDataService = newsDataService;
        this.newsDataRepository = newsDataRepository;
    }

    /**
     * {@code POST  /news-data} : Create a new newsData.
     *
     * @param newsData the newsData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new newsData, or with status {@code 400 (Bad Request)} if the newsData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/news-data")
    public ResponseEntity<NewsData> createNewsData(@Valid @RequestBody NewsData newsData) throws URISyntaxException {
        log.debug("REST request to save NewsData : {}", newsData);
        if (newsData.getId() != null) {
            throw new BadRequestAlertException("A new newsData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsData result = newsDataService.save(newsData);
        return ResponseEntity
            .created(new URI("/api/news-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /news-data/:id} : Updates an existing newsData.
     *
     * @param id the id of the newsData to save.
     * @param newsData the newsData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsData,
     * or with status {@code 400 (Bad Request)} if the newsData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the newsData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/news-data/{id}")
    public ResponseEntity<NewsData> updateNewsData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NewsData newsData
    ) throws URISyntaxException {
        log.debug("REST request to update NewsData : {}, {}", id, newsData);
        if (newsData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, newsData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NewsData result = newsDataService.update(newsData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, newsData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /news-data/:id} : Partial updates given fields of an existing newsData, field will ignore if it is null
     *
     * @param id the id of the newsData to save.
     * @param newsData the newsData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsData,
     * or with status {@code 400 (Bad Request)} if the newsData is not valid,
     * or with status {@code 404 (Not Found)} if the newsData is not found,
     * or with status {@code 500 (Internal Server Error)} if the newsData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/news-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NewsData> partialUpdateNewsData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NewsData newsData
    ) throws URISyntaxException {
        log.debug("REST request to partial update NewsData partially : {}, {}", id, newsData);
        if (newsData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, newsData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NewsData> result = newsDataService.partialUpdate(newsData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, newsData.getId().toString())
        );
    }

    /**
     * {@code GET  /news-data} : get all the newsData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of newsData in body.
     */
    @GetMapping("/news-data")
    public ResponseEntity<List<NewsData>> getAllNewsData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NewsData");
        Page<NewsData> page = newsDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /news-data/:id} : get the "id" newsData.
     *
     * @param id the id of the newsData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the newsData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/news-data/{id}")
    public ResponseEntity<NewsData> getNewsData(@PathVariable Long id) {
        log.debug("REST request to get NewsData : {}", id);
        Optional<NewsData> newsData = newsDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(newsData);
    }

    /**
     * {@code DELETE  /news-data/:id} : delete the "id" newsData.
     *
     * @param id the id of the newsData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/news-data/{id}")
    public ResponseEntity<Void> deleteNewsData(@PathVariable Long id) {
        log.debug("REST request to delete NewsData : {}", id);
        newsDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
