package com.adrian.school.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adrian.school.management.IntegrationTest;
import com.adrian.school.management.domain.NewsData;
import com.adrian.school.management.repository.NewsDataRepository;
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
 * Integration tests for the {@link NewsDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NewsDataResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_LINK_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LINK_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINK_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_NEWS = 1;
    private static final Integer UPDATED_ORDER_NEWS = 2;

    private static final String ENTITY_API_URL = "/api/news-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NewsDataRepository newsDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsDataMockMvc;

    private NewsData newsData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsData createEntity(EntityManager em) {
        NewsData newsData = new NewsData()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .linkLabel(DEFAULT_LINK_LABEL)
            .linkUrl(DEFAULT_LINK_URL)
            .orderNews(DEFAULT_ORDER_NEWS);
        return newsData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsData createUpdatedEntity(EntityManager em) {
        NewsData newsData = new NewsData()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .linkLabel(UPDATED_LINK_LABEL)
            .linkUrl(UPDATED_LINK_URL)
            .orderNews(UPDATED_ORDER_NEWS);
        return newsData;
    }

    @BeforeEach
    public void initTest() {
        newsData = createEntity(em);
    }

    @Test
    @Transactional
    void createNewsData() throws Exception {
        int databaseSizeBeforeCreate = newsDataRepository.findAll().size();
        // Create the NewsData
        restNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsData)))
            .andExpect(status().isCreated());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeCreate + 1);
        NewsData testNewsData = newsDataList.get(newsDataList.size() - 1);
        assertThat(testNewsData.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNewsData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNewsData.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testNewsData.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testNewsData.getLinkLabel()).isEqualTo(DEFAULT_LINK_LABEL);
        assertThat(testNewsData.getLinkUrl()).isEqualTo(DEFAULT_LINK_URL);
        assertThat(testNewsData.getOrderNews()).isEqualTo(DEFAULT_ORDER_NEWS);
    }

    @Test
    @Transactional
    void createNewsDataWithExistingId() throws Exception {
        // Create the NewsData with an existing ID
        newsData.setId(1L);

        int databaseSizeBeforeCreate = newsDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsData)))
            .andExpect(status().isBadRequest());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsDataRepository.findAll().size();
        // set the field null
        newsData.setTitle(null);

        // Create the NewsData, which fails.

        restNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsData)))
            .andExpect(status().isBadRequest());

        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsDataRepository.findAll().size();
        // set the field null
        newsData.setDescription(null);

        // Create the NewsData, which fails.

        restNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsData)))
            .andExpect(status().isBadRequest());

        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderNewsIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsDataRepository.findAll().size();
        // set the field null
        newsData.setOrderNews(null);

        // Create the NewsData, which fails.

        restNewsDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsData)))
            .andExpect(status().isBadRequest());

        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNewsData() throws Exception {
        // Initialize the database
        newsDataRepository.saveAndFlush(newsData);

        // Get all the newsDataList
        restNewsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsData.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].linkLabel").value(hasItem(DEFAULT_LINK_LABEL)))
            .andExpect(jsonPath("$.[*].linkUrl").value(hasItem(DEFAULT_LINK_URL)))
            .andExpect(jsonPath("$.[*].orderNews").value(hasItem(DEFAULT_ORDER_NEWS)));
    }

    @Test
    @Transactional
    void getNewsData() throws Exception {
        // Initialize the database
        newsDataRepository.saveAndFlush(newsData);

        // Get the newsData
        restNewsDataMockMvc
            .perform(get(ENTITY_API_URL_ID, newsData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(newsData.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.linkLabel").value(DEFAULT_LINK_LABEL))
            .andExpect(jsonPath("$.linkUrl").value(DEFAULT_LINK_URL))
            .andExpect(jsonPath("$.orderNews").value(DEFAULT_ORDER_NEWS));
    }

    @Test
    @Transactional
    void getNonExistingNewsData() throws Exception {
        // Get the newsData
        restNewsDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNewsData() throws Exception {
        // Initialize the database
        newsDataRepository.saveAndFlush(newsData);

        int databaseSizeBeforeUpdate = newsDataRepository.findAll().size();

        // Update the newsData
        NewsData updatedNewsData = newsDataRepository.findById(newsData.getId()).get();
        // Disconnect from session so that the updates on updatedNewsData are not directly saved in db
        em.detach(updatedNewsData);
        updatedNewsData
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .linkLabel(UPDATED_LINK_LABEL)
            .linkUrl(UPDATED_LINK_URL)
            .orderNews(UPDATED_ORDER_NEWS);

        restNewsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNewsData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNewsData))
            )
            .andExpect(status().isOk());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeUpdate);
        NewsData testNewsData = newsDataList.get(newsDataList.size() - 1);
        assertThat(testNewsData.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNewsData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNewsData.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testNewsData.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testNewsData.getLinkLabel()).isEqualTo(UPDATED_LINK_LABEL);
        assertThat(testNewsData.getLinkUrl()).isEqualTo(UPDATED_LINK_URL);
        assertThat(testNewsData.getOrderNews()).isEqualTo(UPDATED_ORDER_NEWS);
    }

    @Test
    @Transactional
    void putNonExistingNewsData() throws Exception {
        int databaseSizeBeforeUpdate = newsDataRepository.findAll().size();
        newsData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, newsData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNewsData() throws Exception {
        int databaseSizeBeforeUpdate = newsDataRepository.findAll().size();
        newsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNewsData() throws Exception {
        int databaseSizeBeforeUpdate = newsDataRepository.findAll().size();
        newsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNewsDataWithPatch() throws Exception {
        // Initialize the database
        newsDataRepository.saveAndFlush(newsData);

        int databaseSizeBeforeUpdate = newsDataRepository.findAll().size();

        // Update the newsData using partial update
        NewsData partialUpdatedNewsData = new NewsData();
        partialUpdatedNewsData.setId(newsData.getId());

        partialUpdatedNewsData
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .linkUrl(UPDATED_LINK_URL)
            .orderNews(UPDATED_ORDER_NEWS);

        restNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNewsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNewsData))
            )
            .andExpect(status().isOk());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeUpdate);
        NewsData testNewsData = newsDataList.get(newsDataList.size() - 1);
        assertThat(testNewsData.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNewsData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNewsData.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testNewsData.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testNewsData.getLinkLabel()).isEqualTo(DEFAULT_LINK_LABEL);
        assertThat(testNewsData.getLinkUrl()).isEqualTo(UPDATED_LINK_URL);
        assertThat(testNewsData.getOrderNews()).isEqualTo(UPDATED_ORDER_NEWS);
    }

    @Test
    @Transactional
    void fullUpdateNewsDataWithPatch() throws Exception {
        // Initialize the database
        newsDataRepository.saveAndFlush(newsData);

        int databaseSizeBeforeUpdate = newsDataRepository.findAll().size();

        // Update the newsData using partial update
        NewsData partialUpdatedNewsData = new NewsData();
        partialUpdatedNewsData.setId(newsData.getId());

        partialUpdatedNewsData
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .linkLabel(UPDATED_LINK_LABEL)
            .linkUrl(UPDATED_LINK_URL)
            .orderNews(UPDATED_ORDER_NEWS);

        restNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNewsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNewsData))
            )
            .andExpect(status().isOk());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeUpdate);
        NewsData testNewsData = newsDataList.get(newsDataList.size() - 1);
        assertThat(testNewsData.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNewsData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNewsData.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testNewsData.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testNewsData.getLinkLabel()).isEqualTo(UPDATED_LINK_LABEL);
        assertThat(testNewsData.getLinkUrl()).isEqualTo(UPDATED_LINK_URL);
        assertThat(testNewsData.getOrderNews()).isEqualTo(UPDATED_ORDER_NEWS);
    }

    @Test
    @Transactional
    void patchNonExistingNewsData() throws Exception {
        int databaseSizeBeforeUpdate = newsDataRepository.findAll().size();
        newsData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, newsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNewsData() throws Exception {
        int databaseSizeBeforeUpdate = newsDataRepository.findAll().size();
        newsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsData))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNewsData() throws Exception {
        int databaseSizeBeforeUpdate = newsDataRepository.findAll().size();
        newsData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(newsData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NewsData in the database
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNewsData() throws Exception {
        // Initialize the database
        newsDataRepository.saveAndFlush(newsData);

        int databaseSizeBeforeDelete = newsDataRepository.findAll().size();

        // Delete the newsData
        restNewsDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, newsData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NewsData> newsDataList = newsDataRepository.findAll();
        assertThat(newsDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
