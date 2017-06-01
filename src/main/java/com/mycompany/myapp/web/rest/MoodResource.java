package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Mood;
import com.mycompany.myapp.service.MoodService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mood.
 */
@RestController
@RequestMapping("/api")
public class MoodResource {

    private final Logger log = LoggerFactory.getLogger(MoodResource.class);

    private static final String ENTITY_NAME = "mood";

    private final MoodService moodService;

    public MoodResource(MoodService moodService) {
        this.moodService = moodService;
    }

    /**
     * POST  /moods : Create a new mood.
     *
     * @param mood the mood to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mood, or with status 400 (Bad Request) if the mood has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/moods")
    @Timed
    public ResponseEntity<Mood> createMood(@RequestBody Mood mood) throws URISyntaxException {
        log.debug("REST request to save Mood : {}", mood);
        if (mood.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mood cannot already have an ID")).body(null);
        }
        Mood result = moodService.save(mood);
        return ResponseEntity.created(new URI("/api/moods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /moods : Updates an existing mood.
     *
     * @param mood the mood to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mood,
     * or with status 400 (Bad Request) if the mood is not valid,
     * or with status 500 (Internal Server Error) if the mood couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/moods")
    @Timed
    public ResponseEntity<Mood> updateMood(@RequestBody Mood mood) throws URISyntaxException {
        log.debug("REST request to update Mood : {}", mood);
        if (mood.getId() == null) {
            return createMood(mood);
        }
        Mood result = moodService.save(mood);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mood.getId().toString()))
            .body(result);
    }

    /**
     * GET  /moods : get all the moods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of moods in body
     */
    @GetMapping("/moods")
    @Timed
    public ResponseEntity<List<Mood>> getAllMoods(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Moods");
        Page<Mood> page = moodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/moods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /moods/:id : get the "id" mood.
     *
     * @param id the id of the mood to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mood, or with status 404 (Not Found)
     */
    @GetMapping("/moods/{id}")
    @Timed
    public ResponseEntity<Mood> getMood(@PathVariable Long id) {
        log.debug("REST request to get Mood : {}", id);
        Mood mood = moodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mood));
    }

    /**
     * DELETE  /moods/:id : delete the "id" mood.
     *
     * @param id the id of the mood to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/moods/{id}")
    @Timed
    public ResponseEntity<Void> deleteMood(@PathVariable Long id) {
        log.debug("REST request to delete Mood : {}", id);
        moodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
