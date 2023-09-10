package com.adrian.school.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adrian.school.management.IntegrationTest;
import com.adrian.school.management.domain.StaffData;
import com.adrian.school.management.repository.StaffDataRepository;
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
 * Integration tests for the {@link StaffDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StaffDataResourceIT {

    private static final Integer DEFAULT_ORDER_STAFF = 1;
    private static final Integer UPDATED_ORDER_STAFF = 2;

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/staff-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StaffDataRepository staffDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaffDataMockMvc;

    private StaffData staffData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaffData createEntity(EntityManager em) {
        StaffData staffData = new StaffData()
            .orderStaff(DEFAULT_ORDER_STAFF)
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .jobType(DEFAULT_JOB_TYPE)
            .unitName(DEFAULT_UNIT_NAME);
        return staffData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaffData createUpdatedEntity(EntityManager em) {
        StaffData staffData = new StaffData()
            .orderStaff(UPDATED_ORDER_STAFF)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .jobType(UPDATED_JOB_TYPE)
            .unitName(UPDATED_UNIT_NAME);
        return staffData;
    }

    @BeforeEach
    public void initTest() {
        staffData = createEntity(em);
    }

    @Test
    @Transactional
    void createStaffData() throws Exception {
        int databaseSizeBeforeCreate = staffDataRepository.findAll().size();
        // Create the StaffData
        restStaffDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffData)))
            .andExpect(status().isCreated());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeCreate + 1);
        StaffData testStaffData = staffDataList.get(staffDataList.size() - 1);
        assertThat(testStaffData.getOrderStaff()).isEqualTo(DEFAULT_ORDER_STAFF);
        assertThat(testStaffData.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testStaffData.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStaffData.getJobType()).isEqualTo(DEFAULT_JOB_TYPE);
        assertThat(testStaffData.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
    }

    @Test
    @Transactional
    void createStaffDataWithExistingId() throws Exception {
        // Create the StaffData with an existing ID
        staffData.setId(1L);

        int databaseSizeBeforeCreate = staffDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffData)))
            .andExpect(status().isBadRequest());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOrderStaffIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffDataRepository.findAll().size();
        // set the field null
        staffData.setOrderStaff(null);

        // Create the StaffData, which fails.

        restStaffDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffData)))
            .andExpect(status().isBadRequest());

        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffDataRepository.findAll().size();
        // set the field null
        staffData.setLastName(null);

        // Create the StaffData, which fails.

        restStaffDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffData)))
            .andExpect(status().isBadRequest());

        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffDataRepository.findAll().size();
        // set the field null
        staffData.setFirstName(null);

        // Create the StaffData, which fails.

        restStaffDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffData)))
            .andExpect(status().isBadRequest());

        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJobTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffDataRepository.findAll().size();
        // set the field null
        staffData.setJobType(null);

        // Create the StaffData, which fails.

        restStaffDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffData)))
            .andExpect(status().isBadRequest());

        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffDataRepository.findAll().size();
        // set the field null
        staffData.setUnitName(null);

        // Create the StaffData, which fails.

        restStaffDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffData)))
            .andExpect(status().isBadRequest());

        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStaffData() throws Exception {
        // Initialize the database
        staffDataRepository.saveAndFlush(staffData);

        // Get all the staffDataList
        restStaffDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staffData.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderStaff").value(hasItem(DEFAULT_ORDER_STAFF)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].jobType").value(hasItem(DEFAULT_JOB_TYPE)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)));
    }

    @Test
    @Transactional
    void getStaffData() throws Exception {
        // Initialize the database
        staffDataRepository.saveAndFlush(staffData);

        // Get the staffData
        restStaffDataMockMvc
            .perform(get(ENTITY_API_URL_ID, staffData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staffData.getId().intValue()))
            .andExpect(jsonPath("$.orderStaff").value(DEFAULT_ORDER_STAFF))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.jobType").value(DEFAULT_JOB_TYPE))
            .andExpect(jsonPath("$.unitName").value(DEFAULT_UNIT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingStaffData() throws Exception {
        // Get the staffData
        restStaffDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStaffData() throws Exception {
        // Initialize the database
        staffDataRepository.saveAndFlush(staffData);

        int databaseSizeBeforeUpdate = staffDataRepository.findAll().size();

        // Update the staffData
        StaffData updatedStaffData = staffDataRepository.findById(staffData.getId()).get();
        // Disconnect from session so that the updates on updatedStaffData are not directly saved in db
        em.detach(updatedStaffData);
        updatedStaffData
            .orderStaff(UPDATED_ORDER_STAFF)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .jobType(UPDATED_JOB_TYPE)
            .unitName(UPDATED_UNIT_NAME);

        restStaffDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStaffData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStaffData))
            )
            .andExpect(status().isOk());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeUpdate);
        StaffData testStaffData = staffDataList.get(staffDataList.size() - 1);
        assertThat(testStaffData.getOrderStaff()).isEqualTo(UPDATED_ORDER_STAFF);
        assertThat(testStaffData.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testStaffData.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStaffData.getJobType()).isEqualTo(UPDATED_JOB_TYPE);
        assertThat(testStaffData.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void putNonExistingStaffData() throws Exception {
        int databaseSizeBeforeUpdate = staffDataRepository.findAll().size();
        staffData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, staffData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffData))
            )
            .andExpect(status().isBadRequest());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStaffData() throws Exception {
        int databaseSizeBeforeUpdate = staffDataRepository.findAll().size();
        staffData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffData))
            )
            .andExpect(status().isBadRequest());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStaffData() throws Exception {
        int databaseSizeBeforeUpdate = staffDataRepository.findAll().size();
        staffData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staffData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStaffDataWithPatch() throws Exception {
        // Initialize the database
        staffDataRepository.saveAndFlush(staffData);

        int databaseSizeBeforeUpdate = staffDataRepository.findAll().size();

        // Update the staffData using partial update
        StaffData partialUpdatedStaffData = new StaffData();
        partialUpdatedStaffData.setId(staffData.getId());

        partialUpdatedStaffData.jobType(UPDATED_JOB_TYPE);

        restStaffDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaffData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaffData))
            )
            .andExpect(status().isOk());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeUpdate);
        StaffData testStaffData = staffDataList.get(staffDataList.size() - 1);
        assertThat(testStaffData.getOrderStaff()).isEqualTo(DEFAULT_ORDER_STAFF);
        assertThat(testStaffData.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testStaffData.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStaffData.getJobType()).isEqualTo(UPDATED_JOB_TYPE);
        assertThat(testStaffData.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateStaffDataWithPatch() throws Exception {
        // Initialize the database
        staffDataRepository.saveAndFlush(staffData);

        int databaseSizeBeforeUpdate = staffDataRepository.findAll().size();

        // Update the staffData using partial update
        StaffData partialUpdatedStaffData = new StaffData();
        partialUpdatedStaffData.setId(staffData.getId());

        partialUpdatedStaffData
            .orderStaff(UPDATED_ORDER_STAFF)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .jobType(UPDATED_JOB_TYPE)
            .unitName(UPDATED_UNIT_NAME);

        restStaffDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaffData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaffData))
            )
            .andExpect(status().isOk());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeUpdate);
        StaffData testStaffData = staffDataList.get(staffDataList.size() - 1);
        assertThat(testStaffData.getOrderStaff()).isEqualTo(UPDATED_ORDER_STAFF);
        assertThat(testStaffData.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testStaffData.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStaffData.getJobType()).isEqualTo(UPDATED_JOB_TYPE);
        assertThat(testStaffData.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingStaffData() throws Exception {
        int databaseSizeBeforeUpdate = staffDataRepository.findAll().size();
        staffData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, staffData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staffData))
            )
            .andExpect(status().isBadRequest());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStaffData() throws Exception {
        int databaseSizeBeforeUpdate = staffDataRepository.findAll().size();
        staffData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staffData))
            )
            .andExpect(status().isBadRequest());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStaffData() throws Exception {
        int databaseSizeBeforeUpdate = staffDataRepository.findAll().size();
        staffData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(staffData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StaffData in the database
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStaffData() throws Exception {
        // Initialize the database
        staffDataRepository.saveAndFlush(staffData);

        int databaseSizeBeforeDelete = staffDataRepository.findAll().size();

        // Delete the staffData
        restStaffDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, staffData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaffData> staffDataList = staffDataRepository.findAll();
        assertThat(staffDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
