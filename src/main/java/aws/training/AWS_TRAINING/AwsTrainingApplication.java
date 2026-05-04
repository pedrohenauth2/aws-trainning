package aws.training.AWS_TRAINING;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwsTrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsTrainingApplication.class, args);
	}

}
