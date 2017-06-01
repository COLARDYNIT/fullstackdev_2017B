package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.Exceptions.InvalidMoodNameException;
import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.domain.Mood;
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

    @InjectMocks
    MoodServiceImpl moodService;



    @Test
    public void toggleMood() throws Exception {
        List<Device> deviceList = new ArrayList<>();
        Mood home = new Mood();
        home.setName("home");
        doReturn(home).when(moodRepository).findOneByName(anyString());
        doReturn(deviceList).when(deviceRepository).findAll();
        doReturn(deviceList).when(deviceRepository).findAllByMoods(any(Mood.class));

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
        List<Device> devices = new ArrayList<>();
        devices.add(new Device());
        devices.add(new Device());
        devices.add(new Device());

        int size = devices.size();

        Mood active = new Mood();
        doReturn(active).when(moodRepository).findOneByActive(true);
        doReturn(size).when(deviceRepository).countByMoods(any(Mood.class));
        doReturn(devices).when(deviceRepository).findAllByMoods(any(Mood.class));
        doReturn(devices).when(deviceRepository).findAllByState(true);


        Mood mood = moodService.currentMood();
        assertFalse(mood == null);

    }

    @Test
    public void NoCurrentMood() throws Exception {
        List<Device> devices = new ArrayList<>();
        devices.add(new Device());
        devices.add(new Device());
        devices.add(new Device());

        int size = 1;

        Mood active = new Mood();
        doReturn(active).when(moodRepository).findOneByActive(true);
        doReturn(size).when(deviceRepository).countByMoods(any(Mood.class));
        doReturn(devices).when(deviceRepository).findAllByMoods(any(Mood.class));
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
