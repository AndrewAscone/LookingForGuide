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
import rocks.zipcode.domain.UserGamingAccount;
import rocks.zipcode.repository.UserGamingAccountRepository;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.UserGamingAccount}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserGamingAccountResource {

    private final Logger log = LoggerFactory.getLogger(UserGamingAccountResource.class);

    private static final String ENTITY_NAME = "userGamingAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGamingAccountRepository userGamingAccountRepository;

    public UserGamingAccountResource(UserGamingAccountRepository userGamingAccountRepository) {
        this.userGamingAccountRepository = userGamingAccountRepository;
    }

    /**
     * {@code POST  /user-gaming-accounts} : Create a new userGamingAccount.
     *
     * @param userGamingAccount the userGamingAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGamingAccount, or with status {@code 400 (Bad Request)} if the userGamingAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-gaming-accounts")
    public ResponseEntity<UserGamingAccount> createUserGamingAccount(@RequestBody UserGamingAccount userGamingAccount)
        throws URISyntaxException {
        log.debug("REST request to save UserGamingAccount : {}", userGamingAccount);
        if (userGamingAccount.getId() != null) {
            throw new BadRequestAlertException("A new userGamingAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGamingAccount result = userGamingAccountRepository.save(userGamingAccount);
        return ResponseEntity
            .created(new URI("/api/user-gaming-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-gaming-accounts/:id} : Updates an existing userGamingAccount.
     *
     * @param id the id of the userGamingAccount to save.
     * @param userGamingAccount the userGamingAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGamingAccount,
     * or with status {@code 400 (Bad Request)} if the userGamingAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGamingAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-gaming-accounts/{id}")
    public ResponseEntity<UserGamingAccount> updateUserGamingAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserGamingAccount userGamingAccount
    ) throws URISyntaxException {
        log.debug("REST request to update UserGamingAccount : {}, {}", id, userGamingAccount);
        if (userGamingAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGamingAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGamingAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserGamingAccount result = userGamingAccountRepository.save(userGamingAccount);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGamingAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-gaming-accounts/:id} : Partial updates given fields of an existing userGamingAccount, field will ignore if it is null
     *
     * @param id the id of the userGamingAccount to save.
     * @param userGamingAccount the userGamingAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGamingAccount,
     * or with status {@code 400 (Bad Request)} if the userGamingAccount is not valid,
     * or with status {@code 404 (Not Found)} if the userGamingAccount is not found,
     * or with status {@code 500 (Internal Server Error)} if the userGamingAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-gaming-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserGamingAccount> partialUpdateUserGamingAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserGamingAccount userGamingAccount
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserGamingAccount partially : {}, {}", id, userGamingAccount);
        if (userGamingAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGamingAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGamingAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserGamingAccount> result = userGamingAccountRepository
            .findById(userGamingAccount.getId())
            .map(existingUserGamingAccount -> {
                if (userGamingAccount.getName() != null) {
                    existingUserGamingAccount.setName(userGamingAccount.getName());
                }
                if (userGamingAccount.getAccountName() != null) {
                    existingUserGamingAccount.setAccountName(userGamingAccount.getAccountName());
                }

                return existingUserGamingAccount;
            })
            .map(userGamingAccountRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGamingAccount.getId().toString())
        );
    }

    /**
     * {@code GET  /user-gaming-accounts} : get all the userGamingAccounts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGamingAccounts in body.
     */
    @GetMapping("/user-gaming-accounts")
    public List<UserGamingAccount> getAllUserGamingAccounts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all UserGamingAccounts");
        if (eagerload) {
            return userGamingAccountRepository.findAllWithEagerRelationships();
        } else {
            return userGamingAccountRepository.findAll();
        }
    }

    /**
     * {@code GET  /user-gaming-accounts/:id} : get the "id" userGamingAccount.
     *
     * @param id the id of the userGamingAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGamingAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-gaming-accounts/{id}")
    public ResponseEntity<UserGamingAccount> getUserGamingAccount(@PathVariable Long id) {
        log.debug("REST request to get UserGamingAccount : {}", id);
        Optional<UserGamingAccount> userGamingAccount = userGamingAccountRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(userGamingAccount);
    }

    /**
     * {@code DELETE  /user-gaming-accounts/:id} : delete the "id" userGamingAccount.
     *
     * @param id the id of the userGamingAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-gaming-accounts/{id}")
    public ResponseEntity<Void> deleteUserGamingAccount(@PathVariable Long id) {
        log.debug("REST request to delete UserGamingAccount : {}", id);
        userGamingAccountRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
