package duoc.projects.serena.dto.emotion;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmotionRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String color;

    @NotBlank
    private String textColor;

    @NotBlank
    private String icon;
}
