package psytobetter.emotional_register.mcsv_emotional_register.dto.clients;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmotionalRegisterResponseDTO {

    private Long id;
    private LocalDate date;
    private String description;

    private EmotionDTO emotion;
    private UserDTO user;
}
