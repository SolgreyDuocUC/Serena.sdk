package psytobetter.user.mscv_user.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombreUsuario")
    private String userName;

    @Column(name = "emailUsuario", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "contraseniaUsuario", nullable = false)
    private String userPassword;

    @Column(name = "usuarioCondiciones")
    private Boolean userAceptConditions;

    @Column(name = "imagenUsuario")
    private String userImageUri;

    // Métodos requeridos por Spring Security ---
    public String getUsername() {
        return this.userEmail; // Spring usa email para login
    }

    public String getPassword() {
        return this.userPassword;
    }

    // Este método devuelve el nombre del usuario
    public String getName() {
        return this.userName;
    }
}
