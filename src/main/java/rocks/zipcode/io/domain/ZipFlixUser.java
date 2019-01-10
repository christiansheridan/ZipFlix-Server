package rocks.zipcode.io.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A ZipFlixUser.
 */
@Document(collection = "zip_flix_user")
public class ZipFlixUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date_of_birth")
    private LocalDate dateOfBirth;

    @DBRef
    @Field("profiles")
    private Set<Profile> profiles = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ZipFlixUser dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public ZipFlixUser profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public ZipFlixUser addProfiles(Profile profile) {
        this.profiles.add(profile);
        profile.setZipFlixUser(this);
        return this;
    }

    public ZipFlixUser removeProfiles(Profile profile) {
        this.profiles.remove(profile);
        profile.setZipFlixUser(null);
        return this;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
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
        ZipFlixUser zipFlixUser = (ZipFlixUser) o;
        if (zipFlixUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zipFlixUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZipFlixUser{" +
            "id=" + getId() +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            "}";
    }
}
