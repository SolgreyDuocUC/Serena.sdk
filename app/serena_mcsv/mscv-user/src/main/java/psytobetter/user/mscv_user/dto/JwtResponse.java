package psytobetter.user.mscv_user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
