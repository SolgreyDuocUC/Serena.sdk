package psytobetter.emotion.mcsv_emotion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("PsyToBetterSA");
        contact.setEmail("PsyToBetterSA@gmail.com");
        return new OpenAPI()
                .info(new Info()
                        .title("Serena")
                        .version("1.0.0")
                        .description("Este es el Backend para manejo de datos de emociones registradas para consumo del usuario")
                        .contact(contact)
                );
    }
}
