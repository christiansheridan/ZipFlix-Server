package rocks.zipcode.io.web.rest;

import rocks.zipcode.io.ZipVidzApp;

import rocks.zipcode.io.domain.RecommendedVideos;
import rocks.zipcode.io.repository.RecommendedVideosRepository;
import rocks.zipcode.io.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static rocks.zipcode.io.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RecommendedVideosResource REST controller.
 *
 * @see RecommendedVideosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZipVidzApp.class)
public class RecommendedVideosResourceIntTest {

    @Autowired
    private RecommendedVideosRepository recommendedVideosRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRecommendedVideosMockMvc;

    private RecommendedVideos recommendedVideos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecommendedVideosResource recommendedVideosResource = new RecommendedVideosResource(recommendedVideosRepository);
        this.restRecommendedVideosMockMvc = MockMvcBuilders.standaloneSetup(recommendedVideosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecommendedVideos createEntity() {
        RecommendedVideos recommendedVideos = new RecommendedVideos();
        return recommendedVideos;
    }

    @Before
    public void initTest() {
        recommendedVideosRepository.deleteAll();
        recommendedVideos = createEntity();
    }

    @Test
    public void createRecommendedVideos() throws Exception {
        int databaseSizeBeforeCreate = recommendedVideosRepository.findAll().size();

        // Create the RecommendedVideos
        restRecommendedVideosMockMvc.perform(post("/api/recommended-videos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recommendedVideos)))
            .andExpect(status().isCreated());

        // Validate the RecommendedVideos in the database
        List<RecommendedVideos> recommendedVideosList = recommendedVideosRepository.findAll();
        assertThat(recommendedVideosList).hasSize(databaseSizeBeforeCreate + 1);
        RecommendedVideos testRecommendedVideos = recommendedVideosList.get(recommendedVideosList.size() - 1);
    }

    @Test
    public void createRecommendedVideosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recommendedVideosRepository.findAll().size();

        // Create the RecommendedVideos with an existing ID
        recommendedVideos.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecommendedVideosMockMvc.perform(post("/api/recommended-videos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recommendedVideos)))
            .andExpect(status().isBadRequest());

        // Validate the RecommendedVideos in the database
        List<RecommendedVideos> recommendedVideosList = recommendedVideosRepository.findAll();
        assertThat(recommendedVideosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllRecommendedVideos() throws Exception {
        // Initialize the database
        recommendedVideosRepository.save(recommendedVideos);

        // Get all the recommendedVideosList
        restRecommendedVideosMockMvc.perform(get("/api/recommended-videos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recommendedVideos.getId())));
    }
    
    @Test
    public void getRecommendedVideos() throws Exception {
        // Initialize the database
        recommendedVideosRepository.save(recommendedVideos);

        // Get the recommendedVideos
        restRecommendedVideosMockMvc.perform(get("/api/recommended-videos/{id}", recommendedVideos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recommendedVideos.getId()));
    }

    @Test
    public void getNonExistingRecommendedVideos() throws Exception {
        // Get the recommendedVideos
        restRecommendedVideosMockMvc.perform(get("/api/recommended-videos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRecommendedVideos() throws Exception {
        // Initialize the database
        recommendedVideosRepository.save(recommendedVideos);

        int databaseSizeBeforeUpdate = recommendedVideosRepository.findAll().size();

        // Update the recommendedVideos
        RecommendedVideos updatedRecommendedVideos = recommendedVideosRepository.findById(recommendedVideos.getId()).get();

        restRecommendedVideosMockMvc.perform(put("/api/recommended-videos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecommendedVideos)))
            .andExpect(status().isOk());

        // Validate the RecommendedVideos in the database
        List<RecommendedVideos> recommendedVideosList = recommendedVideosRepository.findAll();
        assertThat(recommendedVideosList).hasSize(databaseSizeBeforeUpdate);
        RecommendedVideos testRecommendedVideos = recommendedVideosList.get(recommendedVideosList.size() - 1);
    }

    @Test
    public void updateNonExistingRecommendedVideos() throws Exception {
        int databaseSizeBeforeUpdate = recommendedVideosRepository.findAll().size();

        // Create the RecommendedVideos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecommendedVideosMockMvc.perform(put("/api/recommended-videos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recommendedVideos)))
            .andExpect(status().isBadRequest());

        // Validate the RecommendedVideos in the database
        List<RecommendedVideos> recommendedVideosList = recommendedVideosRepository.findAll();
        assertThat(recommendedVideosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteRecommendedVideos() throws Exception {
        // Initialize the database
        recommendedVideosRepository.save(recommendedVideos);

        int databaseSizeBeforeDelete = recommendedVideosRepository.findAll().size();

        // Get the recommendedVideos
        restRecommendedVideosMockMvc.perform(delete("/api/recommended-videos/{id}", recommendedVideos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecommendedVideos> recommendedVideosList = recommendedVideosRepository.findAll();
        assertThat(recommendedVideosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecommendedVideos.class);
        RecommendedVideos recommendedVideos1 = new RecommendedVideos();
        recommendedVideos1.setId("id1");
        RecommendedVideos recommendedVideos2 = new RecommendedVideos();
        recommendedVideos2.setId(recommendedVideos1.getId());
        assertThat(recommendedVideos1).isEqualTo(recommendedVideos2);
        recommendedVideos2.setId("id2");
        assertThat(recommendedVideos1).isNotEqualTo(recommendedVideos2);
        recommendedVideos1.setId(null);
        assertThat(recommendedVideos1).isNotEqualTo(recommendedVideos2);
    }
}
