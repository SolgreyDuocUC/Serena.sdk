package psytobetter.emotion.mcsv_emotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class McsvEmotionApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsvEmotionApplication.class, args);
	}

}
