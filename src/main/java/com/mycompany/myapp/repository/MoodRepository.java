package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Device;
import com.mycompany.myapp.domain.Mood;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Mood entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoodRepository extends JpaRepository<Mood,Long> {

    @Query("select distinct mood from Mood mood left join fetch mood.devices")
    List<Mood> findAllWithEagerRelationships();

    @Query("select mood from Mood mood left join fetch mood.devices where mood.id =:id")
    Mood findOneWithEagerRelationships(@Param("id") Long id);

    Mood findOneByName(String moodName);

    Mood findOneByActive(boolean b);

    List<Mood> findAllByDevices(Device device);
}
