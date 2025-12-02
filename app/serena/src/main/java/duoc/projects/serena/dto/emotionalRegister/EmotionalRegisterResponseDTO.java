package duoc.projects.serena.dto.emotionalRegister;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmotionalRegisterResponseDTO {
    private Long id;
    private LocalDate date;
    private String description;
    private Long emotionId;
    private Long userId;
}
