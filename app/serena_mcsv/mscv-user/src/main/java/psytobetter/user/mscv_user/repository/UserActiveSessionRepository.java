package psytobetter.user.mscv_user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psytobetter.user.mscv_user.model.UserActiveSession;

@Repository
public interface UserActiveSessionRepository extends JpaRepository<UserActiveSession, Long> {
}
