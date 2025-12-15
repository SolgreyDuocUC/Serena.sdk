package psytobetter.emotion.mcsv_emotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import psytobetter.emotion.mcsv_emotion.model.Emotion;

import java.util.List;
import java.util.Optional;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {

    Optional<Emotion> findByName(String name);

    List<Emotion> findAllByOrderByNameAsc();
}
