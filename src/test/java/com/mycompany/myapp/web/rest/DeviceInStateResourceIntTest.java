package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Fullstackdev2017BApp;

import com.mycompany.myapp.domain.DeviceInState;
import com.mycompany.myapp.repository.DeviceInStateRepository;
import com.mycompany.myapp.service.DeviceInStateService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeviceInStateResource REST controller.
 *
 * @see DeviceInStateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fullstackdev2017BApp.class)
public class DeviceInStateResourceIntTest {

    private static final Boolean DEFAULT_STATE = false;
    private static final Boolean UPDATED_STATE = true;

    @Autowired
    private DeviceInStateRepository deviceInStateRepository;

    @Autowired
    private DeviceInStateService deviceInStateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeviceInStateMockMvc;

    private DeviceInState deviceInState;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceInStateResource deviceInStateResource = new DeviceInStateResource(deviceInStateService);
        this.restDeviceInStateMockMvc = MockMvcBuilders.standaloneSetup(deviceInStateResource)
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
    public static DeviceInState createEntity(EntityManager em) {
        DeviceInState deviceInState = new DeviceInState()
            .state(DEFAULT_STATE);
        return deviceInState;
    }

    @Before
    public void initTest() {
        deviceInState = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceInState() throws Exception {
        int databaseSizeBeforeCreate = deviceInStateRepository.findAll().size();

        // Create the DeviceInState
        restDeviceInStateMockMvc.perform(post("/api/device-in-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInState)))
            .andExpect(status().isCreated());

        // Validate the DeviceInState in the database
        List<DeviceInState> deviceInStateList = deviceInStateRepository.findAll();
        assertThat(deviceInStateList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceInState testDeviceInState = deviceInStateList.get(deviceInStateList.size() - 1);
        assertThat(testDeviceInState.isState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createDeviceInStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceInStateRepository.findAll().size();

        // Create the DeviceInState with an existing ID
        deviceInState.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceInStateMockMvc.perform(post("/api/device-in-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInState)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DeviceInState> deviceInStateList = deviceInStateRepository.findAll();
        assertThat(deviceInStateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeviceInStates() throws Exception {
        // Initialize the database
        deviceInStateRepository.saveAndFlush(deviceInState);

        // Get all the deviceInStateList
        restDeviceInStateMockMvc.perform(get("/api/device-in-states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceInState.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.booleanValue())));
    }

    @Test
    @Transactional
    public void getDeviceInState() throws Exception {
        // Initialize the database
        deviceInStateRepository.saveAndFlush(deviceInState);

        // Get the deviceInState
        restDeviceInStateMockMvc.perform(get("/api/device-in-states/{id}", deviceInState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceInState.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDeviceInState() throws Exception {
        // Get the deviceInState
        restDeviceInStateMockMvc.perform(get("/api/device-in-states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceInState() throws Exception {
        // Initialize the database
        deviceInStateService.save(deviceInState);

        int databaseSizeBeforeUpdate = deviceInStateRepository.findAll().size();

        // Update the deviceInState
        DeviceInState updatedDeviceInState = deviceInStateRepository.findOne(deviceInState.getId());
        updatedDeviceInState
            .state(UPDATED_STATE);

        restDeviceInStateMockMvc.perform(put("/api/device-in-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeviceInState)))
            .andExpect(status().isOk());

        // Validate the DeviceInState in the database
        List<DeviceInState> deviceInStateList = deviceInStateRepository.findAll();
        assertThat(deviceInStateList).hasSize(databaseSizeBeforeUpdate);
        DeviceInState testDeviceInState = deviceInStateList.get(deviceInStateList.size() - 1);
        assertThat(testDeviceInState.isState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceInState() throws Exception {
        int databaseSizeBeforeUpdate = deviceInStateRepository.findAll().size();

        // Create the DeviceInState

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeviceInStateMockMvc.perform(put("/api/device-in-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInState)))
            .andExpect(status().isCreated());

        // Validate the DeviceInState in the database
        List<DeviceInState> deviceInStateList = deviceInStateRepository.findAll();
        assertThat(deviceInStateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeviceInState() throws Exception {
        // Initialize the database
        deviceInStateService.save(deviceInState);

        int databaseSizeBeforeDelete = deviceInStateRepository.findAll().size();

        // Get the deviceInState
        restDeviceInStateMockMvc.perform(delete("/api/device-in-states/{id}", deviceInState.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeviceInState> deviceInStateList = deviceInStateRepository.findAll();
        assertThat(deviceInStateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceInState.class);
        DeviceInState deviceInState1 = new DeviceInState();
        deviceInState1.setId(1L);
        DeviceInState deviceInState2 = new DeviceInState();
        deviceInState2.setId(deviceInState1.getId());
        assertThat(deviceInState1).isEqualTo(deviceInState2);
        deviceInState2.setId(2L);
        assertThat(deviceInState1).isNotEqualTo(deviceInState2);
        deviceInState1.setId(null);
        assertThat(deviceInState1).isNotEqualTo(deviceInState2);
    }
}
