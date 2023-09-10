package com.adrian.school.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adrian.school.management.IntegrationTest;
import com.adrian.school.management.domain.DocsData;
import com.adrian.school.management.repository.DocsDataRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DocsDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocsDataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOC_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_ORDERDOC = 1;
    private static final Integer UPDATED_ORDERDOC = 2;

    private static final Instant DEFAULT_RESOURCEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESOURCEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/docs-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocsDataRepository docsDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocsDataMockMvc;

    private DocsData docsData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocsData createEntity(EntityManager em) {
        DocsData docsData = new DocsData()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .doc(DEFAULT_DOC)
            .docContentType(DEFAULT_DOC_CONTENT_TYPE)
            .orderdoc(DEFAULT_ORDERDOC)
            .resourcedate(DEFAULT_RESOURCEDATE);
        return docsData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocsData createUpdatedEntity(EntityManager em) {
        DocsData docsData = new DocsData()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .orderdoc(UPDATED_ORDERDOC)
            .resourcedate(UPDATED_RESOURCEDATE);
        return docsData;
    }

    @BeforeEach
    public void initTest() {
        docsData = createEntity(em);
    }

    @Test
    @Transactional
    void createDocsData() throws Exception {
        int databaseSizeBeforeCreate = docsDataRepository.findAll().size();
        // Create the DocsData
        restDocsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docsData)))
            .andExpect(status().isCreated());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeCreate + 1);
        DocsData testDocsData = docsDataList.get(docsDataList.size() - 1);
        assertThat(testDocsData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocsData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDocsData.getDoc()).isEqualTo(DEFAULT_DOC);
        assertThat(testDocsData.getDocContentType()).isEqualTo(DEFAULT_DOC_CONTENT_TYPE);
        assertThat(testDocsData.getOrderdoc()).isEqualTo(DEFAULT_ORDERDOC);
        assertThat(testDocsData.getResourcedate()).isEqualTo(DEFAULT_RESOURCEDATE);
    }

    @Test
    @Transactional
    void createDocsDataWithExistingId() throws Exception {
        // Create the DocsData with an existing ID
        docsData.setId(1L);

        int databaseSizeBeforeCreate = docsDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docsData)))
            .andExpect(status().isBadRequest());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = docsDataRepository.findAll().size();
        // set the field null
        docsData.setName(null);

        // Create the DocsData, which fails.

        restDocsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docsData)))
            .andExpect(status().isBadRequest());

        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderdocIsRequired() throws Exception {
        int databaseSizeBeforeTest = docsDataRepository.findAll().size();
        // set the field null
        docsData.setOrderdoc(null);

        // Create the DocsData, which fails.

        restDocsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docsData)))
            .andExpect(status().isBadRequest());

        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResourcedateIsRequired() throws Exception {
        int databaseSizeBeforeTest = docsDataRepository.findAll().size();
        // set the field null
        docsData.setResourcedate(null);

        // Create the DocsData, which fails.

        restDocsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docsData)))
            .andExpect(status().isBadRequest());

        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocsData() throws Exception {
        // Initialize the database
        docsDataRepository.saveAndFlush(docsData);

        // Get all the docsDataList
        restDocsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docsData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].docContentType").value(hasItem(DEFAULT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].doc").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOC))))
            .andExpect(jsonPath("$.[*].orderdoc").value(hasItem(DEFAULT_ORDERDOC)))
            .andExpect(jsonPath("$.[*].resourcedate").value(hasItem(DEFAULT_RESOURCEDATE.toString())));
    }

    @Test
    @Transactional
    void getDocsData() throws Exception {
        // Initialize the database
        docsDataRepository.saveAndFlush(docsData);

        // Get the docsData
        restDocsDataMockMvc
            .perform(get(ENTITY_API_URL_ID, docsData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(docsData.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.docContentType").value(DEFAULT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.doc").value(Base64Utils.encodeToString(DEFAULT_DOC)))
            .andExpect(jsonPath("$.orderdoc").value(DEFAULT_ORDERDOC))
            .andExpect(jsonPath("$.resourcedate").value(DEFAULT_RESOURCEDATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDocsData() throws Exception {
        // Get the docsData
        restDocsDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocsData() throws Exception {
        // Initialize the database
        docsDataRepository.saveAndFlush(docsData);

        int databaseSizeBeforeUpdate = docsDataRepository.findAll().size();

        // Update the docsData
        DocsData updatedDocsData = docsDataRepository.findById(docsData.getId()).get();
        // Disconnect from session so that the updates on updatedDocsData are not directly saved in db
        em.detach(updatedDocsData);
        updatedDocsData
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .orderdoc(UPDATED_ORDERDOC)
            .resourcedate(UPDATED_RESOURCEDATE);

        restDocsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocsData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocsData))
            )
            .andExpect(status().isOk());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeUpdate);
        DocsData testDocsData = docsDataList.get(docsDataList.size() - 1);
        assertThat(testDocsData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocsData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocsData.getDoc()).isEqualTo(UPDATED_DOC);
        assertThat(testDocsData.getDocContentType()).isEqualTo(UPDATED_DOC_CONTENT_TYPE);
        assertThat(testDocsData.getOrderdoc()).isEqualTo(UPDATED_ORDERDOC);
        assertThat(testDocsData.getResourcedate()).isEqualTo(UPDATED_RESOURCEDATE);
    }

    @Test
    @Transactional
    void putNonExistingDocsData() throws Exception {
        int databaseSizeBeforeUpdate = docsDataRepository.findAll().size();
        docsData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, docsData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocsData() throws Exception {
        int databaseSizeBeforeUpdate = docsDataRepository.findAll().size();
        docsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocsData() throws Exception {
        int databaseSizeBeforeUpdate = docsDataRepository.findAll().size();
        docsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docsData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocsDataWithPatch() throws Exception {
        // Initialize the database
        docsDataRepository.saveAndFlush(docsData);

        int databaseSizeBeforeUpdate = docsDataRepository.findAll().size();

        // Update the docsData using partial update
        DocsData partialUpdatedDocsData = new DocsData();
        partialUpdatedDocsData.setId(docsData.getId());

        partialUpdatedDocsData
            .name(UPDATED_NAME)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .orderdoc(UPDATED_ORDERDOC)
            .resourcedate(UPDATED_RESOURCEDATE);

        restDocsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocsData))
            )
            .andExpect(status().isOk());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeUpdate);
        DocsData testDocsData = docsDataList.get(docsDataList.size() - 1);
        assertThat(testDocsData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocsData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDocsData.getDoc()).isEqualTo(UPDATED_DOC);
        assertThat(testDocsData.getDocContentType()).isEqualTo(UPDATED_DOC_CONTENT_TYPE);
        assertThat(testDocsData.getOrderdoc()).isEqualTo(UPDATED_ORDERDOC);
        assertThat(testDocsData.getResourcedate()).isEqualTo(UPDATED_RESOURCEDATE);
    }

    @Test
    @Transactional
    void fullUpdateDocsDataWithPatch() throws Exception {
        // Initialize the database
        docsDataRepository.saveAndFlush(docsData);

        int databaseSizeBeforeUpdate = docsDataRepository.findAll().size();

        // Update the docsData using partial update
        DocsData partialUpdatedDocsData = new DocsData();
        partialUpdatedDocsData.setId(docsData.getId());

        partialUpdatedDocsData
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .orderdoc(UPDATED_ORDERDOC)
            .resourcedate(UPDATED_RESOURCEDATE);

        restDocsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocsData))
            )
            .andExpect(status().isOk());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeUpdate);
        DocsData testDocsData = docsDataList.get(docsDataList.size() - 1);
        assertThat(testDocsData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocsData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocsData.getDoc()).isEqualTo(UPDATED_DOC);
        assertThat(testDocsData.getDocContentType()).isEqualTo(UPDATED_DOC_CONTENT_TYPE);
        assertThat(testDocsData.getOrderdoc()).isEqualTo(UPDATED_ORDERDOC);
        assertThat(testDocsData.getResourcedate()).isEqualTo(UPDATED_RESOURCEDATE);
    }

    @Test
    @Transactional
    void patchNonExistingDocsData() throws Exception {
        int databaseSizeBeforeUpdate = docsDataRepository.findAll().size();
        docsData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, docsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(docsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocsData() throws Exception {
        int databaseSizeBeforeUpdate = docsDataRepository.findAll().size();
        docsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(docsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocsData() throws Exception {
        int databaseSizeBeforeUpdate = docsDataRepository.findAll().size();
        docsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocsDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(docsData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocsData in the database
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocsData() throws Exception {
        // Initialize the database
        docsDataRepository.saveAndFlush(docsData);

        int databaseSizeBeforeDelete = docsDataRepository.findAll().size();

        // Delete the docsData
        restDocsDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, docsData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocsData> docsDataList = docsDataRepository.findAll();
        assertThat(docsDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
