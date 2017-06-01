package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DeviceInState;
import com.mycompany.myapp.service.DeviceInStateService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DeviceInState.
 */
@RestController
@RequestMapping("/api")
public class DeviceInStateResource {

    private final Logger log = LoggerFactory.getLogger(DeviceInStateResource.class);

    private static final String ENTITY_NAME = "deviceInState";

    private final DeviceInStateService deviceInStateService;

    public DeviceInStateResource(DeviceInStateService deviceInStateService) {
        this.deviceInStateService = deviceInStateService;
    }

    /**
     * POST  /device-in-states : Create a new deviceInState.
     *
     * @param deviceInState the deviceInState to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deviceInState, or with status 400 (Bad Request) if the deviceInState has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/device-in-states")
    @Timed
    public ResponseEntity<DeviceInState> createDeviceInState(@RequestBody DeviceInState deviceInState) throws URISyntaxException {
        log.debug("REST request to save DeviceInState : {}", deviceInState);
        if (deviceInState.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deviceInState cannot already have an ID")).body(null);
        }
        DeviceInState result = deviceInStateService.save(deviceInState);
        return ResponseEntity.created(new URI("/api/device-in-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /device-in-states : Updates an existing deviceInState.
     *
     * @param deviceInState the deviceInState to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deviceInState,
     * or with status 400 (Bad Request) if the deviceInState is not valid,
     * or with status 500 (Internal Server Error) if the deviceInState couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/device-in-states")
    @Timed
    public ResponseEntity<DeviceInState> updateDeviceInState(@RequestBody DeviceInState deviceInState) throws URISyntaxException {
        log.debug("REST request to update DeviceInState : {}", deviceInState);
        if (deviceInState.getId() == null) {
            return createDeviceInState(deviceInState);
        }
        DeviceInState result = deviceInStateService.save(deviceInState);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deviceInState.getId().toString()))
            .body(result);
    }

    /**
     * GET  /device-in-states : get all the deviceInStates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deviceInStates in body
     */
    @GetMapping("/device-in-states")
    @Timed
    public ResponseEntity<List<DeviceInState>> getAllDeviceInStates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DeviceInStates");
        Page<DeviceInState> page = deviceInStateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/device-in-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /device-in-states/:id : get the "id" deviceInState.
     *
     * @param id the id of the deviceInState to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceInState, or with status 404 (Not Found)
     */
    @GetMapping("/device-in-states/{id}")
    @Timed
    public ResponseEntity<DeviceInState> getDeviceInState(@PathVariable Long id) {
        log.debug("REST request to get DeviceInState : {}", id);
        DeviceInState deviceInState = deviceInStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deviceInState));
    }

    /**
     * DELETE  /device-in-states/:id : delete the "id" deviceInState.
     *
     * @param id the id of the deviceInState to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/device-in-states/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeviceInState(@PathVariable Long id) {
        log.debug("REST request to delete DeviceInState : {}", id);
        deviceInStateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
