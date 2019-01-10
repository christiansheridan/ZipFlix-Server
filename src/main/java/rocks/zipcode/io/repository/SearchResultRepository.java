package rocks.zipcode.io.repository;

import rocks.zipcode.io.domain.SearchResult;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the SearchResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SearchResultRepository extends MongoRepository<SearchResult, String> {

}
