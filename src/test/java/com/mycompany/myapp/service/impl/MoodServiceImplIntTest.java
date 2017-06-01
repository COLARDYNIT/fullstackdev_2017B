package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.Exceptions.InvalidMoodNameException;
import com.mycompany.myapp.Fullstackdev2017BApp;
import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.domain.DeviceInState;
import com.mycompany.myapp.domain.Mood;
import com.mycompany.myapp.repository.DeviceInStateRepository;
import com.mycompany.myapp.repository.DeviceRepository;
import com.mycompany.myapp.repository.MoodRepository;
import com.mycompany.myapp.service.MoodService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by stijnhaesendonck on 01/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fullstackdev2017BApp.class)
@Transactional
public class MoodServiceImplIntTest {
    @Resource
    private DeviceRepository deviceRepository;
    @Resource
    private MoodRepository moodRepository;
    @Resource
    private MoodService moodService;
    @Resource
    private DeviceInStateRepository deviceInStateRepository;

    private Mood home;
    private Device light;
    private Device shutter;
    private Device power;

    private DeviceInState lightInState;
    private DeviceInState shutterInState;


    @Before
    public void setup(){
        moodRepository.deleteAll();
        deviceRepository.deleteAll();
        deviceInStateRepository.deleteAll();

        light = new Device();
        light.setName("light");
        shutter = new Device();
        shutter.setName("shutter");
        power = new Device();
        power.setName("power");

        deviceRepository.save(light);
        deviceRepository.save(power);
        deviceRepository.save(shutter);

        lightInState = new DeviceInState();
        lightInState.setDevice(light);
        lightInState.setState(true);

        shutterInState = new DeviceInState();
        shutterInState.setDevice(shutter);
        shutterInState.setState(true);

        deviceInStateRepository.save(lightInState);
        deviceInStateRepository.save(shutterInState);

        home = new Mood();
        home.setName("home");
        home.addDeviceInState(lightInState);
        home.addDeviceInState(shutterInState);

        moodRepository.save(home);

    }

    @Test
    public void toggleMood() throws Exception {
        moodService.toggleMood(home.getName());

        List<Device> activeDevices = deviceRepository.findAllByState(true);
        assertTrue(!activeDevices.contains(power));

        Mood activeMood = moodRepository.findOneByActive(true);
        assertTrue(activeMood.getName() == home.getName());


    }

    @Test
    public void toggleMoodNull(){
        try{
            moodService.toggleMood(null);
        }catch(Exception e){
            assertTrue(e.getClass() == InvalidMoodNameException.class);
        }
    }

    @Test
    public void currentMood() throws Exception {
        moodService.toggleMood(home.getName());
        Mood mood = moodService.currentMood();
        assertTrue(mood.getName() == home.getName());
    }

}
