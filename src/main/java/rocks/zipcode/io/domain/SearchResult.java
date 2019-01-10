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
 * A SearchResult.
 */
@Document(collection = "search_result")
public class SearchResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

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

    public Set<Video> getVideos() {
        return videos;
    }

    public SearchResult videos(Set<Video> videos) {
        this.videos = videos;
        return this;
    }

    public SearchResult addVideos(Video video) {
        this.videos.add(video);
        video.setSearchResult(this);
        return this;
    }

    public SearchResult removeVideos(Video video) {
        this.videos.remove(video);
        video.setSearchResult(null);
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
        SearchResult searchResult = (SearchResult) o;
        if (searchResult.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), searchResult.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SearchResult{" +
            "id=" + getId() +
            "}";
    }
}
