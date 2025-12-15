package psytobetter.emotional_register.mcsv_emotional_register.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Table(name = "emotional_register")
@Data
public class

EmotionalRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String description;

    @Column(name = "emotion_id", nullable = false)
    private Long emotionId;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}

