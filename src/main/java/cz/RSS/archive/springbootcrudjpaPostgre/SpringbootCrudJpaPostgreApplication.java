package cz.RSS.archive.springbootcrudjpaPostgre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootCrudJpaPostgreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrudJpaPostgreApplication.class, args);
	}

}
