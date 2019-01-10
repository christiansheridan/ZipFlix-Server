package rocks.zipcode.io.web.rest;

import rocks.zipcode.io.ZipVidzApp;

import rocks.zipcode.io.domain.ZipFlixUser;
import rocks.zipcode.io.repository.ZipFlixUserRepository;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static rocks.zipcode.io.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ZipFlixUserResource REST controller.
 *
 * @see ZipFlixUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZipVidzApp.class)
public class ZipFlixUserResourceIntTest {

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ZipFlixUserRepository zipFlixUserRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restZipFlixUserMockMvc;

    private ZipFlixUser zipFlixUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZipFlixUserResource zipFlixUserResource = new ZipFlixUserResource(zipFlixUserRepository);
        this.restZipFlixUserMockMvc = MockMvcBuilders.standaloneSetup(zipFlixUserResource)
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
    public static ZipFlixUser createEntity() {
        ZipFlixUser zipFlixUser = new ZipFlixUser()
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH);
        return zipFlixUser;
    }

    @Before
    public void initTest() {
        zipFlixUserRepository.deleteAll();
        zipFlixUser = createEntity();
    }

    @Test
    public void createZipFlixUser() throws Exception {
        int databaseSizeBeforeCreate = zipFlixUserRepository.findAll().size();

        // Create the ZipFlixUser
        restZipFlixUserMockMvc.perform(post("/api/zip-flix-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zipFlixUser)))
            .andExpect(status().isCreated());

        // Validate the ZipFlixUser in the database
        List<ZipFlixUser> zipFlixUserList = zipFlixUserRepository.findAll();
        assertThat(zipFlixUserList).hasSize(databaseSizeBeforeCreate + 1);
        ZipFlixUser testZipFlixUser = zipFlixUserList.get(zipFlixUserList.size() - 1);
        assertThat(testZipFlixUser.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
    }

    @Test
    public void createZipFlixUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zipFlixUserRepository.findAll().size();

        // Create the ZipFlixUser with an existing ID
        zipFlixUser.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restZipFlixUserMockMvc.perform(post("/api/zip-flix-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zipFlixUser)))
            .andExpect(status().isBadRequest());

        // Validate the ZipFlixUser in the database
        List<ZipFlixUser> zipFlixUserList = zipFlixUserRepository.findAll();
        assertThat(zipFlixUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllZipFlixUsers() throws Exception {
        // Initialize the database
        zipFlixUserRepository.save(zipFlixUser);

        // Get all the zipFlixUserList
        restZipFlixUserMockMvc.perform(get("/api/zip-flix-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zipFlixUser.getId())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())));
    }
    
    @Test
    public void getZipFlixUser() throws Exception {
        // Initialize the database
        zipFlixUserRepository.save(zipFlixUser);

        // Get the zipFlixUser
        restZipFlixUserMockMvc.perform(get("/api/zip-flix-users/{id}", zipFlixUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zipFlixUser.getId()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()));
    }

    @Test
    public void getNonExistingZipFlixUser() throws Exception {
        // Get the zipFlixUser
        restZipFlixUserMockMvc.perform(get("/api/zip-flix-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateZipFlixUser() throws Exception {
        // Initialize the database
        zipFlixUserRepository.save(zipFlixUser);

        int databaseSizeBeforeUpdate = zipFlixUserRepository.findAll().size();

        // Update the zipFlixUser
        ZipFlixUser updatedZipFlixUser = zipFlixUserRepository.findById(zipFlixUser.getId()).get();
        updatedZipFlixUser
            .dateOfBirth(UPDATED_DATE_OF_BIRTH);

        restZipFlixUserMockMvc.perform(put("/api/zip-flix-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZipFlixUser)))
            .andExpect(status().isOk());

        // Validate the ZipFlixUser in the database
        List<ZipFlixUser> zipFlixUserList = zipFlixUserRepository.findAll();
        assertThat(zipFlixUserList).hasSize(databaseSizeBeforeUpdate);
        ZipFlixUser testZipFlixUser = zipFlixUserList.get(zipFlixUserList.size() - 1);
        assertThat(testZipFlixUser.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
    }

    @Test
    public void updateNonExistingZipFlixUser() throws Exception {
        int databaseSizeBeforeUpdate = zipFlixUserRepository.findAll().size();

        // Create the ZipFlixUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZipFlixUserMockMvc.perform(put("/api/zip-flix-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zipFlixUser)))
            .andExpect(status().isBadRequest());

        // Validate the ZipFlixUser in the database
        List<ZipFlixUser> zipFlixUserList = zipFlixUserRepository.findAll();
        assertThat(zipFlixUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteZipFlixUser() throws Exception {
        // Initialize the database
        zipFlixUserRepository.save(zipFlixUser);

        int databaseSizeBeforeDelete = zipFlixUserRepository.findAll().size();

        // Get the zipFlixUser
        restZipFlixUserMockMvc.perform(delete("/api/zip-flix-users/{id}", zipFlixUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ZipFlixUser> zipFlixUserList = zipFlixUserRepository.findAll();
        assertThat(zipFlixUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZipFlixUser.class);
        ZipFlixUser zipFlixUser1 = new ZipFlixUser();
        zipFlixUser1.setId("id1");
        ZipFlixUser zipFlixUser2 = new ZipFlixUser();
        zipFlixUser2.setId(zipFlixUser1.getId());
        assertThat(zipFlixUser1).isEqualTo(zipFlixUser2);
        zipFlixUser2.setId("id2");
        assertThat(zipFlixUser1).isNotEqualTo(zipFlixUser2);
        zipFlixUser1.setId(null);
        assertThat(zipFlixUser1).isNotEqualTo(zipFlixUser2);
    }
}
