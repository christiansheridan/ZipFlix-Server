package rocks.zipcode.io.repository;

import rocks.zipcode.io.domain.RecommendedVideos;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the RecommendedVideos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecommendedVideosRepository extends MongoRepository<RecommendedVideos, String> {

}
