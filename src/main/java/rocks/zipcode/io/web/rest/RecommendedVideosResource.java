package rocks.zipcode.io.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.zipcode.io.domain.RecommendedVideos;
import rocks.zipcode.io.repository.RecommendedVideosRepository;
import rocks.zipcode.io.web.rest.errors.BadRequestAlertException;
import rocks.zipcode.io.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RecommendedVideos.
 */
@RestController
@RequestMapping("/api")
public class RecommendedVideosResource {

    private final Logger log = LoggerFactory.getLogger(RecommendedVideosResource.class);

    private static final String ENTITY_NAME = "recommendedVideos";

    private final RecommendedVideosRepository recommendedVideosRepository;

    public RecommendedVideosResource(RecommendedVideosRepository recommendedVideosRepository) {
        this.recommendedVideosRepository = recommendedVideosRepository;
    }

    /**
     * POST  /recommended-videos : Create a new recommendedVideos.
     *
     * @param recommendedVideos the recommendedVideos to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recommendedVideos, or with status 400 (Bad Request) if the recommendedVideos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recommended-videos")
    @Timed
    public ResponseEntity<RecommendedVideos> createRecommendedVideos(@RequestBody RecommendedVideos recommendedVideos) throws URISyntaxException {
        log.debug("REST request to save RecommendedVideos : {}", recommendedVideos);
        if (recommendedVideos.getId() != null) {
            throw new BadRequestAlertException("A new recommendedVideos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecommendedVideos result = recommendedVideosRepository.save(recommendedVideos);
        return ResponseEntity.created(new URI("/api/recommended-videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recommended-videos : Updates an existing recommendedVideos.
     *
     * @param recommendedVideos the recommendedVideos to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recommendedVideos,
     * or with status 400 (Bad Request) if the recommendedVideos is not valid,
     * or with status 500 (Internal Server Error) if the recommendedVideos couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recommended-videos")
    @Timed
    public ResponseEntity<RecommendedVideos> updateRecommendedVideos(@RequestBody RecommendedVideos recommendedVideos) throws URISyntaxException {
        log.debug("REST request to update RecommendedVideos : {}", recommendedVideos);
        if (recommendedVideos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecommendedVideos result = recommendedVideosRepository.save(recommendedVideos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recommendedVideos.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recommended-videos : get all the recommendedVideos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of recommendedVideos in body
     */
    @GetMapping("/recommended-videos")
    @Timed
    public List<RecommendedVideos> getAllRecommendedVideos() {
        log.debug("REST request to get all RecommendedVideos");
        return recommendedVideosRepository.findAll();
    }

    /**
     * GET  /recommended-videos/:id : get the "id" recommendedVideos.
     *
     * @param id the id of the recommendedVideos to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recommendedVideos, or with status 404 (Not Found)
     */
    @GetMapping("/recommended-videos/{id}")
    @Timed
    public ResponseEntity<RecommendedVideos> getRecommendedVideos(@PathVariable String id) {
        log.debug("REST request to get RecommendedVideos : {}", id);
        Optional<RecommendedVideos> recommendedVideos = recommendedVideosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recommendedVideos);
    }

    /**
     * DELETE  /recommended-videos/:id : delete the "id" recommendedVideos.
     *
     * @param id the id of the recommendedVideos to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recommended-videos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecommendedVideos(@PathVariable String id) {
        log.debug("REST request to delete RecommendedVideos : {}", id);

        recommendedVideosRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
