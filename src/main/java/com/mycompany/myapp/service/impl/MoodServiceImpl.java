package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.Exceptions.InvalidMoodNameException;
import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.repository.DeviceRepository;
import com.mycompany.myapp.service.MoodService;
import com.mycompany.myapp.domain.Mood;
import com.mycompany.myapp.repository.MoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Service Implementation for managing Mood.
 */
@Service
@Transactional
public class MoodServiceImpl implements MoodService{

    private final Logger log = LoggerFactory.getLogger(MoodServiceImpl.class);

    @Resource
    private MoodRepository moodRepository;
    @Resource
    private DeviceRepository deviceRepository;

    /**
     * Save a mood.
     *
     * @param mood the entity to save
     * @return the persisted entity
     */
    @Override
    public Mood save(Mood mood) {
        log.debug("Request to save Mood : {}", mood);
        if(mood.isActive()){
            toggleDevicesForMood(mood,true);
        }else{
            toggleDevicesForMood(mood,false);
        }

        return moodRepository.save(mood);
    }

    /**
     *  Get all the moods.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Mood> findAll(Pageable pageable) {
        log.debug("Request to get all Moods");
        return moodRepository.findAll(pageable);
    }

    /**
     *  Get one mood by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Mood findOne(Long id) {
        log.debug("Request to get Mood : {}", id);
        return moodRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  mood by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mood : {}", id);
        moodRepository.delete(id);
    }

    @Override
    public void toggleMood(String moodName) throws Exception {
        String message = "this mood does not exist! (yet)";
        if(moodName != null && !moodName.isEmpty()){
            Mood mood = moodRepository.findOneByName(moodName);
            if (mood != null) {
                disableAllDevices();
                toggleDevicesForMood(mood,true);
                mood.setActive(true);
                moodRepository.save(mood);
            }else throw new InvalidMoodNameException(message);
        }else throw new InvalidMoodNameException(message);
    }

    private void disableAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        for (Device device : devices) {
            device.setState(false);
            deviceRepository.save(device);
        }
    }

    private void toggleDevicesForMood(Mood mood,boolean active) {
        List<Device> devices = deviceRepository.findAllByMoods(mood);
        if(devices.size() == 0){ // e.g. away status that turns off everything
            disableAllDevices();
        }else{
            for (Device device : devices) {
                device.setState(active);
                deviceRepository.save(device);
            }
        }
    }

    @Override
    public Mood currentMood() {
        Mood mood = moodRepository.findOneByActive(true);
        if (mood != null) {
            List<Device> activeDevices = deviceRepository.findAllByState(true);
            int moodDevices = deviceRepository.countByMoods(mood);
            if (activeDevices.size() == moodDevices){
                List<Device> allByMoods = deviceRepository.findAllByMoods(mood);
                for (Device allByMood : allByMoods) {
                    if(!activeDevices.contains(allByMood)){
                        return null;
                    }
                }
                return mood;
            }else return null;
        } else return null;
    }
}
