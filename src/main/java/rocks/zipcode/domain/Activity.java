package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ordinal")
    private Integer ordinal;

    @OneToMany(mappedBy = "activity")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "activity" }, allowSetters = true)
    private Set<Post> posts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Activity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Activity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrdinal() {
        return this.ordinal;
    }

    public Activity ordinal(Integer ordinal) {
        this.setOrdinal(ordinal);
        return this;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public Set<Post> getPosts() {
        return this.posts;
    }

    public void setPosts(Set<Post> posts) {
        if (this.posts != null) {
            this.posts.forEach(i -> i.setActivity(null));
        }
        if (posts != null) {
            posts.forEach(i -> i.setActivity(this));
        }
        this.posts = posts;
    }

    public Activity posts(Set<Post> posts) {
        this.setPosts(posts);
        return this;
    }

    public Activity addPost(Post post) {
        this.posts.add(post);
        post.setActivity(this);
        return this;
    }

    public Activity removePost(Post post) {
        this.posts.remove(post);
        post.setActivity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ordinal=" + getOrdinal() +
            "}";
    }
}
//@Entity
//@Table(name = "activity")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@SuppressWarnings("common-java:DuplicatedBlocks")
//public class Activity implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "ordinal")
//    private Integer ordinal;
//
//    @OneToMany(mappedBy = "activity")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "user", "activity" }, allowSetters = true)
//    private Set<Post> posts = new HashSet<>();
//
//    // jhipster-needle-entity-add-field - JHipster will add fields here
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public Activity id(Long id) {
//        this.setId(id);
//        return this;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public Activity name(String name) {
//        this.setName(name);
//        return this;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getOrdinal() {
//        return this.ordinal;
//    }
//
//    public Activity ordinal(Integer ordinal) {
//        this.setOrdinal(ordinal);
//        return this;
//    }
//
//    public void setOrdinal(Integer ordinal) {
//        this.ordinal = ordinal;
//    }
//
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Activity)) {
//            return false;
//        }
//        return id != null && id.equals(((Activity) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
//        return getClass().hashCode();
//    }
//
//    @Override
//    public String toString() {
//        return "Activity{" +
//            "id=" + getId() +
//            ", name='" + getName() + "'" +
//            ", ordinal=" + getOrdinal() +
//            "}";
//    }
//}
