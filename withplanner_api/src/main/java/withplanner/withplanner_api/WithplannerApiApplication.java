package withplanner.withplanner_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WithplannerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WithplannerApiApplication.class, args);
	}


}
