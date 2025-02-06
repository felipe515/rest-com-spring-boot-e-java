package br.com.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;

@SpringBootApplication
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
		/*
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		 
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		 
		encoders.put("BCryptPasswordEncoder", encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("BCryptPasswordEncoder", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(encoder);
		 
		String result1 = passwordEncoder.encode("1234");
		String result2 = passwordEncoder.encode("teste");
		System.out.println("My hash result1 " + result1);
		System.out.println("My hash result2 " + result2); 
		*/
	}

}
