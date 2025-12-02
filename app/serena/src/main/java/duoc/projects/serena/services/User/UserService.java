package duoc.projects.serena.services.User;

import duoc.projects.serena.dto.user.UserDTO;
import duoc.projects.serena.dto.user.UserCreateRequestDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserCreateRequestDTO dto);

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    List<UserDTO> findAll();

    UserDTO updateUser(Long id, UserCreateRequestDTO dto);

    void deleteUser(Long id);
}
