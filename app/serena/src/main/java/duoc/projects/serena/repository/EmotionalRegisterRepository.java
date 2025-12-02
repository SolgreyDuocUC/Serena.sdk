package duoc.projects.serena.repository;

import duoc.projects.serena.model.EmotionalRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmotionalRegisterRepository extends JpaRepository<EmotionalRegister, Long> {
}
