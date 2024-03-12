package com.example.Vms;

import com.example.Vms.entities.User;
import com.example.Vms.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class VmsApplication implements CommandLineRunner {
@Autowired
	UserRepo userRepo;
	public static void main(String[] args) {
		SpringApplication.run(VmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
