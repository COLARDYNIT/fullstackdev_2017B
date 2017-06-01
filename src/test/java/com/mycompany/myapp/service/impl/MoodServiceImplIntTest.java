package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.Fullstackdev2017BApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by stijnhaesendonck on 01/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Fullstackdev2017BApp.class)
@Transactional
public class MoodServiceImplIntTest {
    @Before
    public void setup(){

    }

    @Test
    public void toggleMood() throws Exception {

    }

    @Test
    public void currentMood() throws Exception {

    }

}
