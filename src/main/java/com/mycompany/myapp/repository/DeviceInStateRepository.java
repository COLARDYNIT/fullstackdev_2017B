package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.domain.DeviceInState;
import com.mycompany.myapp.domain.Mood;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the DeviceInState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceInStateRepository extends JpaRepository<DeviceInState,Long> {

    List<DeviceInState> findAllByMoods(Mood mood);

    int countByMoods(Mood mood);

    List<DeviceInState> findAllByDevice(Device device);
}
