package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DeviceService;
import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Device.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService{

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    /**
     * Save a device.
     *
     * @param device the entity to save
     * @return the persisted entity
     */
    @Override
    public Device save(Device device) {
        log.debug("Request to save Device : {}", device);
        return deviceRepository.save(device);
    }

    /**
     *  Get all the devices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Device> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll(pageable);
    }

    /**
     *  Get one device by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Device findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        return deviceRepository.findOne(id);
    }

    /**
     *  Delete the  device by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.delete(id);
    }
}
