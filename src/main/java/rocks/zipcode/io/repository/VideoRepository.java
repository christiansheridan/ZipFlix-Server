package rocks.zipcode.io.repository;

import rocks.zipcode.io.domain.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Video entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
    @Query("{}")
    Page<Video> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Video> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Video> findOneWithEagerRelationships(String id);

}
