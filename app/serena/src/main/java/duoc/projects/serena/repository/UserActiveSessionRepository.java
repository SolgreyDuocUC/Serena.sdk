package duoc.projects.serena.repository;

import duoc.projects.serena.model.UserActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActiveSessionRepository extends JpaRepository<UserActiveSession, Long> {
}
