package rocks.zipcode.io.repository;

import rocks.zipcode.io.domain.ZipFlixUser;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ZipFlixUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZipFlixUserRepository extends MongoRepository<ZipFlixUser, String> {

}
