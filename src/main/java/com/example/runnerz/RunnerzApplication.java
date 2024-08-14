package com.example.runnerz;

import com.example.runnerz.items.Item;
import com.example.runnerz.items.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RunnerzApplication {
	private static final Logger log= LoggerFactory.getLogger(RunnerzApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RunnerzApplication.class, args);
	}

}
