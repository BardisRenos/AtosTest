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
//		ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
		SpringApplication.run(DemoApplication.class, args);
	}
}
