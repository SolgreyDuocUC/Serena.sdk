package duoc.projects.serena.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sesion_activa")
@Data
public class UserActiveSession {

    @Id
    @Column(name = "session_id")
    private Integer sessionId = 1;

    @Column(name = "id_usuario_activo", nullable = false)
    private Long activeUserId;
}
