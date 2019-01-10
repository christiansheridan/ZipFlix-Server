package rocks.zipcode.io.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Profile.
 */
@Document(collection = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("date_of_birth")
    private LocalDate dateOfBirth;

    @Field("image_url")
    private String imageURL;

    @DBRef
    @Field("interests")
    private Set<Tag> interests = new HashSet<>();
    @DBRef
    @Field("zipFlixUser")
    @JsonIgnoreProperties("profiles")
    private ZipFlixUser zipFlixUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Profile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Profile dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Profile imageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Set<Tag> getInterests() {
        return interests;
    }

    public Profile interests(Set<Tag> tags) {
        this.interests = tags;
        return this;
    }

    public Profile addInterests(Tag tag) {
        this.interests.add(tag);
        tag.setProfile(this);
        return this;
    }

    public Profile removeInterests(Tag tag) {
        this.interests.remove(tag);
        tag.setProfile(null);
        return this;
    }

    public void setInterests(Set<Tag> tags) {
        this.interests = tags;
    }

    public ZipFlixUser getZipFlixUser() {
        return zipFlixUser;
    }

    public Profile zipFlixUser(ZipFlixUser zipFlixUser) {
        this.zipFlixUser = zipFlixUser;
        return this;
    }

    public void setZipFlixUser(ZipFlixUser zipFlixUser) {
        this.zipFlixUser = zipFlixUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", imageURL='" + getImageURL() + "'" +
            "}";
    }
}
