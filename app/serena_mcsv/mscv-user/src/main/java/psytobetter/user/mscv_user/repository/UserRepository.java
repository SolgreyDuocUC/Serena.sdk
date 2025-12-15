package psytobetter.user.mscv_user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psytobetter.user.mscv_user.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByUserEmail(String email);

    boolean existsByUserEmail(String email);
}
