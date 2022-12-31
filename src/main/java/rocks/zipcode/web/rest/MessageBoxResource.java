package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.domain.MessageBox;
import rocks.zipcode.repository.MessageBoxRepository;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.MessageBox}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MessageBoxResource {

    private final Logger log = LoggerFactory.getLogger(MessageBoxResource.class);

    private static final String ENTITY_NAME = "messageBox";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageBoxRepository messageBoxRepository;

    public MessageBoxResource(MessageBoxRepository messageBoxRepository) {
        this.messageBoxRepository = messageBoxRepository;
    }

    /**
     * {@code POST  /message-boxes} : Create a new messageBox.
     *
     * @param messageBox the messageBox to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageBox, or with status {@code 400 (Bad Request)} if the messageBox has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-boxes")
    public ResponseEntity<MessageBox> createMessageBox(@RequestBody MessageBox messageBox) throws URISyntaxException {
        log.debug("REST request to save MessageBox : {}", messageBox);
        if (messageBox.getId() != null) {
            throw new BadRequestAlertException("A new messageBox cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageBox result = messageBoxRepository.save(messageBox);
        return ResponseEntity
            .created(new URI("/api/message-boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-boxes/:id} : Updates an existing messageBox.
     *
     * @param id the id of the messageBox to save.
     * @param messageBox the messageBox to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageBox,
     * or with status {@code 400 (Bad Request)} if the messageBox is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageBox couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-boxes/{id}")
    public ResponseEntity<MessageBox> updateMessageBox(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MessageBox messageBox
    ) throws URISyntaxException {
        log.debug("REST request to update MessageBox : {}, {}", id, messageBox);
        if (messageBox.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageBox.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageBoxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MessageBox result = messageBoxRepository.save(messageBox);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageBox.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /message-boxes/:id} : Partial updates given fields of an existing messageBox, field will ignore if it is null
     *
     * @param id the id of the messageBox to save.
     * @param messageBox the messageBox to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageBox,
     * or with status {@code 400 (Bad Request)} if the messageBox is not valid,
     * or with status {@code 404 (Not Found)} if the messageBox is not found,
     * or with status {@code 500 (Internal Server Error)} if the messageBox couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/message-boxes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MessageBox> partialUpdateMessageBox(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MessageBox messageBox
    ) throws URISyntaxException {
        log.debug("REST request to partial update MessageBox partially : {}, {}", id, messageBox);
        if (messageBox.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageBox.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageBoxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MessageBox> result = messageBoxRepository
            .findById(messageBox.getId())
            .map(existingMessageBox -> {
                return existingMessageBox;
            })
            .map(messageBoxRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageBox.getId().toString())
        );
    }

    /**
     * {@code GET  /message-boxes} : get all the messageBoxes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageBoxes in body.
     */
    @GetMapping("/message-boxes")
    public List<MessageBox> getAllMessageBoxes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all MessageBoxes");
        if (eagerload) {
            return messageBoxRepository.findAllWithEagerRelationships();
        } else {
            return messageBoxRepository.findAll();
        }
    }

    /**
     * {@code GET  /message-boxes/:id} : get the "id" messageBox.
     *
     * @param id the id of the messageBox to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageBox, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-boxes/{id}")
    public ResponseEntity<MessageBox> getMessageBox(@PathVariable Long id) {
        log.debug("REST request to get MessageBox : {}", id);
        Optional<MessageBox> messageBox = messageBoxRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(messageBox);
    }

    /**
     * {@code DELETE  /message-boxes/:id} : delete the "id" messageBox.
     *
     * @param id the id of the messageBox to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-boxes/{id}")
    public ResponseEntity<Void> deleteMessageBox(@PathVariable Long id) {
        log.debug("REST request to delete MessageBox : {}", id);
        messageBoxRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
