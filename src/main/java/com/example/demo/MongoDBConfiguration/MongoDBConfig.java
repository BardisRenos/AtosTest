package com.example.demo.MongoDBConfiguration;
import com.example.demo.dal.UserRepository;
import com.example.demo.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import java.util.*;

@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class MongoDBConfig {

    @Bean
    public CommandLineRunner initDB(UserRepository userRepository){
        return (args) ->{
            List<User> users = new ArrayList<>(Arrays.asList(new User(0, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"),
                    new User(1, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France"),
                    new User(2, "Aggelos", "MPalallis", 30, "90 BVD MM", "Paris", "France"),
                    new User(3, "Vaggelis", "Papagkikas", 50, "90 RUE LL", "Bordeaux", "France")));
            userRepository.saveAll(users);
        };
    }

}
