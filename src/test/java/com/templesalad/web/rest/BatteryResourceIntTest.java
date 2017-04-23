package com.templesalad.web.rest;

import com.templesalad.BubbleBattStoreApp;

import com.templesalad.domain.Battery;
import com.templesalad.repository.BatteryRepository;
import com.templesalad.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.templesalad.domain.enumeration.Vehicle;
/**
 * Test class for the BatteryResource REST controller.
 *
 * @see BatteryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BubbleBattStoreApp.class)
public class BatteryResourceIntTest {

    private static final Vehicle DEFAULT_TYPE = Vehicle.MOTORBIKE;
    private static final Vehicle UPDATED_TYPE = Vehicle.CAR;

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private BatteryRepository batteryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBatteryMockMvc;

    private Battery battery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BatteryResource batteryResource = new BatteryResource(batteryRepository);
        this.restBatteryMockMvc = MockMvcBuilders.standaloneSetup(batteryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Battery createEntity(EntityManager em) {
        Battery battery = new Battery()
            .type(DEFAULT_TYPE)
            .model(DEFAULT_MODEL)
            .price(DEFAULT_PRICE)
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return battery;
    }

    @Before
    public void initTest() {
        battery = createEntity(em);
    }

    @Test
    @Transactional
    public void createBattery() throws Exception {
        int databaseSizeBeforeCreate = batteryRepository.findAll().size();

        // Create the Battery
        restBatteryMockMvc.perform(post("/api/batteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battery)))
            .andExpect(status().isCreated());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeCreate + 1);
        Battery testBattery = batteryList.get(batteryList.size() - 1);
        assertThat(testBattery.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBattery.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testBattery.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBattery.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBattery.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testBattery.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBatteryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = batteryRepository.findAll().size();

        // Create the Battery with an existing ID
        battery.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatteryMockMvc.perform(post("/api/batteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battery)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = batteryRepository.findAll().size();
        // set the field null
        battery.setType(null);

        // Create the Battery, which fails.

        restBatteryMockMvc.perform(post("/api/batteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battery)))
            .andExpect(status().isBadRequest());

        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = batteryRepository.findAll().size();
        // set the field null
        battery.setModel(null);

        // Create the Battery, which fails.

        restBatteryMockMvc.perform(post("/api/batteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battery)))
            .andExpect(status().isBadRequest());

        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = batteryRepository.findAll().size();
        // set the field null
        battery.setPrice(null);

        // Create the Battery, which fails.

        restBatteryMockMvc.perform(post("/api/batteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battery)))
            .andExpect(status().isBadRequest());

        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = batteryRepository.findAll().size();
        // set the field null
        battery.setName(null);

        // Create the Battery, which fails.

        restBatteryMockMvc.perform(post("/api/batteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battery)))
            .andExpect(status().isBadRequest());

        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBatteries() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);

        // Get all the batteryList
        restBatteryMockMvc.perform(get("/api/batteries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(battery.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getBattery() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);

        // Get the battery
        restBatteryMockMvc.perform(get("/api/batteries/{id}", battery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(battery.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingBattery() throws Exception {
        // Get the battery
        restBatteryMockMvc.perform(get("/api/batteries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBattery() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);
        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();

        // Update the battery
        Battery updatedBattery = batteryRepository.findOne(battery.getId());
        updatedBattery
            .type(UPDATED_TYPE)
            .model(UPDATED_MODEL)
            .price(UPDATED_PRICE)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restBatteryMockMvc.perform(put("/api/batteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBattery)))
            .andExpect(status().isOk());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
        Battery testBattery = batteryList.get(batteryList.size() - 1);
        assertThat(testBattery.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBattery.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testBattery.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBattery.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBattery.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testBattery.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBattery() throws Exception {
        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();

        // Create the Battery

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBatteryMockMvc.perform(put("/api/batteries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battery)))
            .andExpect(status().isCreated());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBattery() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);
        int databaseSizeBeforeDelete = batteryRepository.findAll().size();

        // Get the battery
        restBatteryMockMvc.perform(delete("/api/batteries/{id}", battery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Battery.class);
    }
}
