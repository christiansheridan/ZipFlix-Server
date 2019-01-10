package rocks.zipcode.io.web.rest;

import rocks.zipcode.io.ZipVidzApp;

import rocks.zipcode.io.domain.Search;
import rocks.zipcode.io.repository.SearchRepository;
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
 * Test class for the SearchResource REST controller.
 *
 * @see SearchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZipVidzApp.class)
public class SearchResourceIntTest {

    private static final String DEFAULT_SEARCH_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_QUERY = "BBBBBBBBBB";

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSearchMockMvc;

    private Search search;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SearchResource searchResource = new SearchResource(searchRepository);
        this.restSearchMockMvc = MockMvcBuilders.standaloneSetup(searchResource)
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
    public static Search createEntity() {
        Search search = new Search()
            .searchQuery(DEFAULT_SEARCH_QUERY);
        return search;
    }

    @Before
    public void initTest() {
        searchRepository.deleteAll();
        search = createEntity();
    }

    @Test
    public void createSearch() throws Exception {
        int databaseSizeBeforeCreate = searchRepository.findAll().size();

        // Create the Search
        restSearchMockMvc.perform(post("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(search)))
            .andExpect(status().isCreated());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeCreate + 1);
        Search testSearch = searchList.get(searchList.size() - 1);
        assertThat(testSearch.getSearchQuery()).isEqualTo(DEFAULT_SEARCH_QUERY);
    }

    @Test
    public void createSearchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = searchRepository.findAll().size();

        // Create the Search with an existing ID
        search.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchMockMvc.perform(post("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(search)))
            .andExpect(status().isBadRequest());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllSearches() throws Exception {
        // Initialize the database
        searchRepository.save(search);

        // Get all the searchList
        restSearchMockMvc.perform(get("/api/searches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(search.getId())))
            .andExpect(jsonPath("$.[*].searchQuery").value(hasItem(DEFAULT_SEARCH_QUERY.toString())));
    }
    
    @Test
    public void getSearch() throws Exception {
        // Initialize the database
        searchRepository.save(search);

        // Get the search
        restSearchMockMvc.perform(get("/api/searches/{id}", search.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(search.getId()))
            .andExpect(jsonPath("$.searchQuery").value(DEFAULT_SEARCH_QUERY.toString()));
    }

    @Test
    public void getNonExistingSearch() throws Exception {
        // Get the search
        restSearchMockMvc.perform(get("/api/searches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSearch() throws Exception {
        // Initialize the database
        searchRepository.save(search);

        int databaseSizeBeforeUpdate = searchRepository.findAll().size();

        // Update the search
        Search updatedSearch = searchRepository.findById(search.getId()).get();
        updatedSearch
            .searchQuery(UPDATED_SEARCH_QUERY);

        restSearchMockMvc.perform(put("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSearch)))
            .andExpect(status().isOk());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeUpdate);
        Search testSearch = searchList.get(searchList.size() - 1);
        assertThat(testSearch.getSearchQuery()).isEqualTo(UPDATED_SEARCH_QUERY);
    }

    @Test
    public void updateNonExistingSearch() throws Exception {
        int databaseSizeBeforeUpdate = searchRepository.findAll().size();

        // Create the Search

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchMockMvc.perform(put("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(search)))
            .andExpect(status().isBadRequest());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSearch() throws Exception {
        // Initialize the database
        searchRepository.save(search);

        int databaseSizeBeforeDelete = searchRepository.findAll().size();

        // Get the search
        restSearchMockMvc.perform(delete("/api/searches/{id}", search.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Search.class);
        Search search1 = new Search();
        search1.setId("id1");
        Search search2 = new Search();
        search2.setId(search1.getId());
        assertThat(search1).isEqualTo(search2);
        search2.setId("id2");
        assertThat(search1).isNotEqualTo(search2);
        search1.setId(null);
        assertThat(search1).isNotEqualTo(search2);
    }
}
