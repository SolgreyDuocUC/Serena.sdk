package psytobetter.user.mscv_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MscvUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscvUserApplication.class, args);
	}

}
