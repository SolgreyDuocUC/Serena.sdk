package duoc.projects.serena.config;

import duoc.projects.serena.dto.emotion.EmotionRequestDTO;
import duoc.projects.serena.dto.user.UserCreateRequestDTO;
import duoc.projects.serena.services.Emotion.EmotionService;
import duoc.projects.serena.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserService userService;
    private final EmotionService emotionService;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            System.out.println("Iniciando inicialización de datos para Serena...");

            // --- 1. Inicialización de Usuarios ---

            // Usuario de prueba 1
            UserCreateRequestDTO user1Dto = new UserCreateRequestDTO();
            user1Dto.setUserName("Ana Linares");
            user1Dto.setUserEmail("an.linares@duocuc.cl");
            user1Dto.setUserPassword("Password123");
            user1Dto.setUserAceptConditions(true);
            userService.createUser(user1Dto);
            System.out.println("Usuario 'an.linares@duocuc.cl' creado.");

            // Usuario de prueba 2 (Admin o secundario)
            UserCreateRequestDTO user2Dto = new UserCreateRequestDTO();
            user2Dto.setUserName("Pedro Carrasco");
            user2Dto.setUserEmail("pedro.carrasco@serena.cl");
            user2Dto.setUserPassword("Password123");
            user2Dto.setUserAceptConditions(true);
            userService.createUser(user2Dto);
            System.out.println("Usuario 'pedro.carrasco@serena.cl' creado.");

            // --- 2. Inicialización de Emociones (Tabla Emotion) ---

            // Emoción: Alegría
            EmotionRequestDTO e1Dto = new EmotionRequestDTO();
            e1Dto.setName("Alegría");
            e1Dto.setDescription("Un estado de ánimo placentero y satisfactorio.");
            e1Dto.setColor("#FFD700"); // Amarillo dorado
            e1Dto.setTextColor("#000000");
            e1Dto.setIcon("😄");
            emotionService.create(e1Dto);
            System.out.println("Emoción 'Alegría' creada.");

            // Emoción: Tristeza
            EmotionRequestDTO e2Dto = new EmotionRequestDTO();
            e2Dto.setName("Tristeza");
            e2Dto.setDescription("Sentimiento de dolor emocional asociado con la pérdida o la decepción.");
            e2Dto.setColor("#1E90FF"); // Azul claro
            e2Dto.setTextColor("#FFFFFF");
            e2Dto.setIcon("😢");
            emotionService.create(e2Dto);
            System.out.println("Emoción 'Tristeza' creada.");

            // Emoción: Calma
            EmotionRequestDTO e3Dto = new EmotionRequestDTO();
            e3Dto.setName("Calma");
            e3Dto.setDescription("Estado de quietud o tranquilidad, libre de agitación.");
            e3Dto.setColor("#3CB371");
            e3Dto.setTextColor("#FFFFFF");
            e3Dto.setIcon("🧘");
            emotionService.create(e3Dto);
            System.out.println("Emoción 'Calma' creada.");

            System.out.println("Inicialización de datos de Serena completada.");
        };
    }
}
