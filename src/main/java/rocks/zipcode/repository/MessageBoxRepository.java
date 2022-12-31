package rocks.zipcode.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rocks.zipcode.domain.MessageBox;

/**
 * Spring Data JPA repository for the MessageBox entity.
 */
@Repository
public interface MessageBoxRepository extends JpaRepository<MessageBox, Long> {
    default Optional<MessageBox> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<MessageBox> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<MessageBox> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct messageBox from MessageBox messageBox left join fetch messageBox.user",
        countQuery = "select count(distinct messageBox) from MessageBox messageBox"
    )
    Page<MessageBox> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct messageBox from MessageBox messageBox left join fetch messageBox.user")
    List<MessageBox> findAllWithToOneRelationships();

    @Query("select messageBox from MessageBox messageBox left join fetch messageBox.user where messageBox.id =:id")
    Optional<MessageBox> findOneWithToOneRelationships(@Param("id") Long id);
}
