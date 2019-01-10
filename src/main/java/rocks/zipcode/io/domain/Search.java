package rocks.zipcode.io.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Search.
 */
@Document(collection = "search")
public class Search implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("search_query")
    private String searchQuery;

    @DBRef
    @Field("videos")
    private Set<Video> videos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public Search searchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        return this;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public Search videos(Set<Video> videos) {
        this.videos = videos;
        return this;
    }

    public Search addVideos(Video video) {
        this.videos.add(video);
        video.setSearch(this);
        return this;
    }

    public Search removeVideos(Video video) {
        this.videos.remove(video);
        video.setSearch(null);
        return this;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
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
        Search search = (Search) o;
        if (search.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), search.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Search{" +
            "id=" + getId() +
            ", searchQuery='" + getSearchQuery() + "'" +
            "}";
    }
}
