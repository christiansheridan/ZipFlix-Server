package rocks.zipcode.io.repository;

import rocks.zipcode.io.domain.Genre;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Genre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

}
