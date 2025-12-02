package duoc.projects.serena.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "emotional_register")
@Data
public class EmotionalRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String description;

    @ManyToOne
    @JoinColumn(name = "emotion_id")
    private Emotion emotion;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

