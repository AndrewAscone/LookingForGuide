package rocks.zipcode.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.UserGamingAccount;

/**
 * Spring Data JPA repository for the UserGamingAccount entity.
 */
@Repository
public interface UserGamingAccountRepository extends JpaRepository<UserGamingAccount, Long> {
    @Query("select userGamingAccount from UserGamingAccount userGamingAccount where userGamingAccount.user.login = ?#{principal.username}")
    List<UserGamingAccount> findByUserIsCurrentUser();

    default Optional<UserGamingAccount> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UserGamingAccount> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UserGamingAccount> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct userGamingAccount from UserGamingAccount userGamingAccount left join fetch userGamingAccount.user",
        countQuery = "select count(distinct userGamingAccount) from UserGamingAccount userGamingAccount"
    )
    Page<UserGamingAccount> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct userGamingAccount from UserGamingAccount userGamingAccount left join fetch userGamingAccount.user")
    List<UserGamingAccount> findAllWithToOneRelationships();

    @Query(
        "select userGamingAccount from UserGamingAccount userGamingAccount left join fetch userGamingAccount.user where userGamingAccount.id =:id"
    )
    Optional<UserGamingAccount> findOneWithToOneRelationships(@Param("id") Long id);
}
