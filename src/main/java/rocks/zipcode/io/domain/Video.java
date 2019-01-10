package rocks.zipcode.io.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Video.
 */
@Document(collection = "video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("video_name")
    private String videoName;

    @Field("url")
    private String url;

    @Field("image_url")
    private String imageURL;

    @Field("avg_rating")
    private Double avgRating;

    @DBRef
    @Field("comments")
    private Set<Comment> comments = new HashSet<>();
    @DBRef
    @Field("tags")
    private Set<Tag> tags = new HashSet<>();
    @DBRef
    @Field("ratings")
    private Set<Rating> ratings = new HashSet<>();
    @DBRef
    @Field("genres")
    private Set<Genre> genres = new HashSet<>();

    @DBRef
    @Field("recommendedVideos")
    @JsonIgnoreProperties("videos")
    private RecommendedVideos recommendedVideos;

    @DBRef
    @Field("search")
    @JsonIgnoreProperties("videos")
    private Search search;

    @DBRef
    @Field("searchResult")
    @JsonIgnoreProperties("videos")
    private SearchResult searchResult;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public Video videoName(String videoName) {
        this.videoName = videoName;
        return this;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getUrl() {
        return url;
    }

    public Video url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Video imageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public Video avgRating(Double avgRating) {
        this.avgRating = avgRating;
        return this;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Video comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Video addComments(Comment comment) {
        this.comments.add(comment);
        comment.setVideo(this);
        return this;
    }

    public Video removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setVideo(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Video tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Video addTags(Tag tag) {
        this.tags.add(tag);
        tag.setVideo(this);
        return this;
    }

    public Video removeTags(Tag tag) {
        this.tags.remove(tag);
        tag.setVideo(null);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public Video ratings(Set<Rating> ratings) {
        this.ratings = ratings;
        return this;
    }

    public Video addRatings(Rating rating) {
        this.ratings.add(rating);
        rating.setVideo(this);
        return this;
    }

    public Video removeRatings(Rating rating) {
        this.ratings.remove(rating);
        rating.setVideo(null);
        return this;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Video genres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public Video addGenres(Genre genre) {
        this.genres.add(genre);
        return this;
    }

    public Video removeGenres(Genre genre) {
        this.genres.remove(genre);
        return this;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public RecommendedVideos getRecommendedVideos() {
        return recommendedVideos;
    }

    public Video recommendedVideos(RecommendedVideos recommendedVideos) {
        this.recommendedVideos = recommendedVideos;
        return this;
    }

    public void setRecommendedVideos(RecommendedVideos recommendedVideos) {
        this.recommendedVideos = recommendedVideos;
    }

    public Search getSearch() {
        return search;
    }

    public Video search(Search search) {
        this.search = search;
        return this;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public SearchResult getSearchResult() {
        return searchResult;
    }

    public Video searchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
        return this;
    }

    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
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
        Video video = (Video) o;
        if (video.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), video.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Video{" +
            "id=" + getId() +
            ", videoName='" + getVideoName() + "'" +
            ", url='" + getUrl() + "'" +
            ", imageURL='" + getImageURL() + "'" +
            ", avgRating=" + getAvgRating() +
            "}";
    }
}
