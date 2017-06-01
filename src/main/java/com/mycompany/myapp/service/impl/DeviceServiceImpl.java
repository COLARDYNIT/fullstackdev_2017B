package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.domain.DeviceInState;
import com.mycompany.myapp.domain.Mood;
import com.mycompany.myapp.repository.DeviceInStateRepository;
import com.mycompany.myapp.repository.DeviceRepository;
import com.mycompany.myapp.repository.MoodRepository;
import com.mycompany.myapp.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Service Implementation for managing Device.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService{

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Resource
    private DeviceRepository deviceRepository;
    private DeviceInStateRepository deviceInStateRepository;
    @Resource
    private MoodRepository moodRepository;



    /**
     * Save a device.
     *
     * @param device the entity to save
     * @return the persisted entity
     */
    @Override
    public Device save(Device device) {
        log.debug("Request to save Device : {}", device);
        //if device is part of active mood and state is set to false set mood to false

        if (device.getId() != null && !device.isState()){
            List<DeviceInState> allByDevice = deviceInStateRepository.findAllByDevice(device);
            for (DeviceInState deviceInState : allByDevice) {
                List<Mood> allByDeviceInStates = moodRepository.findAllByDeviceInStates(deviceInState);
                for (Mood mood : allByDeviceInStates) {
                    mood.setActive(false);
                    moodRepository.save(mood);
                }
            }
        }


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
