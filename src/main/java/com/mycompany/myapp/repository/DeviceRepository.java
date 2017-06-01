package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.domain.Mood;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Device entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {

    List<Device> findAllByMoods(Mood mood);

    long countByState(boolean b);

    int countByMoods(Mood mood);

    List<Device> findAllByState(boolean b);
}
