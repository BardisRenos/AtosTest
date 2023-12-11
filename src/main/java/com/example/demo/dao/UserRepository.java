package com.example.demo.dao;
import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The Repository class helps to insert and retrieve data from the database. This class extends the crud repository
 * which has as parameters (MongoRepository<User, Integer>) User (The class object of the database table) and the
 * variable type of the primary key. In our case its Integer.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Retrieve a number of users by a country.
     * @param Country parameter
     * @return List<User>
     */
    List<User> findByCountry(String Country);

    /**
     * Retrieve user by a given id.
     * @param id unique variable for each user
     * @return Optional<User>
     */
//    Optional<User> findById(String id);

}
