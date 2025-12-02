package duoc.projects.serena.dto.emotion;

import lombok.Data;

@Data
public class EmotionResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String color;
    private String textColor;
    private String icon;
}
