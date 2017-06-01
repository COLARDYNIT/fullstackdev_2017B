package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.Exceptions.InvalidMoodNameException;
import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.domain.DeviceInState;
import com.mycompany.myapp.domain.Mood;
import com.mycompany.myapp.repository.DeviceInStateRepository;
import com.mycompany.myapp.repository.DeviceRepository;
import com.mycompany.myapp.repository.MoodRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by stijnhaesendonck on 01/06/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MoodServiceImplMockTest {
    @Mock
    MoodRepository moodRepository;
    @Mock
    DeviceRepository deviceRepository;
    @Mock
    DeviceInStateRepository deviceInStateRepository;

    @InjectMocks
    MoodServiceImpl moodService;



    @Test
    public void toggleMood() throws Exception {
        List<DeviceInState> deviceList = new ArrayList<>();
        List<Device> devices = new ArrayList<>();
        Device device = new Device();
        Mood home = new Mood();
        home.setName("home");
        doReturn(home).when(moodRepository).findOneByName(anyString());
        doReturn(devices).when(deviceRepository).findAll();
        doReturn(device).when(deviceRepository).findOne(anyLong());
        doReturn(deviceList).when(deviceInStateRepository).findAllByMoods(any(Mood.class));

        moodService.toggleMood(home.getName());

        verify(moodRepository).save(any(Mood.class));


    }
    @Test
    public void toggleMoodNoExist() {
        try {
            moodService.toggleMood(null);
        }catch (Exception e){
            assertTrue(e.getClass() == InvalidMoodNameException.class);
        }
    }
    @Test
    public void toggleMoodFullCaps() {
        String caps = "CAPS";
        doReturn(null).when(moodRepository).findOneByName(caps);
        try {
            moodService.toggleMood(caps);
        }catch (Exception e){
            assertTrue(e.getClass() == InvalidMoodNameException.class);
        }
    }

    @Test
    public void currentMood() throws Exception {
        List<DeviceInState> devices = new ArrayList<>();
        List<Device> deviceList = new ArrayList<>();
        Device device1 = new Device();
        Device device2 = new Device();
        Device device3 = new Device();
        deviceList.add(device1);
        deviceList.add(device2);
        deviceList.add(device3);


        DeviceInState one = new DeviceInState();
        DeviceInState two = new DeviceInState();
        DeviceInState three = new DeviceInState();
        one.setDevice(device1);
        two.setDevice(device2);
        three.setDevice(device3);


        devices.add(one);
        devices.add(two);
        devices.add(three);

        int size = devices.size();

        Mood active = new Mood();
        doReturn(active).when(moodRepository).findOneByActive(true);

        doReturn(size).when(deviceInStateRepository).countByMoods(any(Mood.class));
        doReturn(devices).when(deviceInStateRepository).findAllByMoods(any(Mood.class));
        doReturn(deviceList).when(deviceRepository).findAllByState(true);


        Mood mood = moodService.currentMood();
        assertFalse(mood == null);

    }

    @Test
    public void NoCurrentMood() throws Exception {
        List<DeviceInState> devices = new ArrayList<>();
        devices.add(new DeviceInState());
        devices.add(new DeviceInState());
        devices.add(new DeviceInState());

        int size = 1;

        Mood active = new Mood();
        doReturn(active).when(moodRepository).findOneByActive(true);
        doReturn(size).when(deviceInStateRepository).countByMoods(any(Mood.class));
        doReturn(devices).when(deviceInStateRepository).findAllByMoods(any(Mood.class));
        doReturn(devices).when(deviceRepository).findAllByState(true);

        Mood mood = moodService.currentMood();
        assertTrue(mood == null);
    }
    @Test
    public void NoCurrentMoodNull() throws Exception {
        doReturn(null).when(moodRepository).findOneByActive(true);
        Mood mood = moodService.currentMood();
        assertTrue(mood == null);
    }


}
