package com.adrian.school.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adrian.school.management.IntegrationTest;
import com.adrian.school.management.domain.ShortNewsData;
import com.adrian.school.management.repository.ShortNewsDataRepository;
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

/**
 * Integration tests for the {@link ShortNewsDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShortNewsDataResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINK_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_NEWS = 1;
    private static final Integer UPDATED_ORDER_NEWS = 2;

    private static final String ENTITY_API_URL = "/api/short-news-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShortNewsDataRepository shortNewsDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShortNewsDataMockMvc;

    private ShortNewsData shortNewsData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShortNewsData createEntity(EntityManager em) {
        ShortNewsData shortNewsData = new ShortNewsData().title(DEFAULT_TITLE).linkUrl(DEFAULT_LINK_URL).orderNews(DEFAULT_ORDER_NEWS);
        return shortNewsData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShortNewsData createUpdatedEntity(EntityManager em) {
        ShortNewsData shortNewsData = new ShortNewsData().title(UPDATED_TITLE).linkUrl(UPDATED_LINK_URL).orderNews(UPDATED_ORDER_NEWS);
        return shortNewsData;
    }

    @BeforeEach
    public void initTest() {
        shortNewsData = createEntity(em);
    }

    @Test
    @Transactional
    void createShortNewsData() throws Exception {
        int databaseSizeBeforeCreate = shortNewsDataRepository.findAll().size();
        // Create the ShortNewsData
        restShortNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shortNewsData)))
            .andExpect(status().isCreated());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeCreate + 1);
        ShortNewsData testShortNewsData = shortNewsDataList.get(shortNewsDataList.size() - 1);
        assertThat(testShortNewsData.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testShortNewsData.getLinkUrl()).isEqualTo(DEFAULT_LINK_URL);
        assertThat(testShortNewsData.getOrderNews()).isEqualTo(DEFAULT_ORDER_NEWS);
    }

    @Test
    @Transactional
    void createShortNewsDataWithExistingId() throws Exception {
        // Create the ShortNewsData with an existing ID
        shortNewsData.setId(1L);

        int databaseSizeBeforeCreate = shortNewsDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShortNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shortNewsData)))
            .andExpect(status().isBadRequest());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = shortNewsDataRepository.findAll().size();
        // set the field null
        shortNewsData.setTitle(null);

        // Create the ShortNewsData, which fails.

        restShortNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shortNewsData)))
            .andExpect(status().isBadRequest());

        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderNewsIsRequired() throws Exception {
        int databaseSizeBeforeTest = shortNewsDataRepository.findAll().size();
        // set the field null
        shortNewsData.setOrderNews(null);

        // Create the ShortNewsData, which fails.

        restShortNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shortNewsData)))
            .andExpect(status().isBadRequest());

        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShortNewsData() throws Exception {
        // Initialize the database
        shortNewsDataRepository.saveAndFlush(shortNewsData);

        // Get all the shortNewsDataList
        restShortNewsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shortNewsData.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].linkUrl").value(hasItem(DEFAULT_LINK_URL)))
            .andExpect(jsonPath("$.[*].orderNews").value(hasItem(DEFAULT_ORDER_NEWS)));
    }

    @Test
    @Transactional
    void getShortNewsData() throws Exception {
        // Initialize the database
        shortNewsDataRepository.saveAndFlush(shortNewsData);

        // Get the shortNewsData
        restShortNewsDataMockMvc
            .perform(get(ENTITY_API_URL_ID, shortNewsData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shortNewsData.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.linkUrl").value(DEFAULT_LINK_URL))
            .andExpect(jsonPath("$.orderNews").value(DEFAULT_ORDER_NEWS));
    }

    @Test
    @Transactional
    void getNonExistingShortNewsData() throws Exception {
        // Get the shortNewsData
        restShortNewsDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShortNewsData() throws Exception {
        // Initialize the database
        shortNewsDataRepository.saveAndFlush(shortNewsData);

        int databaseSizeBeforeUpdate = shortNewsDataRepository.findAll().size();

        // Update the shortNewsData
        ShortNewsData updatedShortNewsData = shortNewsDataRepository.findById(shortNewsData.getId()).get();
        // Disconnect from session so that the updates on updatedShortNewsData are not directly saved in db
        em.detach(updatedShortNewsData);
        updatedShortNewsData.title(UPDATED_TITLE).linkUrl(UPDATED_LINK_URL).orderNews(UPDATED_ORDER_NEWS);

        restShortNewsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedShortNewsData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedShortNewsData))
            )
            .andExpect(status().isOk());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeUpdate);
        ShortNewsData testShortNewsData = shortNewsDataList.get(shortNewsDataList.size() - 1);
        assertThat(testShortNewsData.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testShortNewsData.getLinkUrl()).isEqualTo(UPDATED_LINK_URL);
        assertThat(testShortNewsData.getOrderNews()).isEqualTo(UPDATED_ORDER_NEWS);
    }

    @Test
    @Transactional
    void putNonExistingShortNewsData() throws Exception {
        int databaseSizeBeforeUpdate = shortNewsDataRepository.findAll().size();
        shortNewsData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShortNewsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shortNewsData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shortNewsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShortNewsData() throws Exception {
        int databaseSizeBeforeUpdate = shortNewsDataRepository.findAll().size();
        shortNewsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShortNewsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shortNewsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShortNewsData() throws Exception {
        int databaseSizeBeforeUpdate = shortNewsDataRepository.findAll().size();
        shortNewsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShortNewsDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shortNewsData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShortNewsDataWithPatch() throws Exception {
        // Initialize the database
        shortNewsDataRepository.saveAndFlush(shortNewsData);

        int databaseSizeBeforeUpdate = shortNewsDataRepository.findAll().size();

        // Update the shortNewsData using partial update
        ShortNewsData partialUpdatedShortNewsData = new ShortNewsData();
        partialUpdatedShortNewsData.setId(shortNewsData.getId());

        partialUpdatedShortNewsData.title(UPDATED_TITLE).linkUrl(UPDATED_LINK_URL).orderNews(UPDATED_ORDER_NEWS);

        restShortNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShortNewsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShortNewsData))
            )
            .andExpect(status().isOk());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeUpdate);
        ShortNewsData testShortNewsData = shortNewsDataList.get(shortNewsDataList.size() - 1);
        assertThat(testShortNewsData.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testShortNewsData.getLinkUrl()).isEqualTo(UPDATED_LINK_URL);
        assertThat(testShortNewsData.getOrderNews()).isEqualTo(UPDATED_ORDER_NEWS);
    }

    @Test
    @Transactional
    void fullUpdateShortNewsDataWithPatch() throws Exception {
        // Initialize the database
        shortNewsDataRepository.saveAndFlush(shortNewsData);

        int databaseSizeBeforeUpdate = shortNewsDataRepository.findAll().size();

        // Update the shortNewsData using partial update
        ShortNewsData partialUpdatedShortNewsData = new ShortNewsData();
        partialUpdatedShortNewsData.setId(shortNewsData.getId());

        partialUpdatedShortNewsData.title(UPDATED_TITLE).linkUrl(UPDATED_LINK_URL).orderNews(UPDATED_ORDER_NEWS);

        restShortNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShortNewsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShortNewsData))
            )
            .andExpect(status().isOk());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeUpdate);
        ShortNewsData testShortNewsData = shortNewsDataList.get(shortNewsDataList.size() - 1);
        assertThat(testShortNewsData.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testShortNewsData.getLinkUrl()).isEqualTo(UPDATED_LINK_URL);
        assertThat(testShortNewsData.getOrderNews()).isEqualTo(UPDATED_ORDER_NEWS);
    }

    @Test
    @Transactional
    void patchNonExistingShortNewsData() throws Exception {
        int databaseSizeBeforeUpdate = shortNewsDataRepository.findAll().size();
        shortNewsData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShortNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shortNewsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shortNewsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShortNewsData() throws Exception {
        int databaseSizeBeforeUpdate = shortNewsDataRepository.findAll().size();
        shortNewsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShortNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shortNewsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShortNewsData() throws Exception {
        int databaseSizeBeforeUpdate = shortNewsDataRepository.findAll().size();
        shortNewsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShortNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shortNewsData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShortNewsData in the database
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShortNewsData() throws Exception {
        // Initialize the database
        shortNewsDataRepository.saveAndFlush(shortNewsData);

        int databaseSizeBeforeDelete = shortNewsDataRepository.findAll().size();

        // Delete the shortNewsData
        restShortNewsDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, shortNewsData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShortNewsData> shortNewsDataList = shortNewsDataRepository.findAll();
        assertThat(shortNewsDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
