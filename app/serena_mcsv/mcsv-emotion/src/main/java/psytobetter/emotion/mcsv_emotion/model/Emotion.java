package psytobetter.emotion.mcsv_emotion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "emotion")
@Data
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emotion")
    private Long id;

    @NotNull
    @Column(name = "name_emotion", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description_emotion", nullable = false)
    private String description;

    @Column(name = "color_emotion", nullable = false)
    private String color;

    @Column(name = "text_color_emotion", nullable = false)
    private String textColor;

    @Column(name = "icon_emotion", nullable = false)
    private String icon;
}

