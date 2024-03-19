package com.example.Vms;

import com.example.Vms.entities.Event;
import com.example.Vms.entities.Organisation;
import com.example.Vms.entities.User;
import com.example.Vms.repositories.OrganisationRepo;
import com.example.Vms.repositories.UserRepo;
import com.example.Vms.repositories.VolunteerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class VmsApplication implements CommandLineRunner {
@Autowired
	UserRepo userRepo;
@Autowired
	VolunteerRepo volunteerRepo;
@Autowired
	OrganisationRepo repo;
	public static void main(String[] args) {
		SpringApplication.run(VmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	//Organisation organisation = repo.findById(1).orElse(null);
	//		System.out.println(organisation.getClosedevents());
//		String time1Str = "10:00 AM";
//		String time2Str = "10:00 PM";
//       String [] x = "10:00 Am to 12:00 Am".split("to");
//		System.out.println(Arrays.toString(x));
//		// Define the format for parsing the time
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
//
//		// Parse the time strings into LocalTime objects
//		LocalTime time1 = LocalTime.parse(time1Str, formatter);
//		LocalTime time2 = LocalTime.parse(time2Str, formatter);
//
//		// Compare the LocalTime objects
//		int comparison = time1.compareTo(time2);
//		if (comparison < 0) {
//			System.out.println(time1Str + " is before " + time2Str);
//		} else if (comparison > 0) {
//			System.out.println(time1Str + " is after " + time2Str);
//		} else {
//			System.out.println(time1Str + " is equal to " + time2Str);
//		List<Event> events =
//		System.out.println(volunteerRepo.findEventsByVolunteerAndOrganisation(2,2));
		}
	}
