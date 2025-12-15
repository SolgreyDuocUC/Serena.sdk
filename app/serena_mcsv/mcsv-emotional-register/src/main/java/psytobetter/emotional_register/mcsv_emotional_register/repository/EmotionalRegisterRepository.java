package psytobetter.emotional_register.mcsv_emotional_register.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psytobetter.emotional_register.mcsv_emotional_register.model.EmotionalRegister;

@Repository
public interface EmotionalRegisterRepository extends JpaRepository<EmotionalRegister, Long> {
}
