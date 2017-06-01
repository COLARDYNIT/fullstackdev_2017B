package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DeviceInState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DeviceInState.
 */
public interface DeviceInStateService {

    /**
     * Save a deviceInState.
     *
     * @param deviceInState the entity to save
     * @return the persisted entity
     */
    DeviceInState save(DeviceInState deviceInState);

    /**
     *  Get all the deviceInStates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DeviceInState> findAll(Pageable pageable);

    /**
     *  Get the "id" deviceInState.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DeviceInState findOne(Long id);

    /**
     *  Delete the "id" deviceInState.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
