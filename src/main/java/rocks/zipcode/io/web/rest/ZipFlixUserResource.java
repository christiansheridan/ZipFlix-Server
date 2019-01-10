package rocks.zipcode.io.web.rest;

import com.codahale.metrics.annotation.Timed;
import rocks.zipcode.io.domain.ZipFlixUser;
import rocks.zipcode.io.repository.ZipFlixUserRepository;
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
 * REST controller for managing ZipFlixUser.
 */
@RestController
@RequestMapping("/api")
public class ZipFlixUserResource {

    private final Logger log = LoggerFactory.getLogger(ZipFlixUserResource.class);

    private static final String ENTITY_NAME = "zipFlixUser";

    private final ZipFlixUserRepository zipFlixUserRepository;

    public ZipFlixUserResource(ZipFlixUserRepository zipFlixUserRepository) {
        this.zipFlixUserRepository = zipFlixUserRepository;
    }

    /**
     * POST  /zip-flix-users : Create a new zipFlixUser.
     *
     * @param zipFlixUser the zipFlixUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zipFlixUser, or with status 400 (Bad Request) if the zipFlixUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zip-flix-users")
    @Timed
    public ResponseEntity<ZipFlixUser> createZipFlixUser(@RequestBody ZipFlixUser zipFlixUser) throws URISyntaxException {
        log.debug("REST request to save ZipFlixUser : {}", zipFlixUser);
        if (zipFlixUser.getId() != null) {
            throw new BadRequestAlertException("A new zipFlixUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZipFlixUser result = zipFlixUserRepository.save(zipFlixUser);
        return ResponseEntity.created(new URI("/api/zip-flix-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zip-flix-users : Updates an existing zipFlixUser.
     *
     * @param zipFlixUser the zipFlixUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zipFlixUser,
     * or with status 400 (Bad Request) if the zipFlixUser is not valid,
     * or with status 500 (Internal Server Error) if the zipFlixUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zip-flix-users")
    @Timed
    public ResponseEntity<ZipFlixUser> updateZipFlixUser(@RequestBody ZipFlixUser zipFlixUser) throws URISyntaxException {
        log.debug("REST request to update ZipFlixUser : {}", zipFlixUser);
        if (zipFlixUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZipFlixUser result = zipFlixUserRepository.save(zipFlixUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zipFlixUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zip-flix-users : get all the zipFlixUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zipFlixUsers in body
     */
    @GetMapping("/zip-flix-users")
    @Timed
    public List<ZipFlixUser> getAllZipFlixUsers() {
        log.debug("REST request to get all ZipFlixUsers");
        return zipFlixUserRepository.findAll();
    }

    /**
     * GET  /zip-flix-users/:id : get the "id" zipFlixUser.
     *
     * @param id the id of the zipFlixUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zipFlixUser, or with status 404 (Not Found)
     */
    @GetMapping("/zip-flix-users/{id}")
    @Timed
    public ResponseEntity<ZipFlixUser> getZipFlixUser(@PathVariable String id) {
        log.debug("REST request to get ZipFlixUser : {}", id);
        Optional<ZipFlixUser> zipFlixUser = zipFlixUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zipFlixUser);
    }

    /**
     * DELETE  /zip-flix-users/:id : delete the "id" zipFlixUser.
     *
     * @param id the id of the zipFlixUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zip-flix-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteZipFlixUser(@PathVariable String id) {
        log.debug("REST request to delete ZipFlixUser : {}", id);

        zipFlixUserRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
