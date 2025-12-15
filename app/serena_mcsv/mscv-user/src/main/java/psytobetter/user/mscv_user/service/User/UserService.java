package psytobetter.user.mscv_user.service.User;

import psytobetter.user.mscv_user.dto.user.UserCreateRequestDTO;
import psytobetter.user.mscv_user.dto.user.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserCreateRequestDTO dto);

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    List<UserDTO> findAll();

    UserDTO updateUser(Long id, UserCreateRequestDTO dto);

    void deleteUser(Long id);

    boolean matchesPassword(String raw, String encoded);
}
