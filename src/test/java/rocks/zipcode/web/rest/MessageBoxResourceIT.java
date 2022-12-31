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
import rocks.zipcode.domain.MessageBox;
import rocks.zipcode.repository.MessageBoxRepository;

/**
 * Integration tests for the {@link MessageBoxResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MessageBoxResourceIT {

    private static final String ENTITY_API_URL = "/api/message-boxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MessageBoxRepository messageBoxRepository;

    @Mock
    private MessageBoxRepository messageBoxRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageBoxMockMvc;

    private MessageBox messageBox;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageBox createEntity(EntityManager em) {
        MessageBox messageBox = new MessageBox();
        return messageBox;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageBox createUpdatedEntity(EntityManager em) {
        MessageBox messageBox = new MessageBox();
        return messageBox;
    }

    @BeforeEach
    public void initTest() {
        messageBox = createEntity(em);
    }

    @Test
    @Transactional
    void createMessageBox() throws Exception {
        int databaseSizeBeforeCreate = messageBoxRepository.findAll().size();
        // Create the MessageBox
        restMessageBoxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageBox)))
            .andExpect(status().isCreated());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeCreate + 1);
        MessageBox testMessageBox = messageBoxList.get(messageBoxList.size() - 1);
    }

    @Test
    @Transactional
    void createMessageBoxWithExistingId() throws Exception {
        // Create the MessageBox with an existing ID
        messageBox.setId(1L);

        int databaseSizeBeforeCreate = messageBoxRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageBoxMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageBox)))
            .andExpect(status().isBadRequest());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMessageBoxes() throws Exception {
        // Initialize the database
        messageBoxRepository.saveAndFlush(messageBox);

        // Get all the messageBoxList
        restMessageBoxMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageBox.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMessageBoxesWithEagerRelationshipsIsEnabled() throws Exception {
        when(messageBoxRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMessageBoxMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(messageBoxRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMessageBoxesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(messageBoxRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMessageBoxMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(messageBoxRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMessageBox() throws Exception {
        // Initialize the database
        messageBoxRepository.saveAndFlush(messageBox);

        // Get the messageBox
        restMessageBoxMockMvc
            .perform(get(ENTITY_API_URL_ID, messageBox.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageBox.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMessageBox() throws Exception {
        // Get the messageBox
        restMessageBoxMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMessageBox() throws Exception {
        // Initialize the database
        messageBoxRepository.saveAndFlush(messageBox);

        int databaseSizeBeforeUpdate = messageBoxRepository.findAll().size();

        // Update the messageBox
        MessageBox updatedMessageBox = messageBoxRepository.findById(messageBox.getId()).get();
        // Disconnect from session so that the updates on updatedMessageBox are not directly saved in db
        em.detach(updatedMessageBox);

        restMessageBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMessageBox.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMessageBox))
            )
            .andExpect(status().isOk());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeUpdate);
        MessageBox testMessageBox = messageBoxList.get(messageBoxList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingMessageBox() throws Exception {
        int databaseSizeBeforeUpdate = messageBoxRepository.findAll().size();
        messageBox.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageBox.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageBox))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMessageBox() throws Exception {
        int databaseSizeBeforeUpdate = messageBoxRepository.findAll().size();
        messageBox.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageBox))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMessageBox() throws Exception {
        int databaseSizeBeforeUpdate = messageBoxRepository.findAll().size();
        messageBox.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageBoxMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageBox)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMessageBoxWithPatch() throws Exception {
        // Initialize the database
        messageBoxRepository.saveAndFlush(messageBox);

        int databaseSizeBeforeUpdate = messageBoxRepository.findAll().size();

        // Update the messageBox using partial update
        MessageBox partialUpdatedMessageBox = new MessageBox();
        partialUpdatedMessageBox.setId(messageBox.getId());

        restMessageBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageBox.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessageBox))
            )
            .andExpect(status().isOk());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeUpdate);
        MessageBox testMessageBox = messageBoxList.get(messageBoxList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateMessageBoxWithPatch() throws Exception {
        // Initialize the database
        messageBoxRepository.saveAndFlush(messageBox);

        int databaseSizeBeforeUpdate = messageBoxRepository.findAll().size();

        // Update the messageBox using partial update
        MessageBox partialUpdatedMessageBox = new MessageBox();
        partialUpdatedMessageBox.setId(messageBox.getId());

        restMessageBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageBox.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessageBox))
            )
            .andExpect(status().isOk());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeUpdate);
        MessageBox testMessageBox = messageBoxList.get(messageBoxList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingMessageBox() throws Exception {
        int databaseSizeBeforeUpdate = messageBoxRepository.findAll().size();
        messageBox.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, messageBox.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageBox))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMessageBox() throws Exception {
        int databaseSizeBeforeUpdate = messageBoxRepository.findAll().size();
        messageBox.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageBox))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMessageBox() throws Exception {
        int databaseSizeBeforeUpdate = messageBoxRepository.findAll().size();
        messageBox.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageBoxMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(messageBox))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageBox in the database
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMessageBox() throws Exception {
        // Initialize the database
        messageBoxRepository.saveAndFlush(messageBox);

        int databaseSizeBeforeDelete = messageBoxRepository.findAll().size();

        // Delete the messageBox
        restMessageBoxMockMvc
            .perform(delete(ENTITY_API_URL_ID, messageBox.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageBox> messageBoxList = messageBoxRepository.findAll();
        assertThat(messageBoxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
