package com.example.demo.RepositoryTest;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The UserRepository layer test. This class tests each methods by 2 (one by success and one by failure)
 */
@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Before each unit test the database get 2 records.
     */
    @BeforeEach
    public void init(){
        User user = new User("1", "Renard", "Bardis", 20, "78 BD DD", "Antes", "France");
        User user1 = new User("2", "Nikoleta", "Papas", 45, "10 BVD LLL", "Lyon", "France");
        userRepository.save(user);
        userRepository.save(user1);
    }

    /**
     * After each unit test the database repository get empty.
     */
    @AfterEach
    public void delete(){
        userRepository.deleteAll();
    }

    /**
     * This method checks all the users' records from the database.
     */
    @Test
    public void testData(){
        List<User> userRes = userRepository.findAll();
        assertEquals(2, userRes.size());
        assertEquals("Renard", userRes.get(0).getName());
    }

    /**
     * This method checks if the number of the records are not that the database has. Also,
     * checks if the given name of the first record is not what it has.
     */
    @Test
    public void testDataNot(){
        List<User> userRes = userRepository.findAll();
        assertNotEquals("George", userRes.get(0).getName());
        assertNotEquals(1, userRes.size());
    }

    /**
     * This method retrieve users by the id number. Checks a user by the name and the last name.
     */
    @Test
    public void testFindById(){
        Optional<User> userRes = userRepository.findById("1");
        if(userRes.isPresent()) {
            User user = userRes.get();
            assertEquals("Renard", user.getName());
            assertEquals("Bardis", user.getLastName());
        }
    }

    /**
     * This method retrieve users by the id number. Checks if a user is not present by a arbitrary id number.
     */
    @Test
    public void testFindByIdNot(){
        Optional<User> userRes = userRepository.findById("10");
        assertFalse(userRes.isPresent());
    }

    /**
     * This method retrieves user by the country. Checks the user name and the size of the retrieved users.
     */
    @Test
    public void testFindByCountry(){
        List<User> userRes = userRepository.findByCountry("France");
        assertEquals("Renard", userRes.get(0).getName());
        assertEquals(2, userRes.size());
    }

    /**
     * This method retrieves user by the country. Checks the username and the size of the retrieved users are not
     * there.
     */
    @Test
    public void testFindByCountryNot(){
        List<User> userRes = userRepository.findByCountry("France");
        assertNotEquals("Renoss", userRes.get(0).getName());
        assertNotEquals(1, userRes.size());
    }
}
