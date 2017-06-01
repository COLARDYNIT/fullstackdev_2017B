package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.MoodService;
import com.mycompany.myapp.domain.Mood;
import com.mycompany.myapp.repository.MoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Mood.
 */
@Service
@Transactional
public class MoodServiceImpl implements MoodService{

    private final Logger log = LoggerFactory.getLogger(MoodServiceImpl.class);

    private final MoodRepository moodRepository;

    public MoodServiceImpl(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    /**
     * Save a mood.
     *
     * @param mood the entity to save
     * @return the persisted entity
     */
    @Override
    public Mood save(Mood mood) {
        log.debug("Request to save Mood : {}", mood);
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
}
