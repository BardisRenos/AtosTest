package com.example.demo.dal;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The Repository class helps to insert and retrieve data from the database. This class extends the crud repository
 * which has as parameters (CrudRepository<User, Integer>) User (The class object of the database table) and the
 * variable type of the primary key. In our case its Integer.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

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
    Optional<User> findById(Integer id);

}
