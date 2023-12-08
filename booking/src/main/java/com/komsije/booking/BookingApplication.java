package com.komsije.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class BookingApplication {
//	@Bean
//	public JavaMailSender mailSender(){ return new JavaMailSenderImpl();}
	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}


}
