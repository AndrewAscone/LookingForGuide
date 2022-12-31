package rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.IntegrationTest;
import rocks.zipcode.domain.UserGamingAccount;
import rocks.zipcode.repository.UserGamingAccountRepository;

/**
 * Integration tests for the {@link UserGamingAccountResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserGamingAccountResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-gaming-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserGamingAccountRepository userGamingAccountRepository;

    @Mock
    private UserGamingAccountRepository userGamingAccountRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserGamingAccountMockMvc;

    private UserGamingAccount userGamingAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGamingAccount createEntity(EntityManager em) {
        UserGamingAccount userGamingAccount = new UserGamingAccount().name(DEFAULT_NAME).accountName(DEFAULT_ACCOUNT_NAME);
        return userGamingAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGamingAccount createUpdatedEntity(EntityManager em) {
        UserGamingAccount userGamingAccount = new UserGamingAccount().name(UPDATED_NAME).accountName(UPDATED_ACCOUNT_NAME);
        return userGamingAccount;
    }

    @BeforeEach
    public void initTest() {
        userGamingAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createUserGamingAccount() throws Exception {
        int databaseSizeBeforeCreate = userGamingAccountRepository.findAll().size();
        // Create the UserGamingAccount
        restUserGamingAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userGamingAccount))
            )
            .andExpect(status().isCreated());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeCreate + 1);
        UserGamingAccount testUserGamingAccount = userGamingAccountList.get(userGamingAccountList.size() - 1);
        assertThat(testUserGamingAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserGamingAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void createUserGamingAccountWithExistingId() throws Exception {
        // Create the UserGamingAccount with an existing ID
        userGamingAccount.setId(1L);

        int databaseSizeBeforeCreate = userGamingAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGamingAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userGamingAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserGamingAccounts() throws Exception {
        // Initialize the database
        userGamingAccountRepository.saveAndFlush(userGamingAccount);

        // Get all the userGamingAccountList
        restUserGamingAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGamingAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserGamingAccountsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userGamingAccountRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserGamingAccountMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userGamingAccountRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserGamingAccountsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userGamingAccountRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserGamingAccountMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(userGamingAccountRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUserGamingAccount() throws Exception {
        // Initialize the database
        userGamingAccountRepository.saveAndFlush(userGamingAccount);

        // Get the userGamingAccount
        restUserGamingAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, userGamingAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGamingAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingUserGamingAccount() throws Exception {
        // Get the userGamingAccount
        restUserGamingAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserGamingAccount() throws Exception {
        // Initialize the database
        userGamingAccountRepository.saveAndFlush(userGamingAccount);

        int databaseSizeBeforeUpdate = userGamingAccountRepository.findAll().size();

        // Update the userGamingAccount
        UserGamingAccount updatedUserGamingAccount = userGamingAccountRepository.findById(userGamingAccount.getId()).get();
        // Disconnect from session so that the updates on updatedUserGamingAccount are not directly saved in db
        em.detach(updatedUserGamingAccount);
        updatedUserGamingAccount.name(UPDATED_NAME).accountName(UPDATED_ACCOUNT_NAME);

        restUserGamingAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserGamingAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserGamingAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeUpdate);
        UserGamingAccount testUserGamingAccount = userGamingAccountList.get(userGamingAccountList.size() - 1);
        assertThat(testUserGamingAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserGamingAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void putNonExistingUserGamingAccount() throws Exception {
        int databaseSizeBeforeUpdate = userGamingAccountRepository.findAll().size();
        userGamingAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGamingAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userGamingAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGamingAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserGamingAccount() throws Exception {
        int databaseSizeBeforeUpdate = userGamingAccountRepository.findAll().size();
        userGamingAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGamingAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGamingAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserGamingAccount() throws Exception {
        int databaseSizeBeforeUpdate = userGamingAccountRepository.findAll().size();
        userGamingAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGamingAccountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userGamingAccount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserGamingAccountWithPatch() throws Exception {
        // Initialize the database
        userGamingAccountRepository.saveAndFlush(userGamingAccount);

        int databaseSizeBeforeUpdate = userGamingAccountRepository.findAll().size();

        // Update the userGamingAccount using partial update
        UserGamingAccount partialUpdatedUserGamingAccount = new UserGamingAccount();
        partialUpdatedUserGamingAccount.setId(userGamingAccount.getId());

        partialUpdatedUserGamingAccount.name(UPDATED_NAME);

        restUserGamingAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGamingAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserGamingAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeUpdate);
        UserGamingAccount testUserGamingAccount = userGamingAccountList.get(userGamingAccountList.size() - 1);
        assertThat(testUserGamingAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserGamingAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateUserGamingAccountWithPatch() throws Exception {
        // Initialize the database
        userGamingAccountRepository.saveAndFlush(userGamingAccount);

        int databaseSizeBeforeUpdate = userGamingAccountRepository.findAll().size();

        // Update the userGamingAccount using partial update
        UserGamingAccount partialUpdatedUserGamingAccount = new UserGamingAccount();
        partialUpdatedUserGamingAccount.setId(userGamingAccount.getId());

        partialUpdatedUserGamingAccount.name(UPDATED_NAME).accountName(UPDATED_ACCOUNT_NAME);

        restUserGamingAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGamingAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserGamingAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeUpdate);
        UserGamingAccount testUserGamingAccount = userGamingAccountList.get(userGamingAccountList.size() - 1);
        assertThat(testUserGamingAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserGamingAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingUserGamingAccount() throws Exception {
        int databaseSizeBeforeUpdate = userGamingAccountRepository.findAll().size();
        userGamingAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGamingAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userGamingAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGamingAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserGamingAccount() throws Exception {
        int databaseSizeBeforeUpdate = userGamingAccountRepository.findAll().size();
        userGamingAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGamingAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGamingAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserGamingAccount() throws Exception {
        int databaseSizeBeforeUpdate = userGamingAccountRepository.findAll().size();
        userGamingAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGamingAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userGamingAccount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGamingAccount in the database
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserGamingAccount() throws Exception {
        // Initialize the database
        userGamingAccountRepository.saveAndFlush(userGamingAccount);

        int databaseSizeBeforeDelete = userGamingAccountRepository.findAll().size();

        // Delete the userGamingAccount
        restUserGamingAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, userGamingAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGamingAccount> userGamingAccountList = userGamingAccountRepository.findAll();
        assertThat(userGamingAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
