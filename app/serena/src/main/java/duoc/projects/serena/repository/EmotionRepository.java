package duoc.projects.serena.repository;

import duoc.projects.serena.model.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {

    Optional<Emotion> findByName(String name);

    List<Emotion> findAllByOrderByNameAsc();
}
