package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Mood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Mood.
 */
public interface MoodService {

    /**
     * Save a mood.
     *
     * @param mood the entity to save
     * @return the persisted entity
     */
    Mood save(Mood mood);

    /**
     *  Get all the moods.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Mood> findAll(Pageable pageable);

    /**
     *  Get the "id" mood.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Mood findOne(Long id);

    /**
     *  Delete the "id" mood.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void toggleMood(String moodName) throws Exception;
}
