package com.Abilmansur.EmailSpammer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EmailSpammerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSpammerApplication.class, args);
	}

}
