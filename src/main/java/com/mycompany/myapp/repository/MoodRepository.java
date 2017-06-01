package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Mood;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mood entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoodRepository extends JpaRepository<Mood,Long> {

}
