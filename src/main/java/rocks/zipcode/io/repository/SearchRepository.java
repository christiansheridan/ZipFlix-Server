package rocks.zipcode.io.repository;

import rocks.zipcode.io.domain.Search;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Search entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SearchRepository extends MongoRepository<Search, String> {

}
