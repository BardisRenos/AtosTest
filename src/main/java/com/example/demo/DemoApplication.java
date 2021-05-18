package com.example.demo;

import com.example.demo.dal.UserRepository;
import com.example.demo.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.*;

@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass=true)
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
//		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDB(UserRepository userRepository){
		return (args) ->{
			List<User> users = new ArrayList<>(Arrays.asList(new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"),
					new User(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France"),
					new User(3, "Aggelos", "MPalallis", 30, "90 BVD MM", "Paris", "France"),
					new User(4, "Vaggelis", "Papagkikas", 50, "90 RUE LL", "Bordeaux", "France")));

			userRepository.saveAll(users);
		};

	}

}
