package com.isi.booking;

import com.isi.booking.role.Role;
import com.isi.booking.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BookingRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingRoomApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository){
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(
						Role.builder().name("USER").build()
				);
			}
			if (roleRepository.findByName("ADMIN").isEmpty()){
				roleRepository.save(
						Role.builder().name("ADMIN").build()
				);
			}
			if (roleRepository.findByName("MANAGER").isEmpty()){
				roleRepository.save(
						Role.builder().name("MANAGER").build()
				);
			}
		};
	}

}
