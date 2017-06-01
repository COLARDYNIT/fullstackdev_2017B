package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DeviceInStateService;
import com.mycompany.myapp.domain.DeviceInState;
import com.mycompany.myapp.repository.DeviceInStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DeviceInState.
 */
@Service
@Transactional
public class DeviceInStateServiceImpl implements DeviceInStateService{

    private final Logger log = LoggerFactory.getLogger(DeviceInStateServiceImpl.class);

    private final DeviceInStateRepository deviceInStateRepository;

    public DeviceInStateServiceImpl(DeviceInStateRepository deviceInStateRepository) {
        this.deviceInStateRepository = deviceInStateRepository;
    }

    /**
     * Save a deviceInState.
     *
     * @param deviceInState the entity to save
     * @return the persisted entity
     */
    @Override
    public DeviceInState save(DeviceInState deviceInState) {
        log.debug("Request to save DeviceInState : {}", deviceInState);
        return deviceInStateRepository.save(deviceInState);
    }

    /**
     *  Get all the deviceInStates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceInState> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceInStates");
        return deviceInStateRepository.findAll(pageable);
    }

    /**
     *  Get one deviceInState by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DeviceInState findOne(Long id) {
        log.debug("Request to get DeviceInState : {}", id);
        return deviceInStateRepository.findOne(id);
    }

    /**
     *  Delete the  deviceInState by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceInState : {}", id);
        deviceInStateRepository.delete(id);
    }
}
