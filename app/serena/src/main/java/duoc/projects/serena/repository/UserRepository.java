package duoc.projects.serena.repository;

import duoc.projects.serena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByUserEmail(String email);

    boolean existsByUserEmail(String email);
}
