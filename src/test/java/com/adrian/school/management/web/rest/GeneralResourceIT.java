package com.adrian.school.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adrian.school.management.IntegrationTest;
import com.adrian.school.management.domain.General;
import com.adrian.school.management.repository.GeneralRepository;
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
 * Integration tests for the {@link GeneralResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeneralResourceIT {

    private static final String DEFAULT_SITE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SITE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_PAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCES_PAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCES_PAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STAFF_PAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_PAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT_PAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT_PAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOTTO = "AAAAAAAAAA";
    private static final String UPDATED_MOTTO = "BBBBBBBBBB";

    private static final String DEFAULT_STRUCTURE_1 = "AAAAAAAAAA";
    private static final String UPDATED_STRUCTURE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_STRUCTURE_2 = "AAAAAAAAAA";
    private static final String UPDATED_STRUCTURE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_STRUCTURE_3 = "AAAAAAAAAA";
    private static final String UPDATED_STRUCTURE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_STRUCTURE_4 = "AAAAAAAAAA";
    private static final String UPDATED_STRUCTURE_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_HEADER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_HEADER = "BBBBBBBBBB";

    private static final String DEFAULT_STRUCTURES_HEADER = "AAAAAAAAAA";
    private static final String UPDATED_STRUCTURES_HEADER = "BBBBBBBBBB";

    private static final String DEFAULT_MAP_URL = "AAAAAAAAAA";
    private static final String UPDATED_MAP_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/generals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GeneralRepository generalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeneralMockMvc;

    private General general;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static General createEntity(EntityManager em) {
        General general = new General()
            .siteName(DEFAULT_SITE_NAME)
            .homePageName(DEFAULT_HOME_PAGE_NAME)
            .resourcesPageName(DEFAULT_RESOURCES_PAGE_NAME)
            .staffPageName(DEFAULT_STAFF_PAGE_NAME)
            .aboutPageName(DEFAULT_ABOUT_PAGE_NAME)
            .facebookAddress(DEFAULT_FACEBOOK_ADDRESS)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .motto(DEFAULT_MOTTO)
            .structure1(DEFAULT_STRUCTURE_1)
            .structure2(DEFAULT_STRUCTURE_2)
            .structure3(DEFAULT_STRUCTURE_3)
            .structure4(DEFAULT_STRUCTURE_4)
            .contactHeader(DEFAULT_CONTACT_HEADER)
            .structuresHeader(DEFAULT_STRUCTURES_HEADER)
            .mapUrl(DEFAULT_MAP_URL);
        return general;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static General createUpdatedEntity(EntityManager em) {
        General general = new General()
            .siteName(UPDATED_SITE_NAME)
            .homePageName(UPDATED_HOME_PAGE_NAME)
            .resourcesPageName(UPDATED_RESOURCES_PAGE_NAME)
            .staffPageName(UPDATED_STAFF_PAGE_NAME)
            .aboutPageName(UPDATED_ABOUT_PAGE_NAME)
            .facebookAddress(UPDATED_FACEBOOK_ADDRESS)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .motto(UPDATED_MOTTO)
            .structure1(UPDATED_STRUCTURE_1)
            .structure2(UPDATED_STRUCTURE_2)
            .structure3(UPDATED_STRUCTURE_3)
            .structure4(UPDATED_STRUCTURE_4)
            .contactHeader(UPDATED_CONTACT_HEADER)
            .structuresHeader(UPDATED_STRUCTURES_HEADER)
            .mapUrl(UPDATED_MAP_URL);
        return general;
    }

    @BeforeEach
    public void initTest() {
        general = createEntity(em);
    }

    @Test
    @Transactional
    void createGeneral() throws Exception {
        int databaseSizeBeforeCreate = generalRepository.findAll().size();
        // Create the General
        restGeneralMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(general)))
            .andExpect(status().isCreated());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeCreate + 1);
        General testGeneral = generalList.get(generalList.size() - 1);
        assertThat(testGeneral.getSiteName()).isEqualTo(DEFAULT_SITE_NAME);
        assertThat(testGeneral.getHomePageName()).isEqualTo(DEFAULT_HOME_PAGE_NAME);
        assertThat(testGeneral.getResourcesPageName()).isEqualTo(DEFAULT_RESOURCES_PAGE_NAME);
        assertThat(testGeneral.getStaffPageName()).isEqualTo(DEFAULT_STAFF_PAGE_NAME);
        assertThat(testGeneral.getAboutPageName()).isEqualTo(DEFAULT_ABOUT_PAGE_NAME);
        assertThat(testGeneral.getFacebookAddress()).isEqualTo(DEFAULT_FACEBOOK_ADDRESS);
        assertThat(testGeneral.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testGeneral.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testGeneral.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGeneral.getMotto()).isEqualTo(DEFAULT_MOTTO);
        assertThat(testGeneral.getStructure1()).isEqualTo(DEFAULT_STRUCTURE_1);
        assertThat(testGeneral.getStructure2()).isEqualTo(DEFAULT_STRUCTURE_2);
        assertThat(testGeneral.getStructure3()).isEqualTo(DEFAULT_STRUCTURE_3);
        assertThat(testGeneral.getStructure4()).isEqualTo(DEFAULT_STRUCTURE_4);
        assertThat(testGeneral.getContactHeader()).isEqualTo(DEFAULT_CONTACT_HEADER);
        assertThat(testGeneral.getStructuresHeader()).isEqualTo(DEFAULT_STRUCTURES_HEADER);
        assertThat(testGeneral.getMapUrl()).isEqualTo(DEFAULT_MAP_URL);
    }

    @Test
    @Transactional
    void createGeneralWithExistingId() throws Exception {
        // Create the General with an existing ID
        general.setId(1L);

        int databaseSizeBeforeCreate = generalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneralMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(general)))
            .andExpect(status().isBadRequest());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSiteNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = generalRepository.findAll().size();
        // set the field null
        general.setSiteName(null);

        // Create the General, which fails.

        restGeneralMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(general)))
            .andExpect(status().isBadRequest());

        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGenerals() throws Exception {
        // Initialize the database
        generalRepository.saveAndFlush(general);

        // Get all the generalList
        restGeneralMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(general.getId().intValue())))
            .andExpect(jsonPath("$.[*].siteName").value(hasItem(DEFAULT_SITE_NAME)))
            .andExpect(jsonPath("$.[*].homePageName").value(hasItem(DEFAULT_HOME_PAGE_NAME)))
            .andExpect(jsonPath("$.[*].resourcesPageName").value(hasItem(DEFAULT_RESOURCES_PAGE_NAME)))
            .andExpect(jsonPath("$.[*].staffPageName").value(hasItem(DEFAULT_STAFF_PAGE_NAME)))
            .andExpect(jsonPath("$.[*].aboutPageName").value(hasItem(DEFAULT_ABOUT_PAGE_NAME)))
            .andExpect(jsonPath("$.[*].facebookAddress").value(hasItem(DEFAULT_FACEBOOK_ADDRESS)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].motto").value(hasItem(DEFAULT_MOTTO)))
            .andExpect(jsonPath("$.[*].structure1").value(hasItem(DEFAULT_STRUCTURE_1)))
            .andExpect(jsonPath("$.[*].structure2").value(hasItem(DEFAULT_STRUCTURE_2)))
            .andExpect(jsonPath("$.[*].structure3").value(hasItem(DEFAULT_STRUCTURE_3)))
            .andExpect(jsonPath("$.[*].structure4").value(hasItem(DEFAULT_STRUCTURE_4)))
            .andExpect(jsonPath("$.[*].contactHeader").value(hasItem(DEFAULT_CONTACT_HEADER)))
            .andExpect(jsonPath("$.[*].structuresHeader").value(hasItem(DEFAULT_STRUCTURES_HEADER)))
            .andExpect(jsonPath("$.[*].mapUrl").value(hasItem(DEFAULT_MAP_URL)));
    }

    @Test
    @Transactional
    void getGeneral() throws Exception {
        // Initialize the database
        generalRepository.saveAndFlush(general);

        // Get the general
        restGeneralMockMvc
            .perform(get(ENTITY_API_URL_ID, general.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(general.getId().intValue()))
            .andExpect(jsonPath("$.siteName").value(DEFAULT_SITE_NAME))
            .andExpect(jsonPath("$.homePageName").value(DEFAULT_HOME_PAGE_NAME))
            .andExpect(jsonPath("$.resourcesPageName").value(DEFAULT_RESOURCES_PAGE_NAME))
            .andExpect(jsonPath("$.staffPageName").value(DEFAULT_STAFF_PAGE_NAME))
            .andExpect(jsonPath("$.aboutPageName").value(DEFAULT_ABOUT_PAGE_NAME))
            .andExpect(jsonPath("$.facebookAddress").value(DEFAULT_FACEBOOK_ADDRESS))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.motto").value(DEFAULT_MOTTO))
            .andExpect(jsonPath("$.structure1").value(DEFAULT_STRUCTURE_1))
            .andExpect(jsonPath("$.structure2").value(DEFAULT_STRUCTURE_2))
            .andExpect(jsonPath("$.structure3").value(DEFAULT_STRUCTURE_3))
            .andExpect(jsonPath("$.structure4").value(DEFAULT_STRUCTURE_4))
            .andExpect(jsonPath("$.contactHeader").value(DEFAULT_CONTACT_HEADER))
            .andExpect(jsonPath("$.structuresHeader").value(DEFAULT_STRUCTURES_HEADER))
            .andExpect(jsonPath("$.mapUrl").value(DEFAULT_MAP_URL));
    }

    @Test
    @Transactional
    void getNonExistingGeneral() throws Exception {
        // Get the general
        restGeneralMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGeneral() throws Exception {
        // Initialize the database
        generalRepository.saveAndFlush(general);

        int databaseSizeBeforeUpdate = generalRepository.findAll().size();

        // Update the general
        General updatedGeneral = generalRepository.findById(general.getId()).get();
        // Disconnect from session so that the updates on updatedGeneral are not directly saved in db
        em.detach(updatedGeneral);
        updatedGeneral
            .siteName(UPDATED_SITE_NAME)
            .homePageName(UPDATED_HOME_PAGE_NAME)
            .resourcesPageName(UPDATED_RESOURCES_PAGE_NAME)
            .staffPageName(UPDATED_STAFF_PAGE_NAME)
            .aboutPageName(UPDATED_ABOUT_PAGE_NAME)
            .facebookAddress(UPDATED_FACEBOOK_ADDRESS)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .motto(UPDATED_MOTTO)
            .structure1(UPDATED_STRUCTURE_1)
            .structure2(UPDATED_STRUCTURE_2)
            .structure3(UPDATED_STRUCTURE_3)
            .structure4(UPDATED_STRUCTURE_4)
            .contactHeader(UPDATED_CONTACT_HEADER)
            .structuresHeader(UPDATED_STRUCTURES_HEADER)
            .mapUrl(UPDATED_MAP_URL);

        restGeneralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGeneral.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGeneral))
            )
            .andExpect(status().isOk());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeUpdate);
        General testGeneral = generalList.get(generalList.size() - 1);
        assertThat(testGeneral.getSiteName()).isEqualTo(UPDATED_SITE_NAME);
        assertThat(testGeneral.getHomePageName()).isEqualTo(UPDATED_HOME_PAGE_NAME);
        assertThat(testGeneral.getResourcesPageName()).isEqualTo(UPDATED_RESOURCES_PAGE_NAME);
        assertThat(testGeneral.getStaffPageName()).isEqualTo(UPDATED_STAFF_PAGE_NAME);
        assertThat(testGeneral.getAboutPageName()).isEqualTo(UPDATED_ABOUT_PAGE_NAME);
        assertThat(testGeneral.getFacebookAddress()).isEqualTo(UPDATED_FACEBOOK_ADDRESS);
        assertThat(testGeneral.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testGeneral.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testGeneral.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGeneral.getMotto()).isEqualTo(UPDATED_MOTTO);
        assertThat(testGeneral.getStructure1()).isEqualTo(UPDATED_STRUCTURE_1);
        assertThat(testGeneral.getStructure2()).isEqualTo(UPDATED_STRUCTURE_2);
        assertThat(testGeneral.getStructure3()).isEqualTo(UPDATED_STRUCTURE_3);
        assertThat(testGeneral.getStructure4()).isEqualTo(UPDATED_STRUCTURE_4);
        assertThat(testGeneral.getContactHeader()).isEqualTo(UPDATED_CONTACT_HEADER);
        assertThat(testGeneral.getStructuresHeader()).isEqualTo(UPDATED_STRUCTURES_HEADER);
        assertThat(testGeneral.getMapUrl()).isEqualTo(UPDATED_MAP_URL);
    }

    @Test
    @Transactional
    void putNonExistingGeneral() throws Exception {
        int databaseSizeBeforeUpdate = generalRepository.findAll().size();
        general.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, general.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(general))
            )
            .andExpect(status().isBadRequest());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeneral() throws Exception {
        int databaseSizeBeforeUpdate = generalRepository.findAll().size();
        general.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(general))
            )
            .andExpect(status().isBadRequest());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeneral() throws Exception {
        int databaseSizeBeforeUpdate = generalRepository.findAll().size();
        general.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(general)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeneralWithPatch() throws Exception {
        // Initialize the database
        generalRepository.saveAndFlush(general);

        int databaseSizeBeforeUpdate = generalRepository.findAll().size();

        // Update the general using partial update
        General partialUpdatedGeneral = new General();
        partialUpdatedGeneral.setId(general.getId());

        partialUpdatedGeneral
            .siteName(UPDATED_SITE_NAME)
            .resourcesPageName(UPDATED_RESOURCES_PAGE_NAME)
            .staffPageName(UPDATED_STAFF_PAGE_NAME)
            .aboutPageName(UPDATED_ABOUT_PAGE_NAME)
            .email(UPDATED_EMAIL)
            .motto(UPDATED_MOTTO)
            .structure2(UPDATED_STRUCTURE_2)
            .structure3(UPDATED_STRUCTURE_3)
            .structure4(UPDATED_STRUCTURE_4)
            .structuresHeader(UPDATED_STRUCTURES_HEADER);

        restGeneralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeneral))
            )
            .andExpect(status().isOk());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeUpdate);
        General testGeneral = generalList.get(generalList.size() - 1);
        assertThat(testGeneral.getSiteName()).isEqualTo(UPDATED_SITE_NAME);
        assertThat(testGeneral.getHomePageName()).isEqualTo(DEFAULT_HOME_PAGE_NAME);
        assertThat(testGeneral.getResourcesPageName()).isEqualTo(UPDATED_RESOURCES_PAGE_NAME);
        assertThat(testGeneral.getStaffPageName()).isEqualTo(UPDATED_STAFF_PAGE_NAME);
        assertThat(testGeneral.getAboutPageName()).isEqualTo(UPDATED_ABOUT_PAGE_NAME);
        assertThat(testGeneral.getFacebookAddress()).isEqualTo(DEFAULT_FACEBOOK_ADDRESS);
        assertThat(testGeneral.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testGeneral.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testGeneral.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGeneral.getMotto()).isEqualTo(UPDATED_MOTTO);
        assertThat(testGeneral.getStructure1()).isEqualTo(DEFAULT_STRUCTURE_1);
        assertThat(testGeneral.getStructure2()).isEqualTo(UPDATED_STRUCTURE_2);
        assertThat(testGeneral.getStructure3()).isEqualTo(UPDATED_STRUCTURE_3);
        assertThat(testGeneral.getStructure4()).isEqualTo(UPDATED_STRUCTURE_4);
        assertThat(testGeneral.getContactHeader()).isEqualTo(DEFAULT_CONTACT_HEADER);
        assertThat(testGeneral.getStructuresHeader()).isEqualTo(UPDATED_STRUCTURES_HEADER);
        assertThat(testGeneral.getMapUrl()).isEqualTo(DEFAULT_MAP_URL);
    }

    @Test
    @Transactional
    void fullUpdateGeneralWithPatch() throws Exception {
        // Initialize the database
        generalRepository.saveAndFlush(general);

        int databaseSizeBeforeUpdate = generalRepository.findAll().size();

        // Update the general using partial update
        General partialUpdatedGeneral = new General();
        partialUpdatedGeneral.setId(general.getId());

        partialUpdatedGeneral
            .siteName(UPDATED_SITE_NAME)
            .homePageName(UPDATED_HOME_PAGE_NAME)
            .resourcesPageName(UPDATED_RESOURCES_PAGE_NAME)
            .staffPageName(UPDATED_STAFF_PAGE_NAME)
            .aboutPageName(UPDATED_ABOUT_PAGE_NAME)
            .facebookAddress(UPDATED_FACEBOOK_ADDRESS)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .motto(UPDATED_MOTTO)
            .structure1(UPDATED_STRUCTURE_1)
            .structure2(UPDATED_STRUCTURE_2)
            .structure3(UPDATED_STRUCTURE_3)
            .structure4(UPDATED_STRUCTURE_4)
            .contactHeader(UPDATED_CONTACT_HEADER)
            .structuresHeader(UPDATED_STRUCTURES_HEADER)
            .mapUrl(UPDATED_MAP_URL);

        restGeneralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeneral))
            )
            .andExpect(status().isOk());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeUpdate);
        General testGeneral = generalList.get(generalList.size() - 1);
        assertThat(testGeneral.getSiteName()).isEqualTo(UPDATED_SITE_NAME);
        assertThat(testGeneral.getHomePageName()).isEqualTo(UPDATED_HOME_PAGE_NAME);
        assertThat(testGeneral.getResourcesPageName()).isEqualTo(UPDATED_RESOURCES_PAGE_NAME);
        assertThat(testGeneral.getStaffPageName()).isEqualTo(UPDATED_STAFF_PAGE_NAME);
        assertThat(testGeneral.getAboutPageName()).isEqualTo(UPDATED_ABOUT_PAGE_NAME);
        assertThat(testGeneral.getFacebookAddress()).isEqualTo(UPDATED_FACEBOOK_ADDRESS);
        assertThat(testGeneral.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testGeneral.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testGeneral.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGeneral.getMotto()).isEqualTo(UPDATED_MOTTO);
        assertThat(testGeneral.getStructure1()).isEqualTo(UPDATED_STRUCTURE_1);
        assertThat(testGeneral.getStructure2()).isEqualTo(UPDATED_STRUCTURE_2);
        assertThat(testGeneral.getStructure3()).isEqualTo(UPDATED_STRUCTURE_3);
        assertThat(testGeneral.getStructure4()).isEqualTo(UPDATED_STRUCTURE_4);
        assertThat(testGeneral.getContactHeader()).isEqualTo(UPDATED_CONTACT_HEADER);
        assertThat(testGeneral.getStructuresHeader()).isEqualTo(UPDATED_STRUCTURES_HEADER);
        assertThat(testGeneral.getMapUrl()).isEqualTo(UPDATED_MAP_URL);
    }

    @Test
    @Transactional
    void patchNonExistingGeneral() throws Exception {
        int databaseSizeBeforeUpdate = generalRepository.findAll().size();
        general.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, general.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(general))
            )
            .andExpect(status().isBadRequest());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeneral() throws Exception {
        int databaseSizeBeforeUpdate = generalRepository.findAll().size();
        general.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(general))
            )
            .andExpect(status().isBadRequest());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeneral() throws Exception {
        int databaseSizeBeforeUpdate = generalRepository.findAll().size();
        general.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(general)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the General in the database
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeneral() throws Exception {
        // Initialize the database
        generalRepository.saveAndFlush(general);

        int databaseSizeBeforeDelete = generalRepository.findAll().size();

        // Delete the general
        restGeneralMockMvc
            .perform(delete(ENTITY_API_URL_ID, general.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<General> generalList = generalRepository.findAll();
        assertThat(generalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
