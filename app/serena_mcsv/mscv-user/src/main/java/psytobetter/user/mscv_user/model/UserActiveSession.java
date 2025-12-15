package psytobetter.user.mscv_user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sesion_activa")
@Data
public class UserActiveSession {

    @Id
    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "id_usuario_activo", nullable = false)
    private Long activeUserId;
}
