package com.adrian.school.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adrian.school.management.IntegrationTest;
import com.adrian.school.management.domain.MainNewsData;
import com.adrian.school.management.repository.MainNewsDataRepository;
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
 * Integration tests for the {@link MainNewsDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MainNewsDataResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_ORDER_NEWS = 1;
    private static final Integer UPDATED_ORDER_NEWS = 2;

    private static final String ENTITY_API_URL = "/api/main-news-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MainNewsDataRepository mainNewsDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMainNewsDataMockMvc;

    private MainNewsData mainNewsData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MainNewsData createEntity(EntityManager em) {
        MainNewsData mainNewsData = new MainNewsData()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .orderNews(DEFAULT_ORDER_NEWS);
        return mainNewsData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MainNewsData createUpdatedEntity(EntityManager em) {
        MainNewsData mainNewsData = new MainNewsData()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .orderNews(UPDATED_ORDER_NEWS);
        return mainNewsData;
    }

    @BeforeEach
    public void initTest() {
        mainNewsData = createEntity(em);
    }

    @Test
    @Transactional
    void createMainNewsData() throws Exception {
        int databaseSizeBeforeCreate = mainNewsDataRepository.findAll().size();
        // Create the MainNewsData
        restMainNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mainNewsData)))
            .andExpect(status().isCreated());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeCreate + 1);
        MainNewsData testMainNewsData = mainNewsDataList.get(mainNewsDataList.size() - 1);
        assertThat(testMainNewsData.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMainNewsData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMainNewsData.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMainNewsData.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testMainNewsData.getOrderNews()).isEqualTo(DEFAULT_ORDER_NEWS);
    }

    @Test
    @Transactional
    void createMainNewsDataWithExistingId() throws Exception {
        // Create the MainNewsData with an existing ID
        mainNewsData.setId(1L);

        int databaseSizeBeforeCreate = mainNewsDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMainNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mainNewsData)))
            .andExpect(status().isBadRequest());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = mainNewsDataRepository.findAll().size();
        // set the field null
        mainNewsData.setTitle(null);

        // Create the MainNewsData, which fails.

        restMainNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mainNewsData)))
            .andExpect(status().isBadRequest());

        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mainNewsDataRepository.findAll().size();
        // set the field null
        mainNewsData.setDescription(null);

        // Create the MainNewsData, which fails.

        restMainNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mainNewsData)))
            .andExpect(status().isBadRequest());

        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderNewsIsRequired() throws Exception {
        int databaseSizeBeforeTest = mainNewsDataRepository.findAll().size();
        // set the field null
        mainNewsData.setOrderNews(null);

        // Create the MainNewsData, which fails.

        restMainNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mainNewsData)))
            .andExpect(status().isBadRequest());

        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMainNewsData() throws Exception {
        // Initialize the database
        mainNewsDataRepository.saveAndFlush(mainNewsData);

        // Get all the mainNewsDataList
        restMainNewsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mainNewsData.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].orderNews").value(hasItem(DEFAULT_ORDER_NEWS)));
    }

    @Test
    @Transactional
    void getMainNewsData() throws Exception {
        // Initialize the database
        mainNewsDataRepository.saveAndFlush(mainNewsData);

        // Get the mainNewsData
        restMainNewsDataMockMvc
            .perform(get(ENTITY_API_URL_ID, mainNewsData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mainNewsData.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.orderNews").value(DEFAULT_ORDER_NEWS));
    }

    @Test
    @Transactional
    void getNonExistingMainNewsData() throws Exception {
        // Get the mainNewsData
        restMainNewsDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMainNewsData() throws Exception {
        // Initialize the database
        mainNewsDataRepository.saveAndFlush(mainNewsData);

        int databaseSizeBeforeUpdate = mainNewsDataRepository.findAll().size();

        // Update the mainNewsData
        MainNewsData updatedMainNewsData = mainNewsDataRepository.findById(mainNewsData.getId()).get();
        // Disconnect from session so that the updates on updatedMainNewsData are not directly saved in db
        em.detach(updatedMainNewsData);
        updatedMainNewsData
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .orderNews(UPDATED_ORDER_NEWS);

        restMainNewsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMainNewsData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMainNewsData))
            )
            .andExpect(status().isOk());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeUpdate);
        MainNewsData testMainNewsData = mainNewsDataList.get(mainNewsDataList.size() - 1);
        assertThat(testMainNewsData.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMainNewsData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMainNewsData.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMainNewsData.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testMainNewsData.getOrderNews()).isEqualTo(UPDATED_ORDER_NEWS);
    }

    @Test
    @Transactional
    void putNonExistingMainNewsData() throws Exception {
        int databaseSizeBeforeUpdate = mainNewsDataRepository.findAll().size();
        mainNewsData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMainNewsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mainNewsData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mainNewsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMainNewsData() throws Exception {
        int databaseSizeBeforeUpdate = mainNewsDataRepository.findAll().size();
        mainNewsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMainNewsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mainNewsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMainNewsData() throws Exception {
        int databaseSizeBeforeUpdate = mainNewsDataRepository.findAll().size();
        mainNewsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMainNewsDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mainNewsData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMainNewsDataWithPatch() throws Exception {
        // Initialize the database
        mainNewsDataRepository.saveAndFlush(mainNewsData);

        int databaseSizeBeforeUpdate = mainNewsDataRepository.findAll().size();

        // Update the mainNewsData using partial update
        MainNewsData partialUpdatedMainNewsData = new MainNewsData();
        partialUpdatedMainNewsData.setId(mainNewsData.getId());

        partialUpdatedMainNewsData.orderNews(UPDATED_ORDER_NEWS);

        restMainNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMainNewsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMainNewsData))
            )
            .andExpect(status().isOk());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeUpdate);
        MainNewsData testMainNewsData = mainNewsDataList.get(mainNewsDataList.size() - 1);
        assertThat(testMainNewsData.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMainNewsData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMainNewsData.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testMainNewsData.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testMainNewsData.getOrderNews()).isEqualTo(UPDATED_ORDER_NEWS);
    }

    @Test
    @Transactional
    void fullUpdateMainNewsDataWithPatch() throws Exception {
        // Initialize the database
        mainNewsDataRepository.saveAndFlush(mainNewsData);

        int databaseSizeBeforeUpdate = mainNewsDataRepository.findAll().size();

        // Update the mainNewsData using partial update
        MainNewsData partialUpdatedMainNewsData = new MainNewsData();
        partialUpdatedMainNewsData.setId(mainNewsData.getId());

        partialUpdatedMainNewsData
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .orderNews(UPDATED_ORDER_NEWS);

        restMainNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMainNewsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMainNewsData))
            )
            .andExpect(status().isOk());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeUpdate);
        MainNewsData testMainNewsData = mainNewsDataList.get(mainNewsDataList.size() - 1);
        assertThat(testMainNewsData.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMainNewsData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMainNewsData.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testMainNewsData.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testMainNewsData.getOrderNews()).isEqualTo(UPDATED_ORDER_NEWS);
    }

    @Test
    @Transactional
    void patchNonExistingMainNewsData() throws Exception {
        int databaseSizeBeforeUpdate = mainNewsDataRepository.findAll().size();
        mainNewsData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMainNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mainNewsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mainNewsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMainNewsData() throws Exception {
        int databaseSizeBeforeUpdate = mainNewsDataRepository.findAll().size();
        mainNewsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMainNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mainNewsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMainNewsData() throws Exception {
        int databaseSizeBeforeUpdate = mainNewsDataRepository.findAll().size();
        mainNewsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMainNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mainNewsData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MainNewsData in the database
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMainNewsData() throws Exception {
        // Initialize the database
        mainNewsDataRepository.saveAndFlush(mainNewsData);

        int databaseSizeBeforeDelete = mainNewsDataRepository.findAll().size();

        // Delete the mainNewsData
        restMainNewsDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, mainNewsData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MainNewsData> mainNewsDataList = mainNewsDataRepository.findAll();
        assertThat(mainNewsDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
