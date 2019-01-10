package rocks.zipcode.io.web.rest;

import rocks.zipcode.io.ZipVidzApp;

import rocks.zipcode.io.domain.SearchResult;
import rocks.zipcode.io.repository.SearchResultRepository;
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
 * Test class for the SearchResultResource REST controller.
 *
 * @see SearchResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZipVidzApp.class)
public class SearchResultResourceIntTest {

    @Autowired
    private SearchResultRepository searchResultRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSearchResultMockMvc;

    private SearchResult searchResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SearchResultResource searchResultResource = new SearchResultResource(searchResultRepository);
        this.restSearchResultMockMvc = MockMvcBuilders.standaloneSetup(searchResultResource)
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
    public static SearchResult createEntity() {
        SearchResult searchResult = new SearchResult();
        return searchResult;
    }

    @Before
    public void initTest() {
        searchResultRepository.deleteAll();
        searchResult = createEntity();
    }

    @Test
    public void createSearchResult() throws Exception {
        int databaseSizeBeforeCreate = searchResultRepository.findAll().size();

        // Create the SearchResult
        restSearchResultMockMvc.perform(post("/api/search-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchResult)))
            .andExpect(status().isCreated());

        // Validate the SearchResult in the database
        List<SearchResult> searchResultList = searchResultRepository.findAll();
        assertThat(searchResultList).hasSize(databaseSizeBeforeCreate + 1);
        SearchResult testSearchResult = searchResultList.get(searchResultList.size() - 1);
    }

    @Test
    public void createSearchResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = searchResultRepository.findAll().size();

        // Create the SearchResult with an existing ID
        searchResult.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchResultMockMvc.perform(post("/api/search-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchResult)))
            .andExpect(status().isBadRequest());

        // Validate the SearchResult in the database
        List<SearchResult> searchResultList = searchResultRepository.findAll();
        assertThat(searchResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllSearchResults() throws Exception {
        // Initialize the database
        searchResultRepository.save(searchResult);

        // Get all the searchResultList
        restSearchResultMockMvc.perform(get("/api/search-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(searchResult.getId())));
    }
    
    @Test
    public void getSearchResult() throws Exception {
        // Initialize the database
        searchResultRepository.save(searchResult);

        // Get the searchResult
        restSearchResultMockMvc.perform(get("/api/search-results/{id}", searchResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(searchResult.getId()));
    }

    @Test
    public void getNonExistingSearchResult() throws Exception {
        // Get the searchResult
        restSearchResultMockMvc.perform(get("/api/search-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSearchResult() throws Exception {
        // Initialize the database
        searchResultRepository.save(searchResult);

        int databaseSizeBeforeUpdate = searchResultRepository.findAll().size();

        // Update the searchResult
        SearchResult updatedSearchResult = searchResultRepository.findById(searchResult.getId()).get();

        restSearchResultMockMvc.perform(put("/api/search-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSearchResult)))
            .andExpect(status().isOk());

        // Validate the SearchResult in the database
        List<SearchResult> searchResultList = searchResultRepository.findAll();
        assertThat(searchResultList).hasSize(databaseSizeBeforeUpdate);
        SearchResult testSearchResult = searchResultList.get(searchResultList.size() - 1);
    }

    @Test
    public void updateNonExistingSearchResult() throws Exception {
        int databaseSizeBeforeUpdate = searchResultRepository.findAll().size();

        // Create the SearchResult

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchResultMockMvc.perform(put("/api/search-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchResult)))
            .andExpect(status().isBadRequest());

        // Validate the SearchResult in the database
        List<SearchResult> searchResultList = searchResultRepository.findAll();
        assertThat(searchResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSearchResult() throws Exception {
        // Initialize the database
        searchResultRepository.save(searchResult);

        int databaseSizeBeforeDelete = searchResultRepository.findAll().size();

        // Get the searchResult
        restSearchResultMockMvc.perform(delete("/api/search-results/{id}", searchResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SearchResult> searchResultList = searchResultRepository.findAll();
        assertThat(searchResultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchResult.class);
        SearchResult searchResult1 = new SearchResult();
        searchResult1.setId("id1");
        SearchResult searchResult2 = new SearchResult();
        searchResult2.setId(searchResult1.getId());
        assertThat(searchResult1).isEqualTo(searchResult2);
        searchResult2.setId("id2");
        assertThat(searchResult1).isNotEqualTo(searchResult2);
        searchResult1.setId(null);
        assertThat(searchResult1).isNotEqualTo(searchResult2);
    }
}
