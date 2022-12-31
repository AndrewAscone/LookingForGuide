package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import rocks.zipcode.domain.enumeration.GuideType;
import rocks.zipcode.domain.enumeration.UserType;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "post_body", nullable = false)
    private String postBody;

    @Column(name = "date")
    private ZonedDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "guide_type")
    private GuideType guideType;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "posts" }, allowSetters = true)
    private Activity activity;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Post id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Post title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostBody() {
        return this.postBody;
    }

    public Post postBody(String postBody) {
        this.setPostBody(postBody);
        return this;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Post date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public GuideType getGuideType() {
        return this.guideType;
    }

    public Post guideType(GuideType guideType) {
        this.setGuideType(guideType);
        return this;
    }

    public void setGuideType(GuideType guideType) {
        this.guideType = guideType;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Post image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Post imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public Post userType(UserType userType) {
        this.setUserType(userType);
        return this;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post user(User user) {
        this.setUser(user);
        return this;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Post activity(Activity activity) {
        this.setActivity(activity);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", postBody='" + getPostBody() + "'" +
            ", date='" + getDate() + "'" +
            ", guideType='" + getGuideType() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", userType='" + getUserType() + "'" +
            "}";
    }
}
//@Entity
//@Table(name = "post")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@SuppressWarnings("common-java:DuplicatedBlocks")
//public class Post implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @NotNull
//    @Column(name = "title", nullable = false)
//    private String title;
//
//    @NotNull
//    @Column(name = "post_body", nullable = false)
//    private String postBody;
//
//    @Column(name = "date")
//    private ZonedDateTime date;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "guide_type")
//    private GuideType guideType;
//
//    @Lob
//    @Column(name = "image")
//    private byte[] image;
//
//    @Column(name = "image_content_type")
//    private String imageContentType;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "user_type")
//    private UserType userType;
//
////    @OneToOne
////    @JoinColumn(unique = true)
////    private Activity activity;
//
//    @ManyToOne
//    private User user;
//
//    @ManyToOne
//    @JsonIgnoreProperties(value = { "posts" }, allowSetters = true)
//    private Activity activity;
//
//    // jhipster-needle-entity-add-field - JHipster will add fields here
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public Post id(Long id) {
//        this.setId(id);
//        return this;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return this.title;
//    }
//
//    public Post title(String title) {
//        this.setTitle(title);
//        return this;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getPostBody() {
//        return this.postBody;
//    }
//
//    public Post postBody(String postBody) {
//        this.setPostBody(postBody);
//        return this;
//    }
//
//    public void setPostBody(String postBody) {
//        this.postBody = postBody;
//    }
//
//    public ZonedDateTime getDate() {
//        return this.date;
//    }
//
//    public Post date(ZonedDateTime date) {
//        this.setDate(date);
//        return this;
//    }
//
//    public void setDate(ZonedDateTime date) {
//        this.date = date;
//    }
//
//    public GuideType getGuideType() {
//        return this.guideType;
//    }
//
//    public Post guideType(GuideType guideType) {
//        this.setGuideType(guideType);
//        return this;
//    }
//
//    public void setGuideType(GuideType guideType) {
//        this.guideType = guideType;
//    }
//
//    public byte[] getImage() {
//        return this.image;
//    }
//
//    public Post image(byte[] image) {
//        this.setImage(image);
//        return this;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }
//
//    public String getImageContentType() {
//        return this.imageContentType;
//    }
//
//    public Post imageContentType(String imageContentType) {
//        this.imageContentType = imageContentType;
//        return this;
//    }
//
//    public void setImageContentType(String imageContentType) {
//        this.imageContentType = imageContentType;
//    }
//
//    public UserType getUserType() {
//        return this.userType;
//    }
//
//    public Post userType(UserType userType) {
//        this.setUserType(userType);
//        return this;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }
//
//    public Activity getActivity() {
//        return this.activity;
//    }
//
//    public void setActivity(Activity activity) {
//        this.activity = activity;
//    }
//
//    public Post activity(Activity activity) {
//        this.setActivity(activity);
//        return this;
//    }
//
//    public User getUser() {
//        return this.user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Post user(User user) {
//        this.setUser(user);
//        return this;
//    }
//
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Post)) {
//            return false;
//        }
//        return id != null && id.equals(((Post) o).id);
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
//        return "Post{" +
//            "id=" + getId() +
//            ", title='" + getTitle() + "'" +
//            ", postBody='" + getPostBody() + "'" +
//            ", date='" + getDate() + "'" +
//            ", guideType='" + getGuideType() + "'" +
//            ", image='" + getImage() + "'" +
//            ", imageContentType='" + getImageContentType() + "'" +
//            ", userType='" + getUserType() + "'" +
//            "}";
//    }
//}
