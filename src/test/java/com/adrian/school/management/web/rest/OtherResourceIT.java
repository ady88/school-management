package com.adrian.school.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adrian.school.management.IntegrationTest;
import com.adrian.school.management.domain.Other;
import com.adrian.school.management.domain.enumeration.Theme;
import com.adrian.school.management.repository.OtherRepository;
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
 * Integration tests for the {@link OtherResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OtherResourceIT {

    private static final Boolean DEFAULT_USE_TOP_IMAGE = false;
    private static final Boolean UPDATED_USE_TOP_IMAGE = true;

    private static final byte[] DEFAULT_TOP_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TOP_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TOP_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TOP_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_USE_MAIN_IMAGE = false;
    private static final Boolean UPDATED_USE_MAIN_IMAGE = true;

    private static final byte[] DEFAULT_MAIN_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MAIN_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MAIN_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MAIN_IMAGE_CONTENT_TYPE = "image/png";

    private static final Theme DEFAULT_THEME = Theme.BLUE;
    private static final Theme UPDATED_THEME = Theme.GREEN;

    private static final String ENTITY_API_URL = "/api/others";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OtherRepository otherRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtherMockMvc;

    private Other other;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Other createEntity(EntityManager em) {
        Other other = new Other()
            .useTopImage(DEFAULT_USE_TOP_IMAGE)
            .topImage(DEFAULT_TOP_IMAGE)
            .topImageContentType(DEFAULT_TOP_IMAGE_CONTENT_TYPE)
            .useMainImage(DEFAULT_USE_MAIN_IMAGE)
            .mainImage(DEFAULT_MAIN_IMAGE)
            .mainImageContentType(DEFAULT_MAIN_IMAGE_CONTENT_TYPE)
            .theme(DEFAULT_THEME);
        return other;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Other createUpdatedEntity(EntityManager em) {
        Other other = new Other()
            .useTopImage(UPDATED_USE_TOP_IMAGE)
            .topImage(UPDATED_TOP_IMAGE)
            .topImageContentType(UPDATED_TOP_IMAGE_CONTENT_TYPE)
            .useMainImage(UPDATED_USE_MAIN_IMAGE)
            .mainImage(UPDATED_MAIN_IMAGE)
            .mainImageContentType(UPDATED_MAIN_IMAGE_CONTENT_TYPE)
            .theme(UPDATED_THEME);
        return other;
    }

    @BeforeEach
    public void initTest() {
        other = createEntity(em);
    }

    @Test
    @Transactional
    void createOther() throws Exception {
        int databaseSizeBeforeCreate = otherRepository.findAll().size();
        // Create the Other
        restOtherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(other)))
            .andExpect(status().isCreated());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeCreate + 1);
        Other testOther = otherList.get(otherList.size() - 1);
        assertThat(testOther.getUseTopImage()).isEqualTo(DEFAULT_USE_TOP_IMAGE);
        assertThat(testOther.getTopImage()).isEqualTo(DEFAULT_TOP_IMAGE);
        assertThat(testOther.getTopImageContentType()).isEqualTo(DEFAULT_TOP_IMAGE_CONTENT_TYPE);
        assertThat(testOther.getUseMainImage()).isEqualTo(DEFAULT_USE_MAIN_IMAGE);
        assertThat(testOther.getMainImage()).isEqualTo(DEFAULT_MAIN_IMAGE);
        assertThat(testOther.getMainImageContentType()).isEqualTo(DEFAULT_MAIN_IMAGE_CONTENT_TYPE);
        assertThat(testOther.getTheme()).isEqualTo(DEFAULT_THEME);
    }

    @Test
    @Transactional
    void createOtherWithExistingId() throws Exception {
        // Create the Other with an existing ID
        other.setId(1L);

        int databaseSizeBeforeCreate = otherRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(other)))
            .andExpect(status().isBadRequest());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOthers() throws Exception {
        // Initialize the database
        otherRepository.saveAndFlush(other);

        // Get all the otherList
        restOtherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(other.getId().intValue())))
            .andExpect(jsonPath("$.[*].useTopImage").value(hasItem(DEFAULT_USE_TOP_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].topImageContentType").value(hasItem(DEFAULT_TOP_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].topImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_TOP_IMAGE))))
            .andExpect(jsonPath("$.[*].useMainImage").value(hasItem(DEFAULT_USE_MAIN_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].mainImageContentType").value(hasItem(DEFAULT_MAIN_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].mainImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_MAIN_IMAGE))))
            .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME.toString())));
    }

    @Test
    @Transactional
    void getOther() throws Exception {
        // Initialize the database
        otherRepository.saveAndFlush(other);

        // Get the other
        restOtherMockMvc
            .perform(get(ENTITY_API_URL_ID, other.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(other.getId().intValue()))
            .andExpect(jsonPath("$.useTopImage").value(DEFAULT_USE_TOP_IMAGE.booleanValue()))
            .andExpect(jsonPath("$.topImageContentType").value(DEFAULT_TOP_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.topImage").value(Base64Utils.encodeToString(DEFAULT_TOP_IMAGE)))
            .andExpect(jsonPath("$.useMainImage").value(DEFAULT_USE_MAIN_IMAGE.booleanValue()))
            .andExpect(jsonPath("$.mainImageContentType").value(DEFAULT_MAIN_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.mainImage").value(Base64Utils.encodeToString(DEFAULT_MAIN_IMAGE)))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOther() throws Exception {
        // Get the other
        restOtherMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOther() throws Exception {
        // Initialize the database
        otherRepository.saveAndFlush(other);

        int databaseSizeBeforeUpdate = otherRepository.findAll().size();

        // Update the other
        Other updatedOther = otherRepository.findById(other.getId()).get();
        // Disconnect from session so that the updates on updatedOther are not directly saved in db
        em.detach(updatedOther);
        updatedOther
            .useTopImage(UPDATED_USE_TOP_IMAGE)
            .topImage(UPDATED_TOP_IMAGE)
            .topImageContentType(UPDATED_TOP_IMAGE_CONTENT_TYPE)
            .useMainImage(UPDATED_USE_MAIN_IMAGE)
            .mainImage(UPDATED_MAIN_IMAGE)
            .mainImageContentType(UPDATED_MAIN_IMAGE_CONTENT_TYPE)
            .theme(UPDATED_THEME);

        restOtherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOther.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOther))
            )
            .andExpect(status().isOk());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeUpdate);
        Other testOther = otherList.get(otherList.size() - 1);
        assertThat(testOther.getUseTopImage()).isEqualTo(UPDATED_USE_TOP_IMAGE);
        assertThat(testOther.getTopImage()).isEqualTo(UPDATED_TOP_IMAGE);
        assertThat(testOther.getTopImageContentType()).isEqualTo(UPDATED_TOP_IMAGE_CONTENT_TYPE);
        assertThat(testOther.getUseMainImage()).isEqualTo(UPDATED_USE_MAIN_IMAGE);
        assertThat(testOther.getMainImage()).isEqualTo(UPDATED_MAIN_IMAGE);
        assertThat(testOther.getMainImageContentType()).isEqualTo(UPDATED_MAIN_IMAGE_CONTENT_TYPE);
        assertThat(testOther.getTheme()).isEqualTo(UPDATED_THEME);
    }

    @Test
    @Transactional
    void putNonExistingOther() throws Exception {
        int databaseSizeBeforeUpdate = otherRepository.findAll().size();
        other.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, other.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(other))
            )
            .andExpect(status().isBadRequest());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOther() throws Exception {
        int databaseSizeBeforeUpdate = otherRepository.findAll().size();
        other.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(other))
            )
            .andExpect(status().isBadRequest());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOther() throws Exception {
        int databaseSizeBeforeUpdate = otherRepository.findAll().size();
        other.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtherMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(other)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOtherWithPatch() throws Exception {
        // Initialize the database
        otherRepository.saveAndFlush(other);

        int databaseSizeBeforeUpdate = otherRepository.findAll().size();

        // Update the other using partial update
        Other partialUpdatedOther = new Other();
        partialUpdatedOther.setId(other.getId());

        partialUpdatedOther
            .useMainImage(UPDATED_USE_MAIN_IMAGE)
            .mainImage(UPDATED_MAIN_IMAGE)
            .mainImageContentType(UPDATED_MAIN_IMAGE_CONTENT_TYPE);

        restOtherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOther.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOther))
            )
            .andExpect(status().isOk());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeUpdate);
        Other testOther = otherList.get(otherList.size() - 1);
        assertThat(testOther.getUseTopImage()).isEqualTo(DEFAULT_USE_TOP_IMAGE);
        assertThat(testOther.getTopImage()).isEqualTo(DEFAULT_TOP_IMAGE);
        assertThat(testOther.getTopImageContentType()).isEqualTo(DEFAULT_TOP_IMAGE_CONTENT_TYPE);
        assertThat(testOther.getUseMainImage()).isEqualTo(UPDATED_USE_MAIN_IMAGE);
        assertThat(testOther.getMainImage()).isEqualTo(UPDATED_MAIN_IMAGE);
        assertThat(testOther.getMainImageContentType()).isEqualTo(UPDATED_MAIN_IMAGE_CONTENT_TYPE);
        assertThat(testOther.getTheme()).isEqualTo(DEFAULT_THEME);
    }

    @Test
    @Transactional
    void fullUpdateOtherWithPatch() throws Exception {
        // Initialize the database
        otherRepository.saveAndFlush(other);

        int databaseSizeBeforeUpdate = otherRepository.findAll().size();

        // Update the other using partial update
        Other partialUpdatedOther = new Other();
        partialUpdatedOther.setId(other.getId());

        partialUpdatedOther
            .useTopImage(UPDATED_USE_TOP_IMAGE)
            .topImage(UPDATED_TOP_IMAGE)
            .topImageContentType(UPDATED_TOP_IMAGE_CONTENT_TYPE)
            .useMainImage(UPDATED_USE_MAIN_IMAGE)
            .mainImage(UPDATED_MAIN_IMAGE)
            .mainImageContentType(UPDATED_MAIN_IMAGE_CONTENT_TYPE)
            .theme(UPDATED_THEME);

        restOtherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOther.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOther))
            )
            .andExpect(status().isOk());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeUpdate);
        Other testOther = otherList.get(otherList.size() - 1);
        assertThat(testOther.getUseTopImage()).isEqualTo(UPDATED_USE_TOP_IMAGE);
        assertThat(testOther.getTopImage()).isEqualTo(UPDATED_TOP_IMAGE);
        assertThat(testOther.getTopImageContentType()).isEqualTo(UPDATED_TOP_IMAGE_CONTENT_TYPE);
        assertThat(testOther.getUseMainImage()).isEqualTo(UPDATED_USE_MAIN_IMAGE);
        assertThat(testOther.getMainImage()).isEqualTo(UPDATED_MAIN_IMAGE);
        assertThat(testOther.getMainImageContentType()).isEqualTo(UPDATED_MAIN_IMAGE_CONTENT_TYPE);
        assertThat(testOther.getTheme()).isEqualTo(UPDATED_THEME);
    }

    @Test
    @Transactional
    void patchNonExistingOther() throws Exception {
        int databaseSizeBeforeUpdate = otherRepository.findAll().size();
        other.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, other.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(other))
            )
            .andExpect(status().isBadRequest());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOther() throws Exception {
        int databaseSizeBeforeUpdate = otherRepository.findAll().size();
        other.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(other))
            )
            .andExpect(status().isBadRequest());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOther() throws Exception {
        int databaseSizeBeforeUpdate = otherRepository.findAll().size();
        other.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtherMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(other)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Other in the database
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOther() throws Exception {
        // Initialize the database
        otherRepository.saveAndFlush(other);

        int databaseSizeBeforeDelete = otherRepository.findAll().size();

        // Delete the other
        restOtherMockMvc
            .perform(delete(ENTITY_API_URL_ID, other.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Other> otherList = otherRepository.findAll();
        assertThat(otherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
